# 💰 Finances Backend

Sistema de gerenciamento de finanças pessoais desenvolvido com Quarkus.

## 📋 Sobre o Projeto

Aplicação backend para controle financeiro que permite:

- ✅ Registrar receitas e despesas
- 📊 Categorizar transações
- 📅 Consultar transações por período

## 🏗️ Arquitetura

O projeto utiliza **Arquitetura Hexagonal (Ports and Adapters)**

```
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

## 🚀 Tecnologias

- **Java 17+**
- **Quarkus 3.x** - Framework supersônico
- **Hibernate/JPA** - ORM
- **H2 Database** - Banco de dados em arquivo
- **Flyway** - Migração de banco
- **RESTEasy** - API REST

- ## 📦 Pré-requisitos

- **Java 17** ou superior
- **Maven 3.8+** (ou use o wrapper `mvnw`)

## ⚙️ Como Executar

### 1️⃣ Modo Desenvolvimento (Hot Reload)

```bash
./mvnw quarkus:dev
```

A aplicação estará disponível em: http://localhost:8080

### 📚 API Endpoints

## Transações

```json
POST /transactions
Content-Type: application/json

{
  "accountId": 1,
  "categoryId": 1,
  "type": "EXPENSE",
  "amount": 150.00,
  "description": "Compra supermercado",
  "transactionDate": "2024-01-15"
}
```

## Listar Transações

```json
GET /transactions?startDate=2024-01-01&endDate=2024-01-31
```

## Buscar por ID

```json
GET /transactions/{id}
```

## Atualizar Transação

```json
PUT /transactions/{id}
Content-Type: application/json

{
  "amount": 200.00,
  "description": "Compra atualizada"
}
```

## Deletar Transação
```json
DELETE /transactions/{id}
```

### 💾 Banco de Dados

O projeto utiliza H2 Database em arquivo persistente:

- Arquivo: finances.mv.db
- Console H2: http://localhost:8080/h2-console (em dev mode)
- JDBC URL: jdbc:h2:file:./data/finances
- Usuário: sa
- Senha: (vazia)

## Migrações Flyway

As migrações SQL estão em migration:

- V1__init.sql - Schema inicial
- V2__seed_dev.sql - Dados de desenvolvimento
