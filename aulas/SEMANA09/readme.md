# sprint init

https://start.spring.io/

Spring Web
H2 Database
Spring Data JPA

---

# aplication.properties

## Nome do projeto
spring.application.name=crudproject

## Mudança do número da porta padrão 8080
server.port=8090


## Configuracao para o banco de dados H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

## Habilitar o console do H2
spring.h2.console.enabled=true

## Caminho para acessar o console do H2
spring.h2.console.path=/h2-console

## Configurar o JPA para criar e atualizar o esquema automaticamente
spring.jpa.hibernate.ddl-auto=update

---

# Alternativa para postman

Thunder Client do vscode

