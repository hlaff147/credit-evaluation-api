# API de Avaliação de Crédito: Desafio Neurotech

Esta é uma API RESTful desenvolvida em **Java** com **Spring Boot**, criada para o desafio de desenvolvedores da Neurotech. Ela avalia e aplica diferentes modalidades de crédito a clientes Pessoa Física (PF) com base em critérios de negócio específicos, incluindo elegibilidade para crédito automotivo.

---

## Sumário
- [Arquitetura e Patterns](#arquitetura-e-patterns)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar a Aplicação](#como-executar-a-aplicacao)
- [Documentação da API (Swagger UI)](#documentacao-da-api-swagger-ui)
- [Endpoints e Exemplos de Uso](#endpoints-e-exemplos-de-uso)
- [Validação e Tratamento de Erros](#validacao-e-tratamento-de-erros)
- [Testes Automatizados e Patterns](#testes-automatizados-e-patterns)
- [Dicas de Desenvolvimento](#dicas-de-desenvolvimento)
- [Roadmap e Melhorias Futuras](#roadmap-e-melhorias-futuras)

---

## Arquitetura e Patterns

O projeto adota a **Arquitetura Hexagonal (Ports and Adapters)**, promovendo separação entre domínio e infraestrutura. Principais patterns e práticas:
- **DTO Pattern** para requests/responses
- **Service Layer** para lógica de negócio
- **Global Exception Handler** (`@ControllerAdvice`)
- **Validação declarativa** (Bean Validation)
- **Testes organizados por camada** (unitário, componente, integração)
- **Builder Pattern** (Lombok)

### Estrutura de Pacotes
```
src/main/java/com/neurolake/challenge/credit_evaluation_api/
├── application/
│   ├── domain/        # Modelos, serviços e regras de negócio
│   └── ports/         # Ports (interfaces) de entrada e saída
└── infrastructure/
    ├── adapter/
    │   ├── in/web/    # Adaptadores de entrada (controllers)
    │   └── out/       # Adaptadores de saída (persistência, engines)
    └── config/        # Configurações Spring Boot
```

---

## Tecnologias Utilizadas
- **Java 24**
- **Spring Boot 3.5.0** (Web, Data JPA, Validation, Test)
- **H2 Database** (testes/dev) e **MySQL** (produção)
- **Lombok**
- **SpringDoc OpenAPI (Swagger UI)**
- **Maven**
- **Testcontainers** (integração)

---

## Como Executar a Aplicação

1. **Pré-requisitos:** JDK 24+, Maven 3.6+
2. **Build e execução:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
3. **Acesse:**
   - API: `http://localhost:8080`
   - Swagger: `http://localhost:8080/swagger-ui.html`
   - H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:creditdb`, User: `sa`)

---

## Documentação da API (Swagger UI)
Acesse a documentação interativa em `/swagger-ui.html` para explorar todos os endpoints, exemplos de request/response e erros.

---

## Endpoints e Exemplos de Uso

### Cadastro de Cliente
**POST /api/clients**

Request:
```json
{
  "name": "João da Silva",
  "age": 35,
  "income": 10000.0
}
```
Response 201 Created:
Headers:
```
Location: http://localhost:8080/api/clients/65
Content-Type: application/json
```
Body:
```json
{
  "id": 65,
  "name": "João da Silva",
  "age": 35,
  "income": 10000.0,
  "eligibleCreditTypes": ["VARIABLE_INTEREST"]
}
```

### Buscar Cliente por ID
**GET /api/clients/{id}**

Response 200 OK:
```json
{
  "id": 65,
  "name": "João da Silva",
  "age": 35,
  "income": 10000.0,
  "eligibleCreditTypes": ["VARIABLE_INTEREST"]
}
```

### Listar Todos os Clientes
**GET /api/clients**

Response 200 OK:
```json
[
  { "id": 65, "name": "João da Silva", ... },
  ...
]
```

### Avaliar Crédito Automotivo
**GET /api/clients/{clientId}/automotive-credit**

Response 200 OK:
```json
{
  "clientName": "João da Silva",
  "eligibleForHatch": true,
  "eligibleForSUV": false
}
```

### Listar Clientes Elegíveis para Crédito Fixo e Hatch
**GET /api/clients/eligible-fixed-hatch**

Response 200 OK:
```json
[
  { "Name": "Ana Pereira", "Income": 10000.0 },
  ...
]
```

---

## Validação e Tratamento de Erros

- **Validação automática** via anotações nos DTOs (`@NotBlank`, `@Min`, `@DecimalMin` etc).
- **Erros de validação** retornam JSON padronizado:
```json
{
  "timestamp": "2025-06-08T01:08:34.091311",
  "status": 400,
  "error": "Validation Error",
  "validationErrors": {
    "income": "Income must be a positive value",
    "name": "Name cannot be blank",
    "age": "Age must be a positive number"
  }
}
```
- **404 Not Found** para recursos inexistentes:
```json
{
  "timestamp": "2025-06-08T01:10:00.000000",
  "status": 404,
  "error": "Resource Not Found",
  "message": "Cliente não encontrado"
}
```
- **Handler global**: todos os erros são tratados por um `@ControllerAdvice` centralizado.

---

## Testes Automatizados e Patterns

- **Testes de componente**: simulam requests HTTP reais (MockMvc), cobrindo fluxos completos e cenários de erro.
- **Testes de integração**: usam Testcontainers e banco real (MySQL/H2) para validar persistência e endpoints.
- **Testes unitários**: cobrem regras de negócio isoladas (ex: elegibilidade por idade/renda).
- **Fixtures**: geração de dados de teste reutilizáveis.
- **Padrão de nomenclatura**: `deve<Acao>Quando<Condicao>`.
- **Cobertura de cenários de erro**: todos os campos obrigatórios e regras possuem testes de validação.

### Como rodar os testes
```bash
./mvnw test
```
Relatórios em `target/surefire-reports`.

---

## Dicas de Desenvolvimento
- **Validação declarativa**: use sempre as anotações do Bean Validation.
- **Tratamento global de erros**: mantenha o padrão JSON para facilitar o consumo por frontends.
- **Extensibilidade**: novas regras de crédito podem ser adicionadas implementando a interface de regra.
- **Atualize o Swagger/OpenAPI** ao criar/alterar endpoints.
- **Use DTOs** para requests/responses, nunca exponha entidades diretamente.

---

## Roadmap e Melhorias Futuras
- Autenticação e autorização (Spring Security)
- Versionamento de API
- Deploy automatizado (CI/CD)
- Métricas e monitoramento (Actuator, Prometheus)
- Testes de carga e performance

---

## Contribuição
Pull requests são bem-vindos! Siga o padrão de testes e mantenha a cobertura.

---

## Contato
Dúvidas ou sugestões? Abra uma issue ou entre em contato.

---
