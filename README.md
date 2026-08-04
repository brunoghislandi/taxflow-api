# 📊 Taxflow API

A **Taxflow API** é um serviço RESTful corporativo desenvolvido em Java para o gerenciamento, estruturação e sincronização de dados de tributos (Federais, Estaduais e Municipais). O projeto foi desenhado com foco em escalabilidade, manutenibilidade e aplicação de boas práticas de engenharia de software (Clean Code e SOLID).

---

## 🛠️ Tecnologias Utilizadas

O projeto foi construído utilizando o que há de mais moderno no ecossistema Java:

*   **Java 21**
*   **Spring Boot 4.1.0** (Spring Web, Spring Data JPA, Validation)
*   **PostgreSQL** (Banco de dados relacional)
*   **MapStruct** (Mapeamento de alta performance entre Entidades e DTOs)
*   **Lombok** (Redução de código boilerplate)
*   **Springdoc OpenAPI 3** (Swagger para Documentação interativa)
*   **Maven** (Gerenciador de dependências)

---

## 🏗️ Arquitetura e Padrões Aplicados

A API segue uma arquitetura em camadas (MVC) estritamente definida, garantindo o isolamento de responsabilidades:

*   **Controller Layer:** Responsável exclusivamente por receber as requisições HTTP, delegar o processamento e retornar os *status codes* adequados.
*   **Service Layer:** Contém toda a regra de negócio da aplicação.
*   **Repository Layer:** Responsável pela comunicação com o banco de dados (Spring Data JPA).
*   **Padrão DTO (Data Transfer Object):** Isolamento total entre os modelos de banco de dados (`Domain`) e os contratos de API de entrada e saída.
*   **Tratamento Global de Exceções:** Uso de `@ControllerAdvice` para capturar erros (como `404 Not Found` ou `400 Bad Request` por validação) e padronizar os retornos em JSON.

---

## 🚀 Como Executar o Projeto Passo a Passo

### 1. Pré-requisitos
Antes de começar, você precisará ter instalado em sua máquina:
*   [JDK 21](https://adoptium.net/)
*   [Maven](https://maven.apache.org/)
*   [PostgreSQL](https://www.postgresql.org/) (Rodando na porta padrão `5432`)

### 2. Configuração do Banco de Dados
Abra o seu PostgreSQL (via pgAdmin ou terminal) e crie um banco de dados vazio com o nome `taxflow`:
```sql
CREATE DATABASE taxflow;
```

Em seguida, no projeto clonado, navegue até o arquivo `src/main/resources/application.properties` e certifique-se de que as credenciais correspondem ao seu banco local:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/taxflow
spring.datasource.username=seu_usuario_do_postgres
spring.datasource.password=sua_senha_do_postgres
```

### 3. Instalação das Dependências
Abra o terminal na raiz do projeto e execute o comando abaixo para baixar as dependências e gerar as classes do MapStruct (isso evitará erros de compilação):
```bash
mvn clean install
```

### 4. Rodando a Aplicação
Com o build finalizado com sucesso, inicie o servidor:
```bash
mvn spring-boot:run
```
O servidor iniciará na porta `8080`. Ao subir, um **Database Seeder** (`TestDatabaseSeeder.java`) injetará automaticamente 12 tributos reais no seu banco de dados para facilitar os testes e validações visuais.

---

## 📚 Documentação da API (Swagger)

A API possui uma documentação interativa rica, gerada automaticamente, permitindo explorar os endpoints, visualizar os schemas e realizar requisições de teste diretamente pelo navegador.

A documentação foi personalizada para refletir um padrão corporativo.

*   **Interface Visual (Swagger UI):** [http://localhost:8080/documentacao](http://localhost:8080/documentacao)
*   **Caminho do JSON (OpenAPI Docs):** [http://localhost:8080/service-layer-tributos/docs](http://localhost:8080/service-layer-tributos/docs)

---

## 📍 Tabela de Endpoints

Abaixo estão as rotas disponíveis no **Service Layer** para a manipulação dos recursos de tributos:

| Método | Rota | Descrição |
| :--- | :--- | :--- |
| **POST** | `/tributos` | Cadastra um novo tributo. |
| **GET** | `/tributos` | Lista todos os tributos de forma paginada (ex: `?page=0&size=5&sort=nome,asc`). |
| **GET** | `/tributos/{id}` | Busca os detalhes de um tributo específico pelo ID. |
| **PUT** | `/tributos/{id}` | Atualiza integralmente os dados de um tributo (Substituição). |
| **PATCH** | `/tributos/{id}` | Atualiza parcialmente os campos de um tributo, mantendo os demais intactos. |
| **DELETE** | `/tributos/{id}` | Remove permanentemente um tributo da base de dados. |