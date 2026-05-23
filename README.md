# IMO

Software multiplataforma de aprendizagem que organiza vídeos técnicos da área de desenvolvimento de software do YouTube, em cursos com progresso, interação e recomendação.

## Visão geral

Aprender tecnologia pela internet costuma significar navegar por muito conteúdo relevante, porém disperso, sem sequência clara e sem acompanhamento de progresso. Plataformas como o YouTube concentram material de qualidade, mas não foram desenhadas para organizar cursos de um nicho específico.

A IMO foi criada para atuar como uma camada de curadoria sobre esses vídeos, transformando o consumo solto em uma experiência de estudo mais guiada. Além de organizar cursos e aulas, a plataforma acompanha o progresso, mantém sinais da jornada do usuário e passa a recomendar os próximos cursos com base em skills e lacunas de proficiência.

## Como a IMO funciona

Na prática, a plataforma permite que usuários organizem vídeos, já publicados na internet, em cursos. A partir disso, outros usuários podem consumir as aulas, acompanhar a própria evolução e interagir com a comunidade.

Hoje, a IMO oferece:

- Cursos e aulas organizados em sequência
- Acompanhamento de progresso por aula e por curso
- Comentários em aulas
- Perfil público com atividades e marcos compartilhados
- Conquistas desbloqueadas ao longo da jornada
- Recomendações orientadas por skills e lacunas de proficiência
- Notificações assíncronas e em tempo real
- Experiência integrada entre web e mobile

## Decisões de engenharia e maturidade técnica

O projeto foi desenvolvido com preocupações explícitas de arquitetura, qualidade e evolução incremental. A implementação segue a ideia de um monólito modular, com organização interna por contextos de negócio e fronteiras mais claras entre responsabilidades.

- Organização do backend por contextos de negócio, evitando uma estrutura única centrada em CRUD
- Separação entre camada HTTP, casos de uso, repositórios, integrações e regras de domínio
- Inspiração em domínio rico, com entidades, policies e invariantes encapsuladas no modelo
- Comunicação assíncrona por eventos para fluxos entre catálogo, jornada, reconhecimento, recomendação e notificação
- Tratamento padronizado de erros e respostas da API
- Documentação OpenAPI com Swagger UI e Scalar UI
- Testes automatizados em múltiplas camadas: unidade, integração, E2E e UI
- Integração contínua em Pull Requests
- Deploy contínuo para homologação e produção
- Infraestrutura e entrega automatizadas em AWS

Embora o projeto não tenha sido implementado formalmente como DDD, houve uma preocupação consciente em enriquecer o domínio e em aproximar o código das regras de negócio da plataforma.

## O que este projeto demonstra

Como equipe, o projeto demonstra capacidade de:

- Modelar um produto com múltiplos domínios de negócio
- Desenvolver uma solução full stack com backend, web e mobile
- Transformar requisitos em regras de negócio explícitas no código
- Estruturar testes automatizados em diferentes níveis
- Integrar aplicações com mensageria e serviços externos
- Automatizar validação e deploy em ambientes de nuvem
- Evoluir uma plataforma para além de operações básicas de cadastro

## Estrutura do monorepo

| Diretório  | Descrição                               |
| :--------- | :-------------------------------------- |
| `backend/` | API principal em Java com Spring Boot   |
| `web/`     | Aplicação web em React + Vite           |
| `mobile/`  | Aplicação mobile em React Native + Expo |
| `docs/`    | Diagramas e artefatos de arquitetura    |

## Como rodar cada ambiente

As instruções detalhadas de execução ficam em cada aplicação:

- Backend: [`backend/README.md`](backend/README.md)
- Web: [`web/README.md`](web/README.md)
- Mobile: [`mobile/README.md`](mobile/README.md)

## Domínios da plataforma

Do ponto de vista de negócio, a plataforma está organizada em sete contextos:

- `Identidade`: cadastro, autenticação, autorização e ciclo de vida da conta
- `Catálogo`: cursos, aulas, categorias e skills associadas ao conteúdo
- `Jornada`: progresso do usuário, consumo de aulas e marcos compartilháveis
- `Aprendizagem`: perfil de skills e recomendação de próximos cursos
- `Social`: comentários e perfil público com atividade do usuário
- `Reconhecimento`: conquistas e regras de desbloqueio ao longo da jornada
- `Notificação`: orquestração assíncrona de e-mails, SSE e comunicações disparadas por eventos da plataforma

## Stack principal

| Camada         | Tecnologias                                                                                                  |
| :------------- | :----------------------------------------------------------------------------------------------------------- |
| Backend        | Java 21, Spring Boot, Maven, MongoDB, RabbitMQ, JWT, Spring Security, Spring Mail, Springdoc, Scalar, AWS S3 |
| Web            | React 19, TypeScript, Vite, React Router, Tailwind CSS 4, React Hook Form, Zod, Axios                        |
| Mobile         | React Native, Expo, Expo Router, NativeWind, React Hook Form, Zod                                            |
| Qualidade      | JUnit, Mockito, Rest Assured, Testcontainers, JaCoCo, Playwright                                             |
| Infraestrutura | Docker, GitHub Actions, AWS ECS, ECR, S3, CloudFront, ALB, IAM, SSM, MongoDB Atlas, CloudAMQP                |

## CI/CD e infraestrutura

O projeto possui pipelines separados para backend e web.

- O backend executa validação automatizada em Pull Requests e deploy contínuo por branch: homologação em `develop` e produção em `master`
- O deploy do backend utiliza Docker, Amazon ECR e Amazon ECS
- O frontend web executa testes E2E no CI e deploy em Amazon S3, com distribuição via CloudFront
- A autenticação da pipeline com a AWS utiliza OIDC, evitando credenciais estáticas no repositório

## Arquitetura e documentação complementar

- Relatório técnico: [PDF](https://drive.google.com/file/d/15P-z4X15PUKV5S0RMsh1uc-ZeS7i92vL/view?usp=sharing)
- Apresentação: [PDF](https://drive.google.com/file/d/1IAsyU9Xxz1II7nAcwYAYRNhJbmf37yGR/view?usp=sharing)
- Context Map estrutural: [`docs/context-map.puml`](docs/context-map.puml)
- Fluxo de eventos entre contextos: [`docs/context-events.puml`](docs/context-events.puml)
- C4 nível 1: [`docs/c4/context.puml`](docs/c4/context.puml)
- C4 nível 2: [`docs/c4/container.puml`](docs/c4/container.puml)
- C4 nível 3 da API: [`docs/c4/component.puml`](docs/c4/component.puml)
- C4 nível 3 da aplicação web: [`docs/c4/component-web.puml`](docs/c4/component-web.puml)
- Requisitos e regras de negócio: [Google Docs](https://docs.google.com/document/d/1ppUHqpMedrUB-X3jCE1EB0Yd62vp4r6L/edit?usp=sharing&ouid=118107849553791070174&rtpof=true&sd=true)
- Histórias de usuário e análise de risco: [Google Sheets](https://docs.google.com/spreadsheets/d/13NYt1LqwIODdfDzTfWS20svd2fkz3Mahpeb3dQzeGY0/edit?usp=sharing)

## Contexto acadêmico

- Instituição: FATEC Diadema
- Curso: Desenvolvimento de Software Multiplataforma
