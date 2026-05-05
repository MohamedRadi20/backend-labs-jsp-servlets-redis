<h1 align="center">Java Backend Architecture</h1>

<p align="center">
  JSP / Servlets · Redis · MySQL · Docker
</p>

<p align="center">
  <img src="https://github.com/MohamedRadi20/Hot_dog/blob/b6d27fb5135ef367db4ecc729108e14643ffdee2/773a37fa-ea5c-45c3-912c-8a117af6cd7a.png" width="750">
</p>

---

## Overview

This project was used as a teaching example for backend engineering students, focusing on how real systems combine relational databases with in-memory caching.

It demonstrates how to structure a backend system that balances persistent storage with high-performance caching, reflecting common production patterns.

---

## Architecture

The system is composed of three main components:

- **Application Layer (JSP / Servlets)**  
  Handles HTTP requests, business logic, and integration with external services.

- **MySQL (Relational Database)**  
  Used for persistent storage of structured data.

- **Redis (In-Memory Store)**  
  Used for caching and rate limiting to improve performance and control traffic.

Redis runs inside a Docker container, while MySQL runs on the local machine.

---

## Key Concepts

### Caching

Redis is used to cache frequently accessed data, reducing load on the database and improving response times.

Example: frequently requested data is first checked in Redis before querying MySQL.

### Rate Limiting

A rate limiting mechanism is implemented using Redis to restrict the number of requests within a defined time window. This helps prevent abuse and stabilizes the system under load.

### Data Management Strategy

- MySQL handles durable, structured data  
- Redis handles temporary, high-speed operations  

This separation reflects a common backend design where SQL and NoSQL systems are used together.

---

## Tech Stack

- Java (JSP / Servlets)  
- Apache Tomcat  
- MySQL  
- Redis  
- Docker  
- JDBC  

---

## Running the Project

1. Start Redis using Docker:

```bash
docker run -p 6379:6379 redis
```

2. Ensure MySQL is running on port 3306
3. Configure database and Redis connections
4. Deploy the application on Tomcat
   
## Notes

This project focuses on backend fundamentals, including request handling, data persistence, caching strategies, and basic traffic control using rate limiting.

The goal is clarity and understanding of core backend concepts rather than production-ready completeness.
