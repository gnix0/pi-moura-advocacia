# API Moura Advocacia

Documentacao da API responsavel pela autenticacao de usuarios e pelo gerenciamento de clientes do projeto Moura Advocacia.

## Visao geral

A aplicacao foi desenvolvida em Java 21 com Spring Boot 3 e esta organizada como um projeto Maven multi-modulo. O modulo `bootstrap` sobe a aplicacao, enquanto `user` e `customer` concentram as funcionalidades de autenticacao e cadastro de clientes.

Principais capacidades:

- cadastro de usuarios
- autenticacao com JWT
- consulta do usuario autenticado
- cadastro, listagem, atualizacao e remocao de clientes
- validacao de entrada e tratamento padronizado de erros

## Stack tecnica

- Java 21
- Spring Boot 3.2.4
- Spring Web
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT (`jjwt`)
- MapStruct
- Maven
- Docker / Docker Compose

## Estrutura dos modulos

```text
api/
├── pom.xml
├── bootstrap/
├── customer/
└── user/
```

### `bootstrap`

Modulo de inicializacao da aplicacao.

- contem a classe `Launch`, ponto de entrada do Spring Boot
- agrega os modulos funcionais `customer` e `user`
- centraliza configuracoes da aplicacao

### `customer`

Modulo responsavel pelo dominio de clientes.

- adapters REST para expor endpoints HTTP
- servico de aplicacao com regras de negocio
- portas de entrada e saida seguindo arquitetura hexagonal
- persistencia com Spring Data JPA

### `user`

Modulo responsavel por autenticacao e usuarios.

- registro de usuario
- login com geracao de token JWT
- resolucao do usuario autenticado
- configuracao de seguranca stateless com Spring Security

## Arquitetura

A API segue uma separacao inspirada em arquitetura hexagonal:

- `adapter.inbound.rest`: controllers, requests, responses e mapeamentos da camada HTTP
- `application.service`: implementacao dos casos de uso
- `domain.model`: entidades de dominio
- `domain.port.inbound`: contratos de entrada dos casos de uso
- `domain.port.outbound`: contratos de saida para persistencia e servicos externos
- `adapter.outbound.persistence`: adaptadores JPA, entidades e repositories

Fluxo resumido:

1. o controller recebe a requisicao HTTP
2. o mapper converte DTOs para o modelo de dominio
3. o service executa as regras de negocio
4. o adapter de persistencia conversa com o banco via Spring Data JPA
5. a resposta e devolvida em DTO proprio da camada REST

## Seguranca

A autenticacao usa JWT e a aplicacao opera sem sessao (`stateless`).

- rotas publicas: `POST /auth/register` e `POST /auth/login`
- demais rotas exigem token Bearer valido
- senhas sao armazenadas com `Argon2PasswordEncoder`
- o token usa as propriedades `jwt.secret` e `jwt.expiration-ms`

## Configuracao

As principais propriedades sao carregadas por variaveis de ambiente:

| Variavel | Descricao | Exemplo |
| --- | --- | --- |
| `SERVER_PORT` | porta da API | `8080` |
| `JWT_SECRET` | segredo usado para assinar os tokens | valor Base64 |
| `JWT_EXPIRATION_MS` | expiracao do token em ms | `86400000` |
| `DB_NAME` | nome do banco PostgreSQL | `moura_advocacia` |
| `DB_USER` | usuario do banco | `admin` |
| `DB_PASSWORD` | senha do banco | `admin` |
| `DB_PORT` | porta exposta do PostgreSQL | `5432` |
| `SPRING_PROFILE` | perfil do Spring usado no compose | `docker` |

O arquivo [`api/.env`](/home/gnix0/developer/studies/college/pi-moura-advocacia/api/.env) ja traz um conjunto padrao para execucao com Docker Compose.

## Como executar

### Opcao 1: Docker Compose

Executar a partir da raiz do projeto:

```bash
docker compose up --build
```

Com isso:

- a API sobe na porta definida por `SERVER_PORT`
- o PostgreSQL sobe com os valores definidos em `api/.env`
- o perfil Spring ativo passa a ser `docker`

### Opcao 2: Maven

