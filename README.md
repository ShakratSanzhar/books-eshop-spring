# Eshop
This project is the second version of Eshop of books. The first project was written using serlvets. 

## The technology stack used in this project:
- Java 17
- SpringBoot (Spring Web, Spring Security, Spring Data, Spring Test, Spring Validation)
- Hibernate
- PostgreSQL + Liquibase
- Apache Maven
- Thymeleaf

## Instructions for launching the project:
1. Download [this](https://github.com/ShakratSanzhar/books-eshop-spring) repository
2. In the .env file, specify the following environment variables:
   - HOST - host of Postgresql database
   - POSTGRES_USERNAME - username for Postgresql database
   - POSTGRES_PASSWORD - password for Postgresql database
   - POSTGRES_DATABASE - name of Postgresql database
   - CLIENT_ID - id of client (OAuth 2.0) to authorize through google authorization server (to recieve, need registration on cloud.google.com)
   - CLIENT_SECRET - secret of client (OAuth 2.0) to authorize through google authorization server (to recieve, need registration on cloud.google.com)
3. ./mvnw clean install -Dmaven.test.skip=true
4. docker-compose build
5. docker-compose up -d
6. The application is available at: [http://localhost:8080](http://localhost:8080)


### P.S.
My Telegram for communication: [https://t.me/gss0911](https://t.me/gss0911)
#### I wish all of you good luck!
