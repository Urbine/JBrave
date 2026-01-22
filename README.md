# JBrave

[![CI](https://github.com/YGBStudio/JBrave/actions/workflows/maven.yml/badge.svg)](https://github.com/YGBStudio/JBrave/actions/workflows/maven.yml)
![Status](https://img.shields.io/badge/status-alpha-orange)
![Java](https://img.shields.io/badge/java-21-brightgreen)
![License](https://img.shields.io/badge/license-Apache%202.0-blue)

JBrave is a modern, type-safe, opinionated, and **unofficial** Java **SDK** for modeling and interacting with the **Brave Search API**.

JBrave models search queries, execution, responses, errors, and rate-limit metadata as a **single coherent lifecycle**, allowing applications to build, execute, inspect, persist, and replay search interactions with strong correctness guarantees.

Designed for correctness, composability, and testability, JBrave provides fluent builders for constructing search queries, strict validation to prevent invalid requests, and flexible execution that works with any HTTP strategy — or none at all.
This makes it suitable not only for traditional backend services, but also for **AI/ML pipelines**, offline analysis, and deterministic testing workflows.

---

## Features

* ✅ **Fluent, type-safe query builders**
* 🔒 **Strict invariants** — invalid queries fail fast
* 🧱 **Clear separation of concerns**
  * Query construction
  * Request building
  * Execution
  * Response parsing
* 🔄 **Flexible execution**
  * Use the built-in executor
  * Or treat builders as pure `HttpRequest` / `URI` generators
* 🧪 **Test-friendly**
  * No network required for building or parsing
  * Supports offline deserialization
* 📦 **Clean public API** with minimal internal leakage
* 🔴 **Typed error responses**

  * Deserialize, persist, and replay API failures for custom handling and testing

---

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

### GitHub Releases (via JitPack)

JBrave can also be consumed directly from GitHub releases using JitPack.

---

## Mental Model

In JBrave, a query builder represents a **single executable query session**.

A session is a **stateful object**. It represents exactly one request/response lifecycle.

A session:

* Accumulates query configuration
* Can be executed **explicitly or lazily**
* Stores the HTTP response and associated metadata internally
* Can be inspected multiple times without re-executing
* Must be explicitly reset before reuse

> **Note:** Query builders are **not thread-safe** and are intended to be used as single-request sessions.

This design allows you to:

* Build requests without executing them
* Separate execution from result extraction
* Inspect rate limits and response metadata
* Implement custom retry or backoff strategies
* Use JBrave purely as an `HttpRequest` / `URI` builder
* Treat API responses as durable domain objects, independent of HTTP

---

## Quick Start

### Build a Web Search Request (without executing)

```java
import net.ygbstudio.jbrave.api.builders.BraveWebQuery;
import net.ygbstudio.jbrave.api.options.MarketLocale;
import net.ygbstudio.jbrave.api.options.SearchLanguage;
import net.ygbstudio.jbrave.api.options.Country;
import net.ygbstudio.jbrave.api.filters.SafeSearch;
import net.ygbstudio.jbrave.core.local.ClientInfo;

ClientInfo token =
    ClientInfo.fromProperties(
        "myConfig.properties",
        "brave.subscriptionToken"
    );

HttpRequest request =
    BraveWebQuery.builder()
        .query("search engines")
        .language(SearchLanguage.ENGLISH)
        .market(MarketLocale.UNITED_STATES_ENGLISH)
        .country(Country.UNITED_STATES)
        .safeSearch(SafeSearch.MODERATE)
        .withHeaders(req ->
            req.withLatitude(37.774929)
               .withLongitude(-122.419416)
               .withCity("SF")
               .withState("CA")
               .withUserAgent("myUserAgent")
        )
        .withOperators(op ->
            op.include("brave")
              .exclude("google")
        )
        .textDecorations(true)
        .count(10)
        .withToken(token)
        // You can also obtain the URI via toURI(),
        // or execute using execute() / getHttpResponse()
        .toHttpRequest();
```

---

## Execution Model

JBrave deliberately separates **query construction**, **execution**, and **result extraction**.

A query builder represents a single request/response lifecycle and caches execution results within the session.

### Explicit Execution with Deferred Extraction

```java
BraveWebQuery query =
    BraveWebQuery.builder()
        .query("java http client")
        .withRetries(3)
        .withToken(token);

query.execute();

query.getPOJO()
     .ifPresent(response ->
         System.out.println(
             response.web().results().size()
         )
     );
```

### Lazy Execution

Calling any extraction method will automatically execute the request if it has not yet been executed:

```java
Optional<WebSearchApiResponse> response = query.getPOJO();
Optional<HttpResponse<String>> httpResponse = query.getHttpResponse();
Optional<ErrorResponse> errorResponse = query.getErrorPOJO();

// Returns either a successful response or an error response
Optional<ApiResponse> apiResponse = query.getEitherPOJO();

```

> Execution happens **once per session**, unless explicitly re-triggered (e.g. via `execute()` or `reset()`).

---

## Rate Limit Introspection

JBrave exposes Brave API rate-limit information as **typed domain objects**, extracted directly from HTTP response headers.

After execution, you can inspect:

```java
query.getRateLimits()
     .map(XRateLimit::limit)
     .ifPresent(l -> System.out.println("Limit: " + l));

query.getRateLimitRemaining()
     .map(XRateLimitRemaining::remaining)
     .ifPresent(r -> System.out.println("Remaining: " + r));

query.getRateLimitPolicy()
     .map(XRateLimitPolicy::policy)
     .ifPresent(p -> System.out.println("Policy: " + p));

```

This enables **custom retry, throttling, and backoff strategies** without hiding transport details or coupling behavior to a specific HTTP client.

---

## Offline & File-Based Deserialization

JBrave response models are **not coupled to live HTTP execution**.

Each main API response type provides static factory methods for:

* Deserialization from **local JSON files**
* Deserialization from **raw JSON strings**
* Offline parsing without any HTTP client
* Replaying or caching responses

```java
import net.ygbstudio.jbrave.api.response.WebSearchApiResponse;

File json = new File("web-response.json");

WebSearchApiResponse response =
        WebSearchApiResponse.from(json);

// Or from raw JSON
WebSearchApiResponse response =
        WebSearchApiResponse.from(jsonString);
```

### Common Use Cases

* 🧪 Unit and integration tests without network access
* 📦 Cached or replayed API responses
* 🧠 Offline analysis and transformation
* 🔄 Debugging and benchmarking

> API responses are treated as **first-class domain objects**, not transient network artifacts.

---

## Query Builders

Each Brave Search vertical has a dedicated builder:

| Builder                | Purpose                  |
| ---------------------- | ------------------------ |
| `BraveWebQuery`        | Web search               |
| `BraveImageQuery`      | Image search             |
| `BraveNewsQuery`       | News search              |
| `BraveVideoQuery`      | Video search             |
| `BraveSuggestQuery`    | Autocomplete suggestions |
| `BraveSpellcheckQuery` | Spellcheck               |

All builders:

* Enforce query length and option constraints
* Prevent duplicate or conflicting options
* Fail fast when required fields are missing

---

## Options API

All user-facing configuration types live under:

```
net.ygbstudio.jbrave.api.options
```

Examples include:

* `Country`
* `MarketLocale`
* `SearchLanguage`
* `Units`

---

## Use Cases

JBrave is a deliberate, opinionated abstraction over the Brave Search API, modeling queries, execution, responses, and failures as a single explicit lifecycle. Transport details remain accessible and inspectable, but are not mandatory — allowing consumers to engage at the level of abstraction that best fits their use case.

### 🔍 Search-Powered Applications

* Backend services and microservices integrating Brave Search
* Internal tools and dashboards
* Search gateways or aggregation layers
* Privacy-respecting alternatives to traditional search providers

### 🤖 AI & LLM Tooling

JBrave is particularly well-suited for **AI- and ML-driven workflows** where search results feed downstream processing pipelines:

* Retrieval-augmented generation (RAG)
* Search-based prompt enrichment for LLMs
* Ranking, filtering, or re-scoring layers
* Deterministic query generation for evaluation and benchmarking
* Offline replay of recorded responses during development or testing
* Parsing structured response fields into embeddings, scores, or signals

By treating search responses as **durable, schema-backed domain objects**, JBrave enables reliable ingestion into AI pipelines without coupling retrieval to live HTTP execution.

### 📊 Data Collection & Analysis

* Batch jobs and scheduled crawls
* Trend analysis, monitoring, and research pipelines
* Parsing and transforming saved API responses
* Offline-friendly, data-centric workflows with minimal runtime coupling to a live search provider

---

### 🔐 Premium & Plan-Specific Features

JBrave primarily targets the **public and commonly available surfaces** of the Brave Search API.

Support for certain **premium or plan-restricted features** may be limited or intentionally omitted.
This is a deliberate design choice: features that cannot be reliably exercised and validated without access to the corresponding API plans are not exposed by default.

As a result:

* Some premium-only fields or behaviors may not yet be modeled
* Certain plan-specific capabilities may be missing or partially supported
* Experimental or undocumented features are intentionally excluded except for search operators, 
  which are supported due to their practical utility.

The goal is to ensure that all exposed functionality is **well-defined, verifiable, and stable**, rather than speculative or untested.

*If you have access to premium API plans and/or are interested in contributing to additional features, feedback and contributions are always welcome.*

---

> ⚠️ **Unofficial & Independent**
>
> JBrave is an independent, community-focused Java project built around the Brave Search API.
> It is **not affiliated with, endorsed by, or supported by Brave Software, Inc.**
>
> The library is still evolving and may introduce breaking changes.

---

## License

Apache License 2.0
© 2025–2026 YGBStudio
