# IMO - Plataforma para cursos de TI
Este projeto está sendo desenvolvido na FATEC Diadema como parte do curso Desenvolvimento de Software Multiplataforma (DSM)

# Integrantes
- [Abner Bispo Cerqueira](https://github.com/AbnerCerqueira)
- [Daniel de Azevedo Cordeiro](https://github.com/DanielDeAzevedoCordeiro1)
- [Guilherme Ferreira Monteiro](https://github.com/frrr04)
- [Marcus Antonio da Rocha Filho](https://github.com/4mxi)
- [Matheus Nicolas Mariano](https://github.com/MathNicolas)
- [Nikolas da Palma Cruz](https://github.com/Vastobode)

# Slide Apresentação 5º Semestre
[Canva](https://www.canva.com/design/DAG4iddbOpE/UivgeUHadNnixAJjMDzzYQ/edit?utm_content=DAG4iddbOpE&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton)

[Informações sobre o projeto](https://github.com/imofatec/imo/wiki/IMO-%E2%80%90-Plataforma-para-cursos-de-TI)

## Tecnologias utilizadas
> API
- **Linguagem**: [Java 21](https://www.java.com/pt-BR/)
- **Framework**: [Spring Boot](https://spring.io/projects/spring-boot)
- **Autenticação**: [JWT](https://jwt.io/)
- **Banco de dados**: [MongoDB](https://www.mongodb.com/)
- **Message Broker**: [RabbitMQ](https://www.rabbitmq.com/)
> Interface
- **Linguagem**: [JavaScript](https://developer.mozilla.org/pt-BR/docs/Web/JavaScript)
- **Frameworks**: [React](https://react.dev/) + [TailwindCSS](https://tailwindcss.com/)
- **Componentes**: [shadcn/ui](https://ui.shadcn.com/)

## Requisitos
> Rodar o projeto na sua máquina para **desenvolver**
- Java 21
- MongoDB
- RabbitMQ

## Clone
> Rode no terminal para baixar o projeto
```
git clone https://github.com/imofatec/imo.git
cd imo
```

## Setup back
> Acesse o diretório do backend
```
cd backend
```

### Profile
> Ative o perfil de desenvolvimento em `imo/backend/src/main/resources/application.properties`
```
spring.profiles.default=prod
spring.profiles.active=dev

```

### Envs
Crie um arquivo em `imo/backend/src/main/resources` chamado `.env-dev.properties` e adicine neles as variáveis de ambiente necessárias
Exemplo:
```
MONGO_URI=mongodb://localhost:27017/imo
CLIENT_URL=http://localhost:5173
RABBITMQ_ADDRESS=amqp://localhost:5672
EXCHANGE_NAME=imo.user
ROUTING_KEY_CONFIRMATION_EMAIL=imo.user.confirmation_email
ROUTING_KEY_FORGET_PASSWORD=imo.user.forget_password
```

### Criptografia assimétrica
> Necessário para JWT

> Opção 1
- Execute `./backend/gen-keys.sh`

> Opção 2
- Acesse o site: [RSA KEY GENERATOR](https://www.csfieldguide.org.nz/en/interactives/rsa-key-generator/)
- **Selecione 2048 bits no Key Size**
- **Selecione PKCS #8 (base64) no Format Scheme** e gere a public e private key
- Acesse o diretório `imo/backend/src/main/resources/`
- Crie o arquivo **app.pub** coloque nele a chave PUBLICA
- Crie o arquivo  **app.key** coloque nele a chave PRIVADA

### Spring Boot
> Opção 1
```
./mvnw spring-boot:run

```
> Opção 1 populando o banco
```
./mvnw spring-boot:run -Dspring-boot.run.arguments="--seed=true --users=100 --courses=15"
```

> Opção 2
```
./mvnw clean install -DskipTests \
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### API docs
Endpoints documentados e interface para testar a API
- Scalar UI http://localhost:8080/docs
- Swagger UI http://localhost:8080/swagger-ui/index.html


## Setup Front
### Envs
Crie um arquivo em `imo/frontend` chamado `.env` e adicione neles as variáveis de ambiente necessárias
> Necessário para conexão com backend

Exemplo:
```
VITE_API_BASE_URL=http://localhost:8080
```
### Run
> Inicie o projeto e acesse http://localhost:5173
```
cd frontend
npm install 
npm run dev
```
