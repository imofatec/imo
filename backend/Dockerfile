FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

COPY . .

RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder build/target/backend-0.0.1-SNAPSHOT.jar ./backend.jar

ENTRYPOINT ["java", "-jar", "/app/backend.jar"]