Se o banco ja estiver disponivel, a API pode ser iniciada pela raiz `api/`:

```bash
mvn -pl bootstrap -am spring-boot:run
```

Para esse modo, garanta que as variaveis de banco e JWT estejam definidas no ambiente.

## Endpoints

Base URL padrao:

```text
http://localhost:8080
```

### Autenticacao

#### `POST /auth/register`

Cria um novo usuario.

Exemplo de corpo:

```json
{
  "email": "admin@moura.com",
  "firstName": "Ana",
  "lastName": "Moura",
  "password": "senha123"
}
```

Resposta `201 Created`:

```json
{
  "message": "Usuario cadastrado com sucesso."
}
```

#### `POST /auth/login`

Autentica um usuario e retorna um token JWT.

Exemplo de corpo:

```json
{
  "email": "admin@moura.com",
  "password": "senha123"
}
```

Resposta `200 OK`:

```json
{
  "token": "jwt-token",
  "userId": "00000000-0000-0000-0000-000000000000",
  "email": "admin@moura.com",
  "firstName": "Ana",
  "lastName": "Moura"
}
```

#### `GET /auth/me`

Retorna os dados do usuario autenticado.

Header obrigatorio:

```http
Authorization: Bearer <token>
```

Resposta `200 OK`:

```json
{
  "id": "00000000-0000-0000-0000-000000000000",
  "email": "admin@moura.com",
  "firstName": "Ana",
  "lastName": "Moura"
}
```

### Clientes

Todas as rotas abaixo exigem autenticacao.

#### `POST /customers`

Cadastra um cliente.

```json
{
  "email": "cliente@exemplo.com",
  "firstName": "Joao",
  "lastName": "Silva",
  "phoneNumber": "(11) 99999-9999"
}
```

Resposta `201 Created`:

```json
{
  "message": "Cliente cadastrado com sucesso."
}
```

#### `GET /customers`

Lista todos os clientes.

Resposta `200 OK`:

```json
[
  {
    "id": "00000000-0000-0000-0000-000000000000",
    "email": "cliente@exemplo.com",
    "firstName": "Joao",
    "lastName": "Silva",
    "phoneNumber": "(11) 99999-9999"
  }
]
```

#### `GET /customers?query=texto`

Filtra clientes por nome ou e-mail.

#### `PUT /customers/{id}`

Atualiza parcialmente um cliente existente.

```json
{
  "email": "cliente@novoemail.com",
  "firstName": "Joao",
  "lastName": "Souza",
  "phoneNumber": "(11) 98888-7777"
}
```

Resposta `200 OK`:

```json
{
  "message": "Cliente atualizado com sucesso."
}
```

#### `DELETE /customers/{id}`

Remove um cliente.

Resposta `200 OK`:

```json
{
  "message": "Cliente removido com sucesso."
}
```

## Validacoes e regras de negocio

### Usuarios

- e-mail obrigatorio e validado por formato
- senha obrigatoria com minimo de 8 caracteres
- e-mail normalizado em minusculas antes da persistencia
- nao permite duplicidade de usuario por e-mail

### Clientes

- `email`, `firstName`, `lastName` e `phoneNumber` sao obrigatorios no cadastro
- nao permite dois clientes com o mesmo e-mail
- consultas aceitam filtro opcional por nome ou e-mail
- atualizacao preserva campos nao enviados

## Tratamento de erros

Os modulos `customer` e `user` possuem handlers dedicados para transformar excecoes de negocio em respostas HTTP padronizadas.

Codigos mais comuns:

- `400 Bad Request`: dados invalidos ou falha de validacao
- `401 Unauthorized`: autenticacao invalida ou ausente
- `404 Not Found`: recurso nao encontrado
- `409 Conflict`: conflito de unicidade
- `500 Internal Server Error`: falha inesperada

Exemplo de erro:

```json
{
  "timestamp": "2026-04-05T00:00:00.000+00:00",
  "message": "Ja existe um cliente cadastrado com este e-mail.",
  "details": "uri=/customers"
}
```

## Testes

Existem testes unitarios nos modulos funcionais:

- `customer/src/test`
- `user/src/test`

Para executar:

```bash
mvn test
```
