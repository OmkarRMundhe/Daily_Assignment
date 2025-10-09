#  Retail Project (Microservices + Kafka)

This project is a **Retail Management System** built using **Spring Boot microservices architecture**.  
It consists of two main services — `InventoryService` and `OrderService` — that communicate asynchronously through **Apache Kafka**.

---

## 📁 Project Structure

```text
RetailProject/
│
├── InventoryService331/          # Manages product inventory
│   ├── src/main/java/com/order/
│   │   ├── InventoryService331Application.java  # Main Spring Boot app
│   │   └── controller/
│   │       └── InventoryController.java        # Handles REST APIs
│   ├── src/main/resources/application.properties  # Config file
│   └── pom.xml                                 # Maven dependencies
│
├── OrderService33/              # Handles customer orders
│   ├── src/main/java/com/order/
│   │   ├── OrderService33Application.java  # Main Spring Boot app
│   │   └── controller/
│   │       └── OrderController.java       # Handles REST APIs
│   ├── src/main/resources/application.properties
│   └── pom.xml
│
└── pom.xml (Parent POM)          # Parent Maven configuration

```
---

## Tech Stack

| Component       | Technology         |
| --------------- | ------------------ |
| Language        | Java 17            |
| Framework       | Spring Boot        |
| Build Tool      | Maven              |
| Messaging       | Apache Kafka       |
| Architecture    | Microservices      |
| IDE             | IntelliJ / Eclipse |
| Version Control | Git & GitHub       |

---
## Microservices Overview
1️. Inventory Service

Manages product stock details.
Publishes updates to Kafka topics when stock changes occur.
Listens to Kafka events to synchronize product data.

2️.Order Service

Handles customer orders and order processing.
Sends order requests via Kafka.
Listens for inventory updates to confirm product availability.

---

## Kafka Integration Flow

Order Service sends a message to a Kafka topic (e.g., order-topic) when a new order is created.

Inventory Service consumes the message, checks stock availability, and responds through another topic (e.g., inventory-topic).

---

## Getting Started
1️⃣ Clone the Repository
git clone https://github.com/Abhishek-Kanade-23/Kafka-Project.git
cd Kafka-Project

2️⃣ Start  Kafka
# Start Kafka Server
```bash
kafka-server-start.bat config/server.properties
```

3️⃣ Create Required Kafka Topics
```bash
kafka-topics.bat --create --topic order-topic --bootstrap-server localhost:9092
kafka-topics.bat --create --topic inventory-topic --bootstrap-server localhost:9092
```

4️⃣ Run Microservices

Open two terminals:

# Terminal 1
```bash
cd InventoryService331
mvn spring-boot:run
```

# Terminal 2
```bash
cd OrderService33
mvn spring-boot:run
```

