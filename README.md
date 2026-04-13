# IMO

Plataforma multiplataforma de aprendizagem que organiza conteúdos técnicos externos, especialmente vídeos do YouTube, em trilhas de estudo com progresso, interação e certificação.

## Visão geral

Aprender tecnologia pela internet costuma significar navegar por conteúdos relevantes, porém dispersos, sem sequência clara e sem acompanhamento de progresso. Plataformas como o YouTube concentram muito material de qualidade, mas não foram desenhadas para organizar uma jornada estruturada de aprendizagem técnica.

A IMO foi criada para atuar como uma camada de curadoria sobre esses conteúdos, organizando links e materiais externos em cursos e trilhas com começo, meio e fim. O objetivo é transformar consumo solto de conteúdo em uma experiência de estudo mais guiada, acompanhável e integrada entre web e mobile.

## Destaques

- Transforma conteúdos dispersos em trilhas com sequência e contexto
- Reúne experiência web e mobile
- Acompanha progresso por aula e por curso
- Conecta aprendizado e interação por meio de comentários
- Combina produto educacional com decisões reais de engenharia

## Exemplos de telas

### Web

![Placeholder - Web Page](https://placehold.co/1200x675?text=IMO+Page+Example)

### Mobile

![Placeholder - Mobile page](https://placehold.co/720x1280?text=IMO+Page+Example)

## Como a IMO funciona

A IMO permite que os próprios usuário submetam e organizem conteúdos técnicos já publicados na internet em cursos e trilhas estruturadas. Em vez de consumir vídeos isolados e materiais desconectados, o usuário percorre uma jornada que já foi estruturada por outra pessoa, acompanha a evolução por aula, interage com outros usuários e pode obter certificação ao concluir um percurso.

Na prática, a plataforma oferece:

- Cursos e aulas organizados em sequência
- Acompanhamento de progresso
- Interação social por comentários
- Experiência integrada entre web e mobile

## Números do projeto

- ~30 endpoints HTTP no backend
- ~10 páginas
- ~76% cobertura de testes do backend
- ~?% cobertura de testes do frontend web
- ~?% cobertura de testes do mobile
- Pipelines de CI e CD para homologação e produção

## Decisões de engenharia e maturidade técnica

O projeto foi desenvolvido com decisões conscientes de design, arquitetura e qualidade, buscando refletir preocupações reais de engenharia além da escolha de tecnologias.

- Organização do backend por contextos de negócio, evitando uma estrutura única centrada em CRUD
- Separação entre camada HTTP, casos de uso, repositórios e regras de domínio
- Encapsulamento de comportamentos e invariantes em entidades e policies
- Uso de comunicação assíncrona para fluxos e integrações entre partes do sistema
- Tratamento padronizado de erros e respostas da API
- Documentação OpenAPI para consumo e teste da API
- Testes automatizados em múltiplas camadas
- Integração contínua executada em Pull Requests
- Deploy contínuo para ambientes distintos
- Infraestrutura e deploy automatizados em AWS

Embora o projeto não tenha sido implementado formalmente como DDD, houve uma preocupação consciente em enriquecer o domínio e aplicar orientação a objetos de forma intencional, evitando uma modelagem limitada a classes genéricas e operações básicas de CRUD.

## O que este projeto demonstra

Como equipe, o projeto demonstra capacidade de:

- Modelar um produto com múltiplos domínios de negócio
- Desenvolver uma solução full stack com backend, web e mobile
- Transformar requisitos em regras de negócio explícitas no código
- Estruturar testes automatizados em diferentes níveis
- Integrar aplicações com mensageria e serviços externos
- Automatizar validação e deploy em ambientes de nuvem

## Estrutura do monorepo

| Diretório  | Descrição                               |
| :--------- | :-------------------------------------- |
| `backend/` | API principal em Java com Spring Boot   |
| `web/`     | Aplicação web em React + Vite           |
| `mobile/`  | Aplicação mobile em React Native + Expo |

## Como rodar cada ambiente

As instruções detalhadas de execução ficam em cada aplicação:

- Backend: [`backend/README.md`](backend/README.md)
- Web: [`web/README.md`](web/README.md)
- Mobile: [`mobile/README.md`](mobile/README.md)

## Domínios da plataforma

Do ponto de vista de negócio, a plataforma está organizada em seis contextos:

- `Identidade`: cadastro, autenticação, autorização e ciclo de vida da conta
- `Catálogo`: cursos, aulas e organização do conteúdo
- `Jornada de aprendizagem`: progresso, consumo de aulas e conclusão
- `Social`: comentários e interação entre usuários
- `Certificação`: emissão, consulta e validação de certificados
- `Notificação`: orquestra o envio assíncrono de comunicações disparadas por diferentes operações da plataforma

## Stack principal

| Camada         | Tecnologias                                                      |
| :------------- | :--------------------------------------------------------------- |
| Backend        | Java 21, Spring Boot, Maven, MongoDB, RabbitMQ, JWT              |
| Web            | React, TypeScript, Vite, Tailwind CSS                            |
| Mobile         | React Native, Expo                                               |
| Qualidade      | JUnit, Mockito, Rest Assured, Testcontainers, Playwright, Vitest |
| Infraestrutura | Docker, GitHub Actions, AWS, MongoDB Atlas, CloudAMQP            |

## Demonstração e documentação complementar

- Vídeo de demonstração: [placeholder]()
- Relatório técnico: [placeholder]()
- Requisitos e regras de negócio: [placeholder](s)
- Análise de risco: [placeholder]()

## Contexto acadêmico

- Instituição: FATEC Diadema
- Curso: Desenvolvimento de Software Multiplataforma
