# 🛒 QuickCart – E-Commerce Backend

A secure and scalable RESTful backend for a full-stack E-Commerce application, developed using **Java, Spring Boot, Spring Security, JWT, and MySQL**.

The backend provides APIs for customer authentication, seller and business management, product management, shopping cart operations, and order processing.

It implements role-based authorization for **Admin, Seller, and Customer** users to ensure that each user can access only the resources and operations permitted by their role.

## 🔗 Related Projects & Live Demo

This backend is part of a full-stack E-Commerce application built using React.js and Spring Boot.

- **Frontend Repository:** <a href="https://github.com/priyanshoe/e-commerce" target="_blank">E-Commerce Frontend</a>
- **Live Application:**  <a href="https://github.com/priyanshoe/e-commerce" target="_blank">QuickCart – Live Demo </a>

### Project Architecture

The React frontend communicates with the Spring Boot backend through RESTful APIs, while MySQL is used for persistent data storage.

---

## ✨ Features

### 🔐 Authentication & Security
- User registration and login.
- JWT-based authentication.
- Secure password handling.
- Spring Security integration.
- Role-based authorization.
- Protected REST API endpoints.
- Authentication using JWT bearer tokens.

### 👨‍💼 Admin Management
- Manage application users.
- Manage sellers and businesses.
- Manage products.
- Access administrative operations.
- Manage application resources.

### 🏪 Business & Seller Management
- Create and manage business profiles.
- Associate businesses with authenticated sellers.
- Manage business information.
- Manage business products.
- Access seller-specific resources.

### 📦 Product Management
- Add new products.
- Update product information.
- Delete products.
- Retrieve product details.
- Browse available products.
- Manage products associated with businesses.

### 🛒 Shopping Cart
- Add products to cart.
- Retrieve cart information.
- Update cart items.
- Remove products from cart.
- Manage customer-specific cart data.

### 📋 Order Management
- Place customer orders.
- Retrieve order information.
- View customer orders.
- View seller-related orders.
- Manage order information and status.

---

## 🛠️ Technologies Used

| Category | Technologies |
|---|---|
| Programming Language | Java |
| Backend Framework | Spring Boot |
| Security | Spring Security |
| Authentication | JWT |
| ORM | Hibernate |
| Persistence | Spring Data JPA |
| Database | MySQL |
| API Architecture | RESTful APIs |
| Build Tool | Maven |
| Database Connectivity | JDBC |
| Version Control | Git, GitHub |
| Deployment | Railway / Render |

---

## 🏗️ Backend Architecture

The application follows a layered architecture to maintain separation of concerns and improve maintainability.

```text
              Client (React)
                    |
                    |
              REST Controllers
                    |
                    |
              Service Layer
                    |
                    |
             Repository Layer
                    |
                    |
             Spring Data JPA
                    |
                    |
                 MySQL
```

### Layer Responsibilities

- **Controller Layer:** Handles HTTP requests, API endpoints, and responses.
- **Service Layer:** Contains business logic and application operations.
- **Repository Layer:** Handles database operations using Spring Data JPA.
- **Entity Layer:** Defines database entities and relationships.
- **DTO Layer:** Transfers structured data between frontend and backend.
- **Security Layer:** Handles JWT authentication and role-based API authorization.

---

## 🔒 Authentication & Authorization

The backend uses Spring Security and JWT to secure API endpoints.

### Authentication Workflow

1. User submits login credentials.
2. Backend validates the credentials.
3. JWT token is generated after successful authentication.
4. Frontend stores the token.
5. Token is sent with subsequent API requests.
6. JWT authentication filter validates the token.
7. Spring Security checks the user's role and permissions.
8. Authorized requests are processed.

### User Roles

| Role | Access |
|---|---|
| ADMIN | Administrative operations and application management |
| SELLER | Business, product, and seller-related operations |
| CUSTOMER | Product browsing, cart, and order operations |

### Authorization Example

```java
.requestMatchers("/admin/**").hasRole("ADMIN")

.requestMatchers("/business/**", "/order/seller")
.hasRole("SELLER")

.requestMatchers("/cart/**", "/order/**")
.hasRole("CUSTOMER")

.requestMatchers(HttpMethod.GET, "/product/**")
.permitAll()
```

The application uses role-based access restrictions to protect sensitive backend resources.

---

## 🗄️ Database

**Database:** MySQL

The application uses Hibernate and Spring Data JPA for database operations and entity relationship management.

The database manages information related to:

- Users and authentication.
- User roles.
- Businesses and sellers.
- Products.
- Shopping carts.
- Orders and order details.

---

## 🚀 Getting Started

### Prerequisites

Make sure you have installed:

- Java JDK 21 (or the version required by the project).
- Maven.
- MySQL Server.
- Git.
- IDE such as IntelliJ IDEA, Eclipse, or VS Code.

### 1. Clone the Repository

```bash
git clone https://github.com/priyanshoe/e-commerce-backend.git
```

```bash
cd e-commerce-backend
```

### 2. Configure MySQL

Create a database:

```sql
CREATE DATABASE ecommerce;
```

### 3. Configure Application Properties

Open:

`src/main/resources/application.properties`

Configure your database connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

server.servlet.context-path=/api
```

Configure your JWT secret and other required environment variables according to your application configuration.

**Important:** Never commit actual database credentials or JWT secrets to GitHub.

### 4. Build the Application

```bash
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

Alternatively, run the main Spring Boot application class from your IDE.

The backend will be available at:

```text
http://localhost:8080/api
```

---

## 🌐 API Integration

The backend exposes RESTful APIs that can be consumed by the React frontend using Axios or any HTTP client.

Example authenticated API request:

```http
GET /api/cart
Authorization: Bearer YOUR_JWT_TOKEN
```

Example public API request:

```http
GET /api/product
```

The frontend and backend are maintained in separate repositories.

---

## ☁️ Deployment

The backend is designed to be deployed independently from the frontend.

Deployment configuration includes:

- Spring Boot application packaging.
- MySQL database connectivity.
- Environment-based configuration.
- Production JWT secret configuration.
- REST API availability for frontend integration.

---

## 📌 Project Highlights

- Developed RESTful APIs using Java and Spring Boot.
- Implemented JWT-based authentication.
- Configured Spring Security for role-based authorization.
- Integrated MySQL using Spring Data JPA and Hibernate.
- Designed entity relationships and database operations.
- Implemented layered architecture.
- Used DTOs for structured API communication.
- Separated business logic from controller responsibilities.
- Integrated backend APIs with a React frontend.
- Managed source code using Git and GitHub.

---

## 🎯 Project Purpose

This project was developed to gain practical experience in backend development using Java and Spring Boot.

The primary focus was on building secure REST APIs, implementing authentication and authorization, managing relational database operations, designing reusable backend components, and understanding how a production-oriented backend communicates with a frontend application.

It demonstrates practical knowledge of **Core Java, Spring Boot, Spring Security, JWT, Hibernate, Spring Data JPA, MySQL, and REST API development**.

---

## 👨‍💻 Developer

**Priyanshu Rahate**

- GitHub: https://github.com/priyanshoe
- LinkedIn: https://www.linkedin.com/in/im-priyanshu-rahate/

