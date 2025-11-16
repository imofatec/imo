FROM alpine/java:21-jdk AS builder

WORKDIR /build

COPY . .

RUN apk add --no-cache curl && \
    curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.8.0/opentelemetry-javaagent.jar \
        -o opentelemetry-javaagent.jar && \
    apk del curl

RUN cd backend && ./mvnw clean install -DskipTests

FROM alpine/java:21-jdk

WORKDIR /app

COPY --from=builder /build/opentelemetry-javaagent.jar ./opentelemetry-javaagent.jar

COPY --from=builder /build/backend/target/backend-0.0.1-SNAPSHOT.jar ./backend.jar

COPY --from=builder /build/otel-sampler/target/otel-sampler-1.0.0.jar ./otel-sampler.jar

ENV OTEL_JAVAAGENT_EXTENSIONS=/app/otel-sampler.jar
ENV OTEL_TRACES_SAMPLER=http_method_filter

ENTRYPOINT ["java", "-javaagent:/app/opentelemetry-javaagent.jar", "-jar", "/app/backend.jar"]
