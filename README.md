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

A distributed system is one in which both data and transaction processing are divided between one or more computers connected by a network, each computer playing a specific role in the system.
## The Client-Server Model and Distributed Systems
The client-server model is basic to distributed systems. It is a response to the limitations presented by the traditional mainframe client-host model, in which a single mainframe provides shared data access to many dumb terminals. The client-server model is also a response to the local area network (LAN) model, in which many isolated systems access a file server that provides no processing power.

Client-server architecture provides integration of data and services and allows clients to be isolated from inherent complexities, such as communication protocols. The simplicity of the client-server architecture allows clients to make requests that are routed to the appropriate server. These requests are made in the form of transactions.
