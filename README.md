# Order ECommerce SpringBoot Java App

Rewritten application from https://github.com/egen-engineering/ecommerce-kotlin

This service interacts with postgresDB to perform CRUD ops for order data

#### Make sure Java 17 and Docker is installed on your machine (https://www.docker.com/products/docker-desktop/)

## Building the app
```
     ./gradlew clean build
```
## Running the app : (choose one of the following method)

#### 1. Run docker-compose to start both postgres and springboot app
```
    docker-compose up   
    docker container ls 
```
#### 2. Run the app in IDE while postgres runs in the docker container
```
      docker-compose up postgres
```
Make appropriate changes to datasource in application.yml file
Start the SpringBootApp in IDE using the following env args

    -Dspring.datasource.orderecommercedb.url=jdbc:postgresql://localhost:5432/orderecommerce_db?currentSchema=public
    -Dspring.datasource.orderecommercedb.name=orderecommerceuser
    -Dspring.datasource.orderecommercedb.password=orderecommercepwd
    -Dspring.datasource.orderecommercedb.hikari.connectiontimeout=60000
    -Dspring.datasource.orderecommercedb.hikari.maximumpoolsize=5
    -Dspring.datasource.orderecommercedb.hikari.poolname=orderecommercedb
    -Dspring.datasource.orderecommercedb.hikari.autocommit=false
    -Dservice.name=orderecommerce
    -Dspring_application_context_path=/order-ecommerce

Note:

* All the env args are specified in the docker-env-file
* PostgresSQL startup create script is specified within DockerCompose [setup.sql](setup.sql)
* Verify running instances from Docker Desktop

## Swagger : Use to validate API end points
http://localhost:8080/order-ecommerce/swagger-ui/index.html
