# Online Shopping Management Backend

**Student:** IRADUKUNDA Josee
**Student ID:** 26083
**Course:** INSY 8322 – Web Technology and Internet
**Lecturer:** Patrick DUSHIMIMANA
**Institution:** AUCA – Faculty of Information Technology

---

## What this project is about

This is the backend for an Online Shopping Management system built with Spring Boot, Spring Data JPA and PostgreSQL. It handles everything on the order and delivery side of an online shop — customers place orders, couriers deliver them on motorbikes across different delivery zones, and customers can leave reviews once their order arrives.

---

## Tech stack

- Java 21
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL
- Maven

---

## How to run it locally

### 1. Make sure you have these installed
- Java 21
- Maven (or use the included `mvnw` wrapper)
- PostgreSQL running on your machine

### 2. Create the database

Open your terminal and run:

```sql
CREATE DATABASE online_shopping_db;
```

Or connect to PostgreSQL and create it from there.

### 3. Update the database config

Open `src/main/resources/application.properties` and make sure these match your local setup:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/online_shopping_db
spring.datasource.username=postgres
spring.datasource.password=your_password
```

### 4. Run the application

```bash
./mvnw spring-boot:run
```

Or on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The app starts on `http://localhost:8080`. Hibernate will auto-create all the tables on first run.

---

## Project structure

```
src/main/java/com/example/Online_shopping_Management/
├── entity/          # All JPA entity classes and the OrderStatus enum
├── repository/      # Spring Data JPA repositories with derived and JPQL queries
├── service/         # Business logic layer
└── controller/      # REST API endpoints
```

---

## All API endpoints

### Delivery Zones — `/api/delivery-zones`

| Method | Path | What it does |
|--------|------|--------------|
| POST | `/save` | Save a new delivery zone |
| GET | `/all` | Get all delivery zones |
| GET | `/{id}` | Get one delivery zone by id |
| PUT | `/update/{id}` | Update a delivery zone |
| DELETE | `/delete/{id}` | Delete a delivery zone |
| GET | `/unused` | Get delivery zones that no courier holds |

### Motorbikes — `/api/motorbikes`

| Method | Path | What it does |
|--------|------|--------------|
| POST | `/save` | Save a new motorbike |
| GET | `/all` | Get all motorbikes |
| GET | `/{id}` | Get one motorbike by id |
| PUT | `/update/{id}` | Update a motorbike |
| DELETE | `/delete/{id}` | Delete a motorbike |
| GET | `/free` | Get motorbikes not assigned to any courier |

### Couriers — `/api/couriers`

| Method | Path | What it does |
|--------|------|--------------|
| POST | `/save` | Save a new courier |
| GET | `/all` | Get all couriers |
| GET | `/{id}` | Get one courier by id |
| PUT | `/update/{id}` | Update a courier |
| DELETE | `/delete/{id}` | Delete a courier |
| GET | `/by-delivery-zone?name=kicu` | Get couriers by delivery zone name (case-insensitive) |
| PUT | `/{courierId}/delivery-zones/{deliveryZoneId}` | Add a delivery zone to a courier |
| PATCH | `/{courierId}/motorbike?bikeNumber=4` | Move a courier to a different motorbike |

### Customers — `/api/customers`

| Method | Path | What it does |
|--------|------|--------------|
| POST | `/save` | Save a new customer |
| GET | `/all` | Get all customers |
| GET | `/{id}` | Get one customer by id |
| PUT | `/update/{id}` | Update a customer |
| DELETE | `/delete/{id}` | Delete a customer |
| GET | `/by-district?district=gasabo` | Get customers by district (case-insensitive, sorted by last name) |
| GET | `/of-courier/{courierId}` | Get all distinct customers served by a specific courier |

### Customer Orders — `/api/customer-orders`

| Method | Path | What it does |
|--------|------|--------------|
| POST | `/save` | Save a new order (always starts as PLACED, blocks past dates and double bookings) |
| GET | `/all` | Get all orders |
| GET | `/{id}` | Get one order by id |
| PUT | `/update/{id}` | Update an order (does not change status) |
| DELETE | `/delete/{id}` | Delete an order (blocked if it has reviews) |
| PATCH | `/{id}/status?status=SHIPPED` | Move an order to the next status (enforces status flow rules) |
| GET | `/by-status?status=PLACED` | Get all orders with a given status, sorted by date |
| GET | `/between?start=2026-10-01&end=2026-10-31` | Get orders in a date range |
| GET | `/stats/by-status` | Count orders grouped by status |
| PATCH | `/cancel-day?courierId=...&date=2026-10-20` | Cancel all non-delivered orders for a courier on a given date |

### Reviews — `/api/reviews`

| Method | Path | What it does |
|--------|------|--------------|
| POST | `/save?customerOrderId=...` | Write a review (only allowed for DELIVERED orders) |
| GET | `/customer/{customerCode}` | Get all reviews by a customer, newest first |
| DELETE | `/delete/{id}` | Delete a review |

---

## Business rules the API enforces

- A new customer order always starts as **PLACED** regardless of what status you send in the body
- You cannot set an order date in the past
- A courier cannot have two active orders on the same date (WITHDRAWN orders don't count)
- Order status can only move forward: PLACED → SHIPPED → DELIVERED or WITHDRAWN. You cannot go backwards and DELIVERED/WITHDRAWN are final
- Reviews can only be written for orders that are **DELIVERED**
- You cannot delete a customer order that still has reviews — delete the reviews first

---

## Postman collection

The file `online-shopping-management-midterm.postman_collection.json` in the root of this repository contains one request for each requirement (A2, B1, B2, B3, C1, C2, C3, C4, D1, D2, D3, D4, E1, E2, E3, F1, F2).

Import it into Postman to test all endpoints quickly.

---

## Notes

- `spring.jpa.hibernate.ddl-auto=update` means Hibernate creates and updates tables automatically on startup — no SQL scripts needed
- `spring.jpa.show-sql=true` prints every SQL query in the console so you can see what is happening
- No DTOs are used — entities are passed directly as request and response bodies as required by the exam specification
