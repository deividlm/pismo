[![Build Status](https://app.travis-ci.com/deividlm/pismo.svg?token=k8ofDSLLcpSTRmtjzdot&branch=main)](https://app.travis-ci.com/deividlm/pismo)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/660813869027400ea8abe729171b25f2)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=deividlm/pismo&amp;utm_campaign=Badge_Grade)
[![Coverage Status](https://coveralls.io/repos/github/deividlm/pismo/badge.svg?branch=main)](https://coveralls.io/github/deividlm/pismo?branch=main)
# Pismo Back Test 3.0
API de Rotina de Transações.

## Descrição
Cada portador de cartão (cliente) possui uma conta com seus dados.
A cada operação realizada pelo cliente uma transação é criada e associada à sua respectiva conta.
Cada transação possui um tipo (compra a vista, compra parcelada, saque ou pagamento), um valor e uma data de criação.
Transações de tipo compra e saque são registradas com valor negativo, enquanto transações de pagamento são registradas com valor positivo.

## Tecnologias/Framework

- [Java 8](https://www.oracle.com/br/java/)
- [Spring Boot - 2.6.3](https://spring.io/projects/spring-boot)
- [Spring Hateoas](https://spring.io/projects/spring-hateoas)  
- [JUnit 5](https://junit.org/junit5/docs/current/user-guide/)
- [OpenAPI 3 - Swagger](https://swagger.io/docs/)
- [H2 Database](https://www.h2database.com/html/quickstart.html)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Padrões de implementação
- Factory method para facilitar a manutenção e evolução
- Camadas (Model,Service,Controller)
- DTO para transferência de dados entre as camadas
- ControllerAdvice para tratamento adequado de exceções

## Passo-a-passo uso da aplicação

#### Clone do repositório no github:
- https://github.com/deividlm/pismo.git

#### Empacotando o projeto com [maven](https://maven.apache.org/download.cgi)
- mvn clean package

#### adicionando o projeto no docker
a partir do diretório raiz do projeto executar o seguinte comando
- docker-compose up -d --build

#### Removendo o projeto do docker
a partir do diretório raiz do projeto executar o seguinte comando
- docker-compose down -v

## Open API - Swagger

- UI: http://localhost:8080/swagger-ui
- Docs: http://localhost:8080/api-docs


## Executando os testes

- via maven => mvn test

##Cobertura dos testes
-No topo da página no lado esquerdo tem um link para o coveralls


##Acessar o banco de dados H2

-Endereço: http://localhost:8080/h2

-JDBC URL: jdbc:h2:mem:pismo-test  
-User Name: sa  
-Password: (deixar em branco)

##Postman
-Primeiro é necessário criar uma conta para passar o ID da conta nas transações  
-Usar o ID retornado da conta criada no endpoint de transação  

--Tipos de transações suportadas no campo "transactionType"  
CASH  
INSTALLMENTS  
WITHDRAWN  
PAYMENT  



