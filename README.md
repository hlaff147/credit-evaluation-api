# API de Avaliação de Crédito: Desafio Neurotech

Esta é uma API RESTful desenvolvida em **Java** com **Spring Boot**, criada para o desafio de desenvolvedores da Neurotech. Ela foi projetada para avaliar e aplicar diferentes modalidades de crédito a clientes Pessoa Física (PF) com base em critérios de negócio específicos. Além disso, a API determina a elegibilidade de clientes para crédito automotivo.

---

## Arquitetura

O projeto adota a **Arquitetura Hexagonal (Ports and Adapters)**, garantindo uma clara separação entre a lógica de negócio central (o **domínio**) e os detalhes de infraestrutura (como banco de dados e APIs externas). Essa abordagem promove um código mais testável, flexível e resiliente a mudanças.

### Estrutura de Pacotes

A estrutura de pacotes reflete as camadas da arquitetura hexagonal:

```
src/main/java/
└── com/
└── neurolake/
└── challenge/
└── creditevaluation/  # Pacote raiz do projeto
├── application/
│   ├── domain/        # Core do domínio: modelos, serviços e regras de negócio puras
│   │   ├── model/
│   │   ├── service/
│   │   └── rules/
│   └── ports/         # Definições de Ports (interfaces) de entrada e saída
│       ├── in/
│       └── out/
└── infrastructure/    # Adaptadores: implementações dos ports e detalhes de infraestrutura
├── adapter/
│   ├── in/        # Adaptadores de entrada (e.g., camada web)
│   │   └── web/
│   └── out/       # Adaptadores de saída (e.g., persistência)
│       ├── persistence/
│       └── creditengine/
└── config/        # Configurações gerais do Spring Boot
```
*A estrutura de pacotes acima reflete a organização interna e pode ser ajustada conforme a sua implementação exata `com.neurolake.challenge.credit_evaluation_api`.*

---

## Tecnologias Chave

Este projeto foi construído utilizando as seguintes tecnologias:

* **Java 24**: Linguagem de programação.
* **Spring Boot 3.5.0**: Framework para construção de aplicações robustas.
    * **Spring Web**: Criação de APIs RESTful.
    * **Spring Data JPA**: Abstração para persistência de dados.
    * **Spring Boot Validation**: Validação declarativa de dados.
    * **Spring Boot Test**: Ambiente completo para testes automatizados.
* **H2 Database**: Banco de dados em memória para desenvolvimento e testes.
* **Lombok**: Redução de boilerplate code (Getters/Setters, Construtores, etc.).
* **SpringDoc OpenAPI (Swagger UI) 2.3.0**: Geração automática e interativa da documentação da API.
* **Maven**: Ferramenta de gerenciamento de dependências e build.

---

## Requisitos para Execução

Para rodar este projeto, você precisará ter instalado:

* **JDK 24** ou superior
* **Maven 3.6.x** ou superior

---

## Como Executar a Aplicação

Siga os passos abaixo para colocar a API no ar:

1.  **Clone seu fork do repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/credit-evaluation-api.git](https://github.com/seu-usuario/credit-evaluation-api.git)
    cd credit-evaluation-api
    ```
2.  **Compile e inicie a aplicação via Maven:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
A API estará acessível em `http://localhost:8080`.

---

## Documentação da API (Swagger UI)

A documentação interativa completa dos endpoints está disponível através do Swagger UI:
`http://localhost:8080/swagger-ui.html`

O console do H2 Database, para inspeção dos dados em memória, pode ser acessado em:
`http://localhost:8080/h2-console`
*Credenciais: JDBC URL: `jdbc:h2:mem:creditdb` (verifique seu `application.properties`), User: `sa`, Password: (deixe em branco)*

---

## Endpoints da API

A API foi desenvolvida seguindo os princípios RESTful, utilizando códigos de status HTTP apropriados e validação de dados de entrada.

### Clientes e Créditos Principais

* **`POST /api/client`**: **Registra um novo cliente** e, no processo, avalia e salva todos os tipos de crédito para os quais ele é elegível.
    * **Request Body Exemplo**:
        ```json
        {
            "name": "João Silva",
            "age": 30,
            "income": 7500.00
        }
        ```
    * **Respostas Esperadas**:
        * `201 Created` - Em caso de sucesso.
        * **Header**: `Location: http://localhost:8080/api/client/{id_do_cliente}` (URL para acessar o cliente recém-criado).

* **`GET /api/client/{id}`**: **Retorna os dados detalhados de um cliente** específico, incluindo a lista de `eligibleCreditTypes` (tipos de crédito para os quais ele é elegível).
    * **Path Parameter**: `{id}` - O identificador único do cliente.
    * **Respostas Esperadas**:
        * `200 OK` - Com um objeto JSON do cliente.
        * `404 Not Found` - Se o cliente não existir.

* **`GET /api/client`**: **Lista todos os clientes** cadastrados na aplicação.
    * **Respostas Esperadas**:
        * `200 OK` - Com uma lista de objetos JSON de clientes.

### Crédito Automotivo

* **`GET /api/client/{clientId}/automotive-credit`**: **Avalia a elegibilidade de um cliente para crédito automotivo**, especificamente para veículos Hatch e SUV.
    * **Path Parameter**: `{clientId}` - O identificador único do cliente.
    * **Respostas Esperadas**:
        * `200 OK` - JSON indicando a elegibilidade para cada tipo de veículo.
        * `404 Not Found` - Se o cliente não existir.

### Endpoint Adicional (Critério Combinado)

* **`GET /api/client/eligible-fixed-hatch`**: **Lista clientes específicos** que atendem a uma combinação de critérios:
    * Idade entre **23 e 49 anos** (inclusive).
    * Elegíveis para **Crédito com Juros Fixos**.
    * Elegíveis para **Crédito Automotivo tipo Hatch**.
    * **Respostas Esperadas**:
        * `200 OK` - Com uma lista de objetos JSON contendo `Name` e `Income` de cada cliente qualificado.

---

## Regras de Negócio Implementadas

As modalidades de crédito e as elegibilidades automotivas são baseadas nos seguintes critérios:

### 1. Modalidades de Crédito Principais (Múltipla Elegibilidade)

Um cliente pode ser elegível para **múltiplos** tipos de crédito principais, e todos são retornados.

* **Crédito com Juros Fixos**:
    * **Critério**: Idade entre **18 e 25 anos** (inclusive).
    * **Renda**: Independente.
    * **Taxa de Juros**: 5% a.a.

* **Crédito com Juros Variáveis**:
    * **Critério**: Idade entre **21 e 65 anos** (inclusive).
    * **Renda**: Entre **R$ 5.000,00 e R$ 15.000,00** (inclusive).

* **Crédito Consignado**:
    * **Critério**: Idade **acima de 65 anos**.
    * **Renda**: Independente.

### 2. Elegibilidade para Crédito Automotivo

* **Veículo Tipo Hatch**:
    * **Critério**: Renda entre **R$ 5.000,00 e R$ 15.000,00** (inclusive).

* **Veículo Tipo SUV**:
    * **Critério**: Renda **acima de R$ 8.000,00** E idade **superior a 20 anos**.

---

## Testes Automatizados

O projeto inclui testes automatizados para garantir a robustez e o correto funcionamento dos endpoints e das regras de negócio, utilizando as ferramentas padrão do Spring Boot Test.

