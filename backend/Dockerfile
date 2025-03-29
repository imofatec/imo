FROM alpine/java:21-jdk
RUN apk add --no-cache openssl
COPY . .
RUN openssl genpkey -algorithm RSA -out ./src/main/resources/app.key -pkeyopt rsa_keygen_bits:2048
RUN openssl rsa -pubout -in /src/main/resources/app.key -out /src/main/resources/app.pub
RUN ./mvnw clean install -DskipTests
ENTRYPOINT ["java", "-jar", "target/backend-0.0.1-SNAPSHOT.jar"]
