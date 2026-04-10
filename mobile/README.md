# Mobile

## Pré-requisitos

- Node.js
- npm
- Expo CLI via `npx`
- backend da IMO rodando e acessível pela rede do dispositivo ou emulador

## Setup local

### 1. Acesse o diretório

```bash
cd mobile
```

### 2. Instale as dependências

```bash
npm install
```

### 3. Configure a URL da API

O app lê a API a partir de `expo.extra.API_URL` em `app.json`.

Exemplo:

```json
{
  "expo": {
    "extra": {
      "API_URL": "http://SEU_IP_LOCAL:8080"
    }
  }
}
```

Substitua `SEU_IP_LOCAL` pelo IP da sua máquina quando estiver testando em dispositivo físico ou emulador que não compartilha `localhost` com o backend.

## Execução

```bash
npm run start
npm run android
npm run ios
npm run web
```

## Observações

- O arquivo `mobile/src/api/enviroment.js` lê a API a partir de `Constants.expoConfig?.extra?.API_URL`.
- Em dispositivo físico, `localhost` normalmente não funciona para acessar o backend da sua máquina.
