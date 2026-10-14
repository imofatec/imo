# Arquitetura IMO — Decisões e trade-offs

> Detalhe das decisões resumidas no `README.md`. Aqui fica a versão completa para banca e novos devs.

## 1. Estilo arquitetural

Monólito modular em `backend/src/main/java/com/imo/backend/contexts/` com 7 contextos:
Identidade, Catálogo, Jornada (`journey_tracking`), Aprendizagem (`learning_path`),
Social, Reconhecimento e Notificação.

Por que não microsserviços? Time pequeno de projeto integrador, 1 ano, 3 frentes. Microsserviços
trocariam fronteira lógica (que já temos via pacotes) por custo de rede, versionamento,
observabilidade e deploys coordenados. O monólito permite extrair um contexto depois
se houver motivo real de escala independente.

Estrutura interna por contexto: `http` → `usecases` → `repositories` + domínio
(entidades, `*Policies`, value objects como `CourseName`, `Level`). A regra
(ex: `CoursePolicies`, `LessonPolicies`, `AchievementPolicies`) fica no domínio para
valer igual na web e no mobile.

Não é DDD formal (sem Aggregate Root / Bounded Context estrito), mas usa a ideia
central: regra explícita no código, não só em anotação `@Valid`.

## 2. Tabela completa de decisões

| Decisão | Por que | Descartado |
| :------ | :------ | :--------- |
| Monólito modular | Fronteira sem custo distribuído | Microsserviços: overkill operacional |
| Separação HTTP / usecase / repo / domínio | Mesma regra para web e mobile | CRUD no controller: duplica validação |
| Domínio rico (policies/invariantes) | Ex: aula fora de ordem, conquista dupla | Só `@Valid`: cobre formato, não negócio |
| Eventos via RabbitMQ | `concluir aula` rápido mesmo se e-mail/recomendação falhar | Cascata síncrona: e-mail fora do ar derruba progresso |
| MongoDB Atlas + índices (`MongoIndexesE2ETest`) | Documento flexível, leitura por ID/usuário, tier free | Postgres puro: joins melhores, mas schema rígido para este acesso |
| JWT stateless + Spring Security | Web + mobile sem sessão, escala horizontal no ECS | Sessão server-side: sticky session ou Redis só para login |
| Erros padronizados | Front trata uma vez | Um formato por controller |
| OpenAPI + Scalar em `/docs` | Contrato vivo para web/mobile/banca | Collection Postman no Drive: desatualiza rápido |
| Testes unidade + integração (Testcontainers) + E2E (RestAssured) + UI (Playwright) + JaCoCo | Regra quebrada silenciosamente é pior que bug visual | Só manual: regressão entre Catálogo ↔ Jornada ↔ Reconhecimento |
| CI em PR + CD `develop`→homolog, `master`→prod | Demo estável sem "na minha máquina" | Deploy SSH manual |
| AWS ECS/ECR + S3/CloudFront + OIDC + SSM | Sem gerenciar EC2, front barato, sem secret fixa no repo | VPS único: mistura tudo e vaza segredo |

## 3. Eventos entre contextos

Ver `context-events.png` (gerado de `context-events.puml`):

- Catálogo → Jornada: `LessonListUpdatedEvent` (progresso reavaliado)
- Catálogo → Aprendizagem: `CourseCreated`, `CourseSkillIdsChanged`, `CourseStatusToggled`
- Jornada → Aprendizagem: `CourseFinishedEvent`
- Jornada → Reconhecimento: `LessonWatchedEvent`, `CourseFinishedEvent`
- Reconhecimento → Notificação: `AchievementUnlockedEvent`

Notificação (e-mail via Spring Mail + SSE via `useAchievementNotificationStream`) só reage.
Se cair, o núcleo (assistir, progredir) continua.

## 4. Por que RabbitMQ e não eventos internos do Spring?

Alternativa honesta: `@EventListener` / Spring Modulith resolveria a comunicação
dentro do monólito com menos infra (sem CloudAMQP, sem fila).

Escolhemos RabbitMQ porque:

1. E-mails de Identidade (`confirmEmail`, `forgetPassword` em `RabbitMQConfig.java`)
   precisam sobreviver a restart/deploy — fila durável (`QueueBuilder.durable()`)
   dá isso, evento em memória perde.
2. Deixa o caminho aberto para extrair Notificação/Reconhecimento depois sem
   reescrever publishers.
3. É didático para a banca: mostra mensageria real (exchange, routing key, converter JSON).

Custo assumido: mais um serviço para subir local (`docker compose up`) e no Atlas/CloudAMQP,
mais latência e mais um ponto de falha. Para um projeto integrador só com eventos de domínio em memória,
o Spring interno seria mais simples — registramos aqui como simplificação válida.

Estado atual (limitação conhecida): sem retry com backoff, sem DLQ e sem idempotência
no consumidor. Se o consumidor falhar após o commit do progresso, o reprocessamento pode
duplicar efeito colateral (ex: e-mail duplo, segunda checagem de conquista). Ver seção
Limitações no README para o plano.
