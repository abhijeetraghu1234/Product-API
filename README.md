# Product API

A RESTful API solution for managing **Products** and their associated **Items**, built using **Java 17** and **Spring Boot**. The API supports full CRUD operations, JWT-based authentication, role-based authorization, and standard best practices for RESTful design.

---

## **Table of Contents**
- [Project Overview](#project-overview)
- [Technical Stack](#technical-stack)
- [API Design](#api-design)
- [Database Schema](#database-schema)
- [Security](#security)
- [Testing](#testing)
- [Docker Setup](#docker-setup)
- [Swagger Documentation](#swagger-documentation)
- [How to Run](#how-to-run)

---

## **Project Overview**
This project implements a **Product Management API** with the following capabilities:

- CRUD operations for **Products**.
- CRUD operations for **Items** associated with a Product.
- JWT authentication with refresh token rotation.
- Role-based access control (e.g., ADMIN, USER).
- Input validation and standardized error responses.
- Pagination for collection endpoints.
- Secure endpoints with HTTPS and CORS configured.

---

## **Technical Stack**
- **Java:** 17+
- **Spring Boot:** 3.x+
- **Spring Data JPA (Hibernate)** for ORM
- **Database:** PostgreSQL / MySQL
- **Spring Security:** JWT & Refresh Token
- **Testing:** JUnit 5, Mockito, Spring Boot Test, H2 in-memory DB for tests
- **Documentation:** Swagger / OpenAPI
- **Containerization:** Docker & Docker Compose

---

## **API Design**

**Base URL:** `/api/v1`

| Method | Endpoint                    | Description                        |
|--------|-----------------------------|------------------------------------|
| GET    | /products                   | Get all products (supports pagination) |
| GET    | /products/{id}              | Get a product by ID                 |
| POST   | /products                   | Create a new product                |
| PUT    | /products/{id}              | Update a product                    |
| DELETE | /products/{id}              | Delete a product                    |
| GET    | /products/{id}/items        | Get items for a specific product    |

**Request/Response:** JSON format  
**Error Handling:** Standardized error response with HTTP status codes  

---

## **Database Schema**

```sql
CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    created_by VARCHAR(100) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    modified_by VARCHAR(100),
    modified_on TIMESTAMP
);

CREATE TABLE item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id)
);
