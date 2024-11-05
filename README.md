# Spring Boot and REST API Project

This project implements a Spring Boot service that stores data in a PostgreSQL database and provides access through REST API endpoints for Entity 1 and Entity 2.

## Context

The project utilizes the domain chosen in Task 1. Entity 1 (primary for us) relates as many-to-one to Entity 2. Attributes or their types may be modified compared to Task 1 due to the creation of a new project.

## Tasks

- Develop a Spring Boot service with the following endpoints:

### Entity 1 Endpoints:

- `POST /api/entity1`: Creates a new Entity 1 record.
- `GET /api/entity1/{id}`: Retrieves detailed data of an Entity 1 record, including data from Entity 2 it refers to.
- `PUT /api/entity1/{id}`: Updates data of an Entity 1 record by ID.
- `DELETE /api/entity1/{id}`: Deletes an Entity 1 record by ID.
- `POST /api/entity1/_list`: Returns a data structure containing a list of Entity 1 items based on pagination and optional filtering criteria.
- `POST /api/entity1/_report`: Generates and offers to download a report file (Excel or CSV) containing all records matching the filtering criteria.
- `POST /api/entity1/upload`: Accepts a JSON file for bulk import of Entity 1 records into the database.

### Entity 2 Endpoints:

- `GET /api/entity2`: Retrieves a list of all existing Entity 2 records.
- `POST /api/entity2`: Creates a new Entity 2 record, ensuring the uniqueness of names.
- `PUT /api/entity2/{id}`: Updates an Entity 2 record by ID, considering the uniqueness of names.
- `DELETE /api/entity2/{id}`: Deletes an Entity 2 record by ID.

## Integration Tests

Each endpoint should be covered by integration tests to ensure functionality and reliability.

## Liquibase Script

Include a Liquibase script that creates the necessary database schema and provides initial data for Entity 2 upon application startup.

## JSON Data File

Provide a JSON file in the repository for data import, synchronized with the Liquibase script for data population.

Make sure to maintain consistency between the JSON file and the Liquibase script.

Feel free to modify the content or structure according to your project's specific requirements.

$$ ProductionCostsPerGram = {50(75) / 1000 \over 1000} $$
