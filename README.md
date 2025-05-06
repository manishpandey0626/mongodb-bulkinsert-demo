# MongoDB Bulk Insert Benchmark Demo

This project demonstrates and benchmarks three different approaches for inserting a large number of documents into a
MongoDB collection using Spring Boot and `MongoTemplate`.

## 🔍 Overview

When working with large-scale data ingestion into MongoDB, performance can vary significantly depending on the insertion
strategy used. This demo compares the following insertion methods:

1. `save()` in a loop
2. `mongoTemplate.insertAll()`
3. `mongoTemplate.bulkOps(BulkMode.UNORDERED).insert().execute()`

Each method is tested with **10,000 `Person` records**, and execution time is measured to identify the most efficient
approach.

---

## 📊 Benchmark Results

| Insert Method      | Ordered | Time Taken |
|--------------------|---------|------------|
| `save()` in a loop | Yes     | ~16,728 ms |
| `insertAll()`      | Yes     | ~346 ms    |
| `bulkOps.insert()` | No      | ~220 ms    |

> 📌 *Note:* Times may vary depending on system configuration and MongoDB server performance.

---

## 🐳 Running MongoDB with Docker

If you don't have MongoDB installed, you can quickly set it up using Docker:

```bash
docker run --name mongodb -d -p 27017:27017 mongo:latest
```

This command will:

- Pull the latest MongoDB image (if not already present)
- Run MongoDB in a detached container
- Expose it on the default port `27017`

---

## 📦 Technologies Used

- Java 21
- Spring Boot 3.x
- Spring Data MongoDB
- MongoDB
- OpenCSV
- Maven

---

## ▶️ Running the Application

To run the application, use the following command:

```bash
mvn spring-boot:run
```
