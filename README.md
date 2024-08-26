# Docker
- Você precisará ter docker instalado em seu computador para criar um container com a imagem do MongoDB
```
cd backend/
docker-compose up -d
```

# Secret Keys
- Acesse o site: `https://www.csfieldguide.org.nz/en/interactives/rsa-key-generator/`
- **SELECIONE 2048 bits no Key Size**
- **SELECIONE PKCS #8 (base64) no Format Scheme** e gere a public e private key
- Se os passos acima não forem seguidos corretamente o servidor não irá rodar
- No diretório `imo/backend/src/main/resources`
- Crie o arquivo **app.pub** coloque nele a chave publica
- Crie o arquivo  **app.key** coloque nele a chave privada

# Run
```
cd backend/
./mvnw spring-boot:run
```
