# JBrave

[![CI](https://github.com/Urbine/JBrave/actions/workflows/maven.yml/badge.svg)](https://github.com/Urbine/JBrave/actions/workflows/maven.yml)
![Status](https://img.shields.io/badge/status-alpha-orange)
![Java](https://img.shields.io/badge/java-21-brightgreen)
![License](https://img.shields.io/badge/license-Apache%202.0-blue)
[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://urbine.github.io/JBrave)

JBrave is a modern, type-safe Java SDK for the **Brave Search API**. It models search queries, execution, responses, and errors as a unified lifecycle—enabling you to build, execute, inspect, and replay search interactions with strong correctness guarantees.

JBrave provides fluent builders for constructing search queries, strict validation to prevent invalid requests, and flexible execution that works with any HTTP strategy—or none at all. This makes it suitable not only for traditional backend services, but also for AI/ML pipelines, offline analysis, and deterministic testing workflows.

## Features

- ✅ Fluent, type-safe query builders with strict validation
- 🔒 Fail-fast invariants—invalid queries never reach the network
- 🔄 Flexible execution—use built-in executor or treat builders as pure `HttpRequest`/`URI` generators
- 🧪 Test-friendly—build and parse without network access
- 📦 Clean public API with typed error responses
- 🧠 Offline deserialization from JSON files or strings

## Installation

> Replace `<version>` with the published release.

### Gradle

```gradle
implementation("net.ygbstudio:jbrave:<version>")
```

### Maven

```xml
<dependency>
  <groupId>net.ygbstudio</groupId>
  <artifactId>jbrave</artifactId>
  <version><!-- version --></version>
</dependency>
```

JBrave can also be consumed via [JitPack](https://jitpack.io) from GitHub releases.

## Core Concepts

A query builder represents a **stateful, single-request, reusable session object**. Each instance:

- Accumulates query configuration
- Stores execution results internally (HTTP response, rate limits, errors)
- Can be inspected multiple times without re-executing
- Must be explicitly reset via `reset()` before reuse

> **Note:** Query builders are not thread-safe.

This design separates **query construction**, **execution**, and **result extraction**, enabling custom retry logic and offline testing.

You can use them as a pure `HttpRequest`/`URI` generator with a custom HTTP client or execute them with the built-in rate-aware executor. Executions through the built-in executor are serialized per token via a FIFO admission gate, and rate-limited responses are retried once by default with server-specified backoff.

## Usage

### Building Queries

```java
import java.net.http.HttpRequest;
import net.ygbstudio.jbrave.api.builders.BraveWebQuery;
import net.ygbstudio.jbrave.api.options.*;
import net.ygbstudio.jbrave.api.filters.*;
import net.ygbstudio.jbrave.core.local.ClientInfo;

// Load token from properties file in your classpath or environment variable
ClientInfo token = ClientInfo.fromProperties(
    "config.properties",
    "brave.subscriptionToken"
);
// Or:
// ClientInfo token = ClientInfo.fromEnvironment("BRAVE_SUBSCRIPTION_TOKEN");

// Build request
HttpRequest request = BraveWebQuery.builder()
    .query("search engines")
    .language(SearchLanguage.ENGLISH)
    .market(MarketLocale.UNITED_STATES_ENGLISH)
    .country(Country.UNITED_STATES)
    .safeSearch(SafeSearch.MODERATE)
    .withHeaders(req -> req
        .withLatitude(37.774929)
        .withLongitude(-122.419416)
        .withCity("SF")
        .withState("CA")
    )
    .withOperators(op -> op
        .include("brave")
        .exclude("google")
    )
    .count(10)
    .withToken(token)
    .toHttpRequest(); // Or: .toURI() / .execute()
```

### Executing & Extracting Results

```java
BraveWebQuery query = BraveWebQuery.builder()
    .query("java http client")
    .withRetries(3)
    .withToken(token);

// Explicit execution
query.execute();

// or execute and get a POJO response
query.getPOJO()
     .ifPresent(r -> System.out.println(
         r.web().results().size()
     ));

// Lazy execution—extraction triggers execution automatically
Optional<WebSearchApiResponse> response = query.getPOJO();
Optional<ErrorResponse> error = query.getErrorPOJO();

// Returns either a successful response or an error response
Optional<ApiResponse> either = query.getEitherPOJO();
```

> Execution happens **once per session**, unless explicitly re-triggered (for example, via `execute()` or after calling `reset()`).

### Retry & Rate-Limit Backoff

By default, executions retry once when the API responds with HTTP 429. The retry is **rate-aware**: the execution gate sleeps for the server-specified rate-limit reset window (`X-RateLimit-Reset`) before attempting again, so the API's backoff guidance is honored without caller involvement.

Use `withRetries(n)` to set the maximum number of retry attempts for rate-limited responses; values `<= 0` fall back to the default (one retry). When retries are exhausted, the final response — including a rate-limited one — is stored in the builder and remains inspectable via the rate-limit introspection methods below.

> **Note:** A 429 that does not carry a rate-limit error code is treated as an unrecoverable API error (`BraveApiException`). A rate-limited response missing the rate-limit window headers raises `BraveClientException`, since rate-aware backoff cannot be activated.

### Rate Limit Introspection

After execution, inspect Brave API rate limit headers as typed objects. Rate-limit headers are captured on every response, including the final 429 after retries are exhausted:

```java
query.getRateLimits()
     .map(XRateLimit::limit)
     .ifPresent(System.out::println);

query.getRateLimitRemaining()
     .map(XRateLimitRemaining::remaining)
     .ifPresent(System.out::println);

query.getRateLimitPolicy()
     .map(XRateLimitPolicy::policy)
     .ifPresent(System.out::println);

query.getRateLimitReset()
     .map(XRateLimitReset::reset)
     .ifPresent(System.out::println);

```

All rate limit classes provide a static factory method `from` for deserializing data from `HttpResponse` headers, allowing you to implement your own HTTP client while still taking advantage of JBrave’s rate limit introspection.

## Offline Deserialization

Response models are decoupled from HTTP execution. Each response type also provides static factory methods for deserialization:

```java
// From file
WebSearchApiResponse response = WebSearchApiResponse.from(new File("response.json"));

// From string
WebSearchApiResponse response = WebSearchApiResponse.from(jsonString);

// Serialize back
response.write(new File("output.json"));
String json = response.toJson();
```

This is useful for unit tests, cached responses, offline analysis, and debugging.

## API Reference

### Query Builders

| Builder                | Endpoint     | Response Type                 |
| ---------------------- | ------------ | ----------------------------- |
| `BraveWebQuery`        | Web search   | `WebSearchApiResponse`        |
| `BraveImageQuery`      | Image search | `ImageSearchApiResponse`      |
| `BraveNewsQuery`       | News search  | `NewsSearchApiResponse`       |
| `BraveVideoQuery`      | Video search | `VideoSearchApiResponse`      |
| `BraveSuggestQuery`    | Autocomplete | `SuggestSearchApiResponse`    |
| `BraveSpellcheckQuery` | Spellcheck   | `SpellcheckSearchApiResponse` |

All builders enforce query constraints and fail fast on invalid configuration.

### Options (`net.ygbstudio.jbrave.api.options`)

| Type             | Purpose                     |
| ---------------- | --------------------------- |
| `Country`        | Geographic country codes    |
| `MarketLocale`   | Regional market identifiers |
| `SearchLanguage` | Language preferences        |
| `Units`          | Measurement units           |

### Filters (`net.ygbstudio.jbrave.api.filters`)

| Type           | Purpose                  |
| -------------- | ------------------------ |
| `SafeSearch`   | Content safety filtering |
| `Freshness`    | Recency constraints      |
| `ResultFilter` | Result type filtering    |

### Rate Limits (`net.ygbstudio.jbrave.api.rate`)

| Type                  | Header                  |
| --------------------- | ----------------------- |
| `XRateLimit`          | `X-RateLimit-Limit`     |
| `XRateLimitRemaining` | `X-RateLimit-Remaining` |
| `XRateLimitPolicy`    | `X-RateLimit-Policy`    |
| `XRateLimitReset`     | `X-RateLimit-Reset`     |

## Use Cases

- **Search-powered applications** — backend services, dashboards, search gateways
- **AI/ML pipelines** — RAG, prompt enrichment, ranking layers, and deterministic test data; structured response fields can be parsed into embeddings, scores, or signals.
- **Data collection** — batch jobs, trend analysis, offline research workflows

## Limitations

JBrave targets publicly available Brave Search API surfaces. Some premium or plan-restricted features may be limited or omitted—only functionality that can be reliably validated is exposed.

Contributions for additional features are welcome.

> ⚠️ **Unofficial & Independent**
>
> JBrave is an independent, community-focused Java project built around the Brave Search API.
> It is **not affiliated with, endorsed by, or supported by Brave Software, Inc.**
>
> The library is still evolving and may introduce breaking changes.

## License

Apache License 2.0  
© 2025–2026 YGBStudio
