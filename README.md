# Setup Back
## Docker
- Temos disponível um arquivo Docker Compose caso queira um container com a imagem do MongoDB
```
cd backend/
docker-compose up -d
```

## Criptografia assimétrica
### Sem isso os Beans usados para JWT não irão funcionar
- Acesse o site: [RSA KEY GENERATOR](https://www.csfieldguide.org.nz/en/interactives/rsa-key-generator/)
- **SELECIONE 2048 bits no Key Size**
- **SELECIONE PKCS #8 (base64) no Format Scheme** e gere a public e private key
- No diretório `imo/backend/src/main/resources`
- Crie o arquivo **app.pub** coloque nele a chave PUBLICA
- Crie o arquivo  **app.key** coloque nele a chave PRIVADA
- Se os passos acima não forem seguidos corretamente o servidor não irá rodar

## Spring Boot
```
cd backend/
./mvnw spring-boot:run
```
## Swagger UI
- Documentação API 
```
http://localhost:8080/swagger-ui/index.html
```
# Setup Front
```
cd frontend/
npm install 
npm run dev
```

