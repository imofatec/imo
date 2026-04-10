# Backend

## Pré-requisitos

- Java 21
- MongoDB em execução
- RabbitMQ em execução

## Setup local

### 1. Acesse o diretório

```bash
cd backend
```

### 2. Configure propriedades locais

O backend roda com o perfil `dev` por padrão.

Para alterar configurações no desenvolvimento local, a forma mais prática é editar ou criar o arquivo `src/main/resources/application-dev.properties`. Como esse arquivo fica fora de versionamento, você pode sobrescrever propriedades definidas em `application.properties` sem precisar configurar variáveis de ambiente locais.

Exemplo:

```properties
server.port=8081
spring.data.mongodb.database=imo_dev
frontend.client.url=http://localhost:5173
email.username=meu email
email.password=minha senha de app
```

Use variáveis de ambiente apenas se preferir esse modelo de configuração ou se estiver integrando o backend com outro ambiente.

### 3. Gere as chaves JWT

O backend espera os arquivos `src/main/resources/app.pub` e `src/main/resources/app.key`.

```bash
./gen-keys.sh
```

## Execução

### Rodar a aplicação

```bash
./mvnw spring-boot:run
```

### Rodar e popular banco de dados

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--seed=true --users=100 --courses=15"
```

### Subir dependências com Docker

O `docker-compose.yml` deste diretório sobe MongoDB e RabbitMQ.

```bash
docker compose up -d
```

## Build e testes

```bash
./mvnw clean package
./mvnw test
```

## Formatação

```bash
./mvnw spotless:apply
```

## Documentação da API

Com a aplicação rodando localmente:

- Scalar UI: <http://localhost:8080/docs>
- Swagger UI: <http://localhost:8080/swagger-ui.html>
- OpenAPI JSON: <http://localhost:8080/v3/api-docs>
