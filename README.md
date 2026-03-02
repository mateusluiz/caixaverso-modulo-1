# Finances Backend

Sistema de gerenciamento de financas pessoais desenvolvido com Quarkus.

## Sobre o Projeto

Aplicacao backend para controle financeiro que permite:

- Registrar receitas e despesas
- Categorizar transações
- Consultar transações por periodo

## Arquitetura da Aplicacao (Hexagonal)

O projeto utiliza Arquitetura Hexagonal (Ports and Adapters):

```text
📁 domain/          → Lógica de negócio
   ├── model/       → Entidades de domínio
   └── ports/       → Interfaces (contratos)

📁 app/             → Casos de uso
   ├── usecase/     → Regras
   └── dto/         → Contratos de entrada/saída

📁 infra/           → Implementações técnicas
   └── persistence/ → JPA/Hibernate
      ├── entity/   → Entidades do banco
      ├── repository/ → Implementação das portas
      └── mapper/   → Conversão domínio ↔ DB

📁 api/             → Camada REST
   └── resource/    → Endpoints HTTP
```

## Tecnologias

- Java 17+
- Quarkus 3.x
- Hibernate/JPA
- H2 Database
- Flyway
- REST (Quarkus REST)
- Nginx (camada de infraestrutura)
- Docker Compose (orquestracao local)

## Pre-requisitos

Para desenvolvimento da API (modo dev):

- Java 17 ou superior
- Maven 3.8+ (ou usar `./mvnw`)

Para camada de infraestrutura (Nginx + API):

- Docker
- Docker Compose

## Como Executar a API em Desenvolvimento (sem Nginx)

```bash
./mvnw quarkus:dev
```

Aplicação disponivel em `http://localhost:8080`.

## API Endpoints (projeto atual)

Observação: no projeto atual os endpoints estão em `/api/v1/transactions`.

### Criar transacao

```http
POST /api/v1/transactions
Content-Type: application/json
```

Exemplo de body:

```json
{
  "accountId": "22222222-2222-2222-2222-222222222222",
  "categoryId": "44444444-4444-4444-4444-444444444444",
  "type": "EXPENSE",
  "amount": 150.00,
  "date": "2026-03-01",
  "description": "Compra supermercado"
}
```

### Listar transacoes do mes

```http
GET /api/v1/transactions?month=2026-03
```

## Banco de Dados

Modo dev (rodando `quarkus:dev`):

- H2 em arquivo
- JDBC URL: `jdbc:h2:file:./data/finances;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH`
- Console H2 (dev): `http://localhost:8080/h2`

Modo Docker (API no compose):

- H2 em memoria (configurado via `docker-compose.yml`)

## Migracoes Flyway

As migracoes SQL ficam em `src/main/resources/db/migration`:

- `V1__init.sql` - schema inicial
- `V2__seed_dev.sql` - dados de desenvolvimento

<br>
<br>
<br>
<br>
<br>
<br>

---

## Versão 2 -> Camada Nginx + Docker (trabalho de infraestrutura)

Arquitetura usada nesta etapa:

```text
Cliente -> Nginx (porta 80) -> Load Balancer -> API Quarkus (app1/app2, porta 8080 interna)
```

### Arquivos importantes

- `docker-compose.yml`: sobe API + Nginx
- `nginx/nginx.conf`: configuracao principal do Nginx
- `nginx/errors/404.html`: pagina customizada para 404
- `nginx/errors/50x.html`: pagina customizada para 502/503/504
- `logs/nginx/access.log`: log gerado em runtime
- `postman/API + Nginx (CaixaVerso).postman_collection.json`: colecao pronta com testes

### docker-compose (resumo)

Servicos `app1` e `app2`:

- montam duas instancias identicas da API
- exposição apenas interna (`8080`), sem publicar no host

Servico `nginx`:

- publica porta `80` no host
- usa `nginx.conf`
- grava logs em `logs/nginx`

## nginx.conf (o que cada bloco faz)

Bloco `http`:

- `log_format` + `access_log`: log estruturado
- `limit_req_zone rate=5r/s`: limite por IP
- `limit_req_status 429`: excedeu limite, retorna 429
- `proxy_cache_path`: cache de GET
- `gzip on` + `gzip_types`: compressao para JSON e texto

Bloco `server`:

- `listen 80`
- `client_max_body_size 1m` (retorna 413 se exceder)
- `add_header ...`: headers de seguranca
- `error_page 404 ...` e `error_page 502 503 504 ...`

Bloco `location /`:

- aplica rate limit com burst 10
- encaminha headers de proxy para API
- aplica cache GET por 10s
- `X-Cache-Status`: MISS/HIT/BYPASS
- `X-Upstream-Addr`: mostra qual instancia respondeu (app1 ou app2)
- `proxy_pass http://finances_backend` (upstream com load balancer)

## Como subir API + Nginx

1. Build da API:

```bash
./mvnw clean package -DskipTests
```

2. Subir ambiente:

```bash
docker compose up --build -d
```

3. Ver containers:

```bash
docker compose ps
```

4. Teste rapido via Nginx:

```bash
curl -i "http://localhost/api/v1/transactions?month=2026-03"
```

## Testes no Postman

### Importar

1. Importe `postman/API + Nginx (CaixaVerso).postman_collection.json`

### Ordem sugerida

1. `Inserir Transação Entrada`
2. `Inserir Transação Saída`
3. `01 - Reverse proxy (200)`
4. `02 - Security headers`
5. `03 - Gzip`
6. `04 - Payload > 1MB (413)`
7. `05 - Cache sem Authorization (1)`
8. `06 - Cache sem Authorization (2)`
9. `07 - Cache com Authorization (BYPASS)`
10. `08 - Custom 404`
11. `09 - Rate limit (Runner)`
12. `10 - Custom 50x (parar app1 e app2)`
13. `11 - Load balancer`

### Detalhes

`09 - Rate limit (Runner)`:

- rodar no Collection Runner com 25 iteracoes
- a validacao espera pelo menos um status 429

`10 - Custom 50x (parar app1 e app2)`:

- antes: `docker compose stop app1 app2`
- depois: `docker compose start app1 app2`
- esperado: 502 (ou 503/504) com pagina HTML customizada

`11 - Load balancer (Runner simples)`:

- rode no Collection Runner com 10 iteracoes e delay de 200ms
- cada iteracao mostra no resultado de testes: `Upstream atual: appX:8080`
- no final valida: `Passou por app1 e app2 em algum momento`

### Como validar o load balancer

1. Rode varias chamadas com cache bypass:

```bash
for i in {1..10}; do
  curl -s -D - -o /dev/null -H "Authorization: Bearer test" "http://localhost/api/v1/transactions?month=2026-03" | grep -i "X-Upstream-Addr"
done
```

2. Resultado esperado:
- cabecalho alternando entre `app1:8080` e `app2:8080` (round-robin / circular).

## Evidencias

Log estruturado:

```bash
tail -n 20 logs/nginx/access.log
```

Comprovar que a API nao esta publica direto na 8080 do host:

```bash
curl -i "http://localhost:8080/api/v1/transactions?month=2026-03"
```

Encerrar ambiente:

```bash
docker compose down
```
