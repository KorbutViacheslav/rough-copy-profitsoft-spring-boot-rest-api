# Spring Boot REST API Project. Book Management Application.
* [Task issued](https://docs.google.com/document/d/15W34SUZ0n3D_NGzUL9njgQROZdyWHlVFHQx5kK8YZ_M/edit?hl=ru#heading=h.arwffw3rrrrx) (Only for users who have access to the internship)
* A short video about how the application works [YouTube](https://www.youtube.com/watch?v=AxmRCDS6iU4)
---
### Application Description
This is a Spring Boot application that provides a RESTful API 
for managing authors and books. The application allows you to create,
read, update, and delete authors and books, as well as perform 
additional operations such as pagination, report generation, 
and bulk import of books from a JSON file.
---
### Features

* Create, read, update, and delete authors
* Create, read, update, and delete books
* Retrieve paginated list of books with filtering options
* Generate reports in CSV or Excel format based on filters
* Upload books from a JSON file and save valid entries to the database
---
### Usage

**Run**
- [SpringBootRestApiApplication](src/main/java/ua/profitsoft/roughcopyprofitsoftspringbootrestapi/RoughCopyProfitsoftSpringBootRestApiApplication.java)

**Database Initialization**
- The application utilizes Liquibase [scripts](src/main/resources/db/changelog/V1__init.yml) to manage the database schema.
Upon application startup, the necessary database schema is created along
with [initial](src/main/resources/db/changelog/V2__insert_authors.yml) data for authors.

**Import Books from JSON**
- To import books from a JSON file, you can use the provided [books.json](src/main/resources/json_file/books.json) file
in the repository. This file is compatible with the Liquibase script for 
seeding the Book table.
- Send a [POST](http://localhost:8080/api/book/upload) request with the books.json file as the request body.

**Use Swagger for surf application**
- [Swagger](http://localhost:8080/swagger-ui/index.html#/)
---
### Controllers
**AuthorController**

    Endpoints:
        POST /authors: Create a new author.
        GET /authors/{id}: Retrieve an author by ID.
        PUT /authors/{id}: Update an author by ID.
        DELETE /authors/{id}: Delete an author by ID.
        GET /authors: Retrieve a list of all authors.

**BookController**

    Endpoints:
        POST /books: Create a new book.
        GET /books/{id}: Retrieve a book by ID.
        PUT /books/{id}: Update a book by ID.
        DELETE /books/{id}: Delete a book by ID.
        GET /books: Retrieve a page of books based on filters.
        GET /books/report: Generate a report for books based on filters.
        POST /books/upload: Upload books from a JSON file.

---
### Integration Testing

Each endpoint is covered by integration tests to ensure functionality and 
reliability. These tests validate the behavior of the application under 
various scenarios.
---
### Summary
The Book Management Application is a Spring Boot RESTful API that provides
CRUD operations for managing authors and books. It supports pagination,
report generation, and bulk import of books from JSON files.
The application is thoroughly tested with integration tests covering 
all endpoints to ensure correct functionality. Built with Java 17, 
Spring Boot, Spring Data JPA, Hibernate, PostgreSQL, Liquibase, and Swagger.