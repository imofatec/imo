# Web

## Pré-requisitos

- Node.js
- npm
- backend da IMO rodando localmente

## Setup local

### 1. Acesse o diretório

```bash
cd web
```

### 2. Instale as dependências

```bash
npm install
```

### 3. Configure o arquivo `.env`

Crie o arquivo `.env` na raiz de `web/`.

```env
VITE_API_BASE_URL=http://localhost:8080
PLAYWRIGHT_BASE_URL=http://localhost:5173
```

| Variável              | Obrigatória | Uso                        |
| :-------------------- | :---------- | :------------------------- |
| `VITE_API_BASE_URL`   | sim         | URL base da API            |
| `PLAYWRIGHT_BASE_URL` | recomendada | URL usada pelos testes E2E |

## Execução

### Rodar em desenvolvimento

```bash
npm run dev
```

Aplicação disponível em: <http://localhost:5173>

## Scripts úteis

```bash
npm run build
npm run lint
npm run format
npm run format:check
npm run preview
```

## Testes E2E

```bash
npx playwright test
npx playwright show-report
```
