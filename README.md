# Backend for Oportunia
The Oportunia represents a distributed system based on layers and interactions with different components.

## Skills 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![JWT Security](https://img.shields.io/badge/Security-JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
![Dagger](https://img.shields.io/badge/DI-Dagger-FF4081?style=for-the-badge&logo=dagger&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)
![SQL](https://img.shields.io/badge/Language-SQL-4479A1?style=for-the-badge&logo=sqlite&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![OpenAI](https://img.shields.io/badge/API-OpenAI-412991?style=for-the-badge&logo=openai&logoColor=white)



## App Architecture

![OportuniaApp_System](diagrams/TaskApp_System.png)


### Persistent or Data Layer

The persistence layer contains components for data access, such as an **object-relational mapping** (**ORM**) tool.

The objective is to interact with the data base.

The main objects are the Entities and the relationship between them.

### Data Base

PostgreSQL: The World's Most Advanced Open Source Relational Database

![taskapp-er](diagrams/ER.png)

# General terms

## Distributed system

This backend is built using an N-Tier (multi-layer) architecture, which clearly separates
responsibilities between presentation, business logic, domain, and data. Its purpose is to
provide secure and scalable REST services that interact with the mobile frontend, while also
integrating smart features using the OpenAI API

## Key Features

- **JWT-based authentication and authorization** (JSON Web Tokens)
- **Well-structured REST services**
- **Integration with OpenAI API** for content generation and intelligent processing
