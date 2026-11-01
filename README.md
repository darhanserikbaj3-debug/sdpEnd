# Assignment 6: Smart City Monitoring System (Observer and Visitor Patterns)

## Overview
This project is an implementation of a real-time **Smart City Monitoring System** built with **Java 17** and **JUnit 5**[cite: 1]. The system demonstrates a thread-safe **Observer Pattern** for processing live sensor events and a **Visitor Pattern** operating over a 3-level **Composite Tree** representing city infrastructure[cite: 1].

---

## Key Architecture & Design Decisions

### 1. Observer Pattern Design Decisions
* **Custom Generic Interfaces:** Implemented generic `Subject<T, E>` and `Observer<E>` interfaces to enforce strict type safety without raw types or `Object` casting[cite: 1].
* **Push vs. Pull Model Justification:** 
  * **Selected Model:** **Push Model**[cite: 1].
  * **Justification:** Sensors push immutable `SensorEvent` objects directly to subscribed listeners upon crossing critical threshold values. This eliminates continuous polling loops from subscribers, reduces unnecessary CPU overhead, and guarantees real-time notification for safety-critical alerts[cite: 1].
* **Chain of Observers:** The `DistrictAggregator` acts as both an `Observer` (listening to raw sensor alerts) and a `Subject` (republishing aggregated district averages to other subscribers)[cite: 1].

### 2. Visitor Pattern Design Decisions
* **Composite Tree Hierarchy:** A 3-level tree structure[cite: 1]:
  * **Container Nodes (2 types):** `City` (Level 1), `District` (Level 2)[cite: 1].
  * **Leaf Nodes (4 types):** `LightSensor`, `TrafficCamera`, `AirQualityDevice`, `WaterLevelStation`[cite: 1].
  * Element classes strictly hold data only and contain zero business logic[cite: 1].
* **Tree Traversal Ownership Justification:**
  * **Selected Model:** **Visitor-driven Traversal**[cite: 1].
  * **Justification:** Each `Visitor` controls its own navigation through the Composite tree[cite: 1]. This enables flexible algorithms (e.g., stopping traversal early during filtering) without polluting element classes with traversal logic[cite: 1].

### 3. Integration Link (Observer + Visitor)
When an `AirQualitySensor` publishes an alert event, the `InspectionAlertService` listener intercepts it, locates the specific `District` subtree corresponding to the alert's district ID, and triggers a `MaintenanceInspectionVisitor` **only on that subtree**[cite: 1]. The inspection result is then reported back to the system[cite: 1].

### 4. Concurrency & Thread Safety
* **Locking Strategy:** Shared listener registries in `AbstractSubject` utilize `ConcurrentHashMap` combined with `CopyOnWriteArrayList`[cite: 1].
* **Benefits:** This prevents coarse-grained locks on the entire `Subject` while allowing subscribers to register or unregister dynamically at runtime—including from within their own event handlers—without throwing `ConcurrentModificationException`[cite: 1].
* **Graceful Shutdown:** Implemented via `ExecutorService.shutdown()` and `awaitTermination()` in multi-threaded environments to process all queued events without hanging threads[cite: 1].

---

## Trade-off Proof (Git Commit Analysis)

As required by the assignment specification, the Git commit history contains two distinct commits proving the architectural trade-offs of the Visitor Pattern[cite: 1]:

---

## UML Class Diagram

City Sensor Observer Visitor Architecture-2026-11-01-130150.png