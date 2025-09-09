FROM alpine/java:21-jdk

COPY . .

RUN apk add --no-cache curl && \
    curl -L https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v2.8.0/opentelemetry-javaagent.jar \
        -o opentelemetry-javaagent.jar && \
    apk del curl

RUN ./mvnw clean install -DskipTests

ENTRYPOINT ["java", "-javaagent:./opentelemetry-javaagent.jar", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]