
# Cosmos Backend

Este projeto é um exemplo de uma aplicação Spring Boot para uma loja de livros. Ele utiliza Java 21, Gradle 7.5.1, Spring Boot 3.3.1, Spring Cloud 2023.0.2, Spring Security e SpringDoc Open API 2.6.0. O banco de dados utilizado é MySQL.

## Pré-requisitos

- Java 21
- Gradle 7.5.1 (utilizando gradle wrapper)
- MySQL (última versão)
- Docker (opcional, mas recomendado)

## Configuração do Ambiente

### Passo 1: Clonar o Repositório

```sh
git clone https://bitbucket.org/ginfodesenv/cosmos-backend.git
cd cosmos-backend
```

### Passo 2: Configurar Variável de Ambiente

Defina a variável de ambiente para setar o profile "local" do Spring.

```sh
export SPRING_PROFILES_ACTIVE=local
```

### Passo 3: Configurar o Banco de Dados

#### Opção 1: Usando Docker

Se você tiver Docker instalado, pode usar o seguinte comando para rodar um contêiner MySQL:

```sh
docker run --name cosmos-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=cosmos_db -e MYSQL_USER=cosmos_user -e MYSQL_PASSWORD=cosmos123 -p 3306:3306 -d mysql:latest
```

#### Opção 2: Instalação Manual do MySQL

1. Instale o MySQL seguindo as instruções para seu sistema operacional.
2. Crie um banco de dados chamado `cosmos_db`.

```sql
CREATE DATABASE cosmos_db;
CREATE USER 'cosmos_user'@'localhost' IDENTIFIED BY 'cosmos123';
GRANT ALL PRIVILEGES ON cosmos_db.* TO 'cosmos_user'@'localhost';
FLUSH PRIVILEGES;
```

### Passo 4: Executar Scripts de Inicialização do Banco de Dados

Execute o script `/src/main/resources/db/init.sql` para inicializar o banco de dados.

```sh
mysql -u cosmos_user -p cosmos123 < PATH_TO_FILE/init.sql
```

### Passo 5: Rodar a Aplicação

Use o Gradle para rodar a aplicação:

```sh
./gradlew bootRun
```

### Passo 6: Acessar a Documentação da API

A documentação da API estará disponível em:

```
http://localhost:9090/docs/swagger-ui.html
```

## Testes

### Executar Testes

Para executar os testes, use o seguinte comando:

```sh
./gradlew test
```

### Testar com Autenticação

Os testes estão configurados para usar a anotação `@WithMockUser` para simular a autenticação. Certifique-se de que a configuração de segurança está correta e que as roles apropriadas são atribuídas aos usuários simulados nos testes.

---