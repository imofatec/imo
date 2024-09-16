# Docker
- Você precisará ter docker instalado em seu computador para criar um container com a imagem do MongoDB
```
cd backend/
docker-compose up -d
```

# SECRET KEY
- Acesse o site: [RSA KEY GENERATOR](https://www.csfieldguide.org.nz/en/interactives/rsa-key-generator/)
- **SELECIONE 2048 bits no Key Size**
- **SELECIONE PKCS #8 (base64) no Format Scheme** e gere a public e private key
- No diretório `imo/backend/src/main/resources`
- Crie o arquivo **app.pub** coloque nele a chave PUBLICA
- Crie o arquivo  **app.key** coloque nele a chave PRIVADA
- Se os passos acima não forem seguidos corretamente o servidor não irá rodar

# Run
```
cd backend/
./mvnw spring-boot:run
```
# Swagger UI
- Documentação API 
```
http://localhost:8080/swagger-ui/index.html
```
# Apresentação Slide
- 
```
https://docs.google.com/presentation/d/1D0Epi524--hr2keVAwWy6qi8Vjv1uEhd/edit?usp=sharing&ouid=114885402757744866925&rtpof=true&sd=true
```

