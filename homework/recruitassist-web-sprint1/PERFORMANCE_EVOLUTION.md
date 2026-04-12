# Sprint 1 Performance Evolution Notes

This Sprint 1 project intentionally keeps several simple implementations so later iterations can demonstrate measurable optimization work.

Current optimization opportunities:

1. `src/main/java/com/recruitassist/util/JsonFileStore.java`
The Sprint 1 version reads JSON files fresh on every request and does not keep in-memory caches. This keeps the implementation simple, but it increases disk I/O as data grows.

2. `src/main/java/com/recruitassist/web/LoginServlet.java`
The login page repeatedly asks `UserService` for role-based lists and totals instead of reusing one aggregated result. This is acceptable for a small demo dataset, but it does extra repository reads.

3. `src/main/java/com/recruitassist/web/HomeServlet.java`
The home page recomputes counts and featured users every time the page loads. A later sprint can combine these reads or cache summary data.

4. `src/main/java/com/recruitassist/service/ApplicationService.java`
`countByJobId()` recalculates counts by scanning all application records for each dashboard request. Later sprints can introduce pre-aggregated counts or cached summaries.

5. `src/main/java/com/recruitassist/service/JobService.java`
`listAllJobs()` re-reads and revalidates job status from storage on every request. Later iterations can separate status synchronization from normal reads.

Suggested later optimizations:

- Add read-through caching for JSON file and directory reads.
- Reuse dashboard aggregates within a request instead of recomputing them.
- Add pagination or lazy loading when job/application volume grows.
- Add precomputed summary files or lightweight indexes for frequently used counts.
