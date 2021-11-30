## Requirements
- Java 16
- Maven
- PostgresSQL

> Change <...> to setting from application.properties, <version> to version from pom.xml

## Create database in postgresql
`create database currency;`  
`create user <spring.datasource.username> with encrypted password '<spring.datasource.password>';`  
`grant all privileges on database currency to curradmin;`  

## Run by spring boot from 'converter' folder
`mvn spring-boot:run`

## Run by java from 'converter' folder
`mvn clean package`  
`java -jar target/<version>.jar`

## In the browser address bar
`localhost:<server.port>`

## Login and password
`<spring.security.user.name>`  
`<spring.security.user.password>`

## demo-version
https://easy-converter.herokuapp.com

login-admin, password-1573