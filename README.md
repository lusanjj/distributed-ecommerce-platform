# Distributed E-Commerce Platform

This project is a distributed e-commerce platform built with a microservices architecture. 
It provides features like user management, product browsing, order management, payment processing, and more.

## It provides features like user management, product browsing, order management, payment processing, and more.
- `backend/`: Contains all backend microservices.
- `frontend/`: React-based frontend application.
- `docker/`: Docker configuration for containerization.
- `k8s/`: Kubernetes configuration for deployment and orchestration.

Refer to the detailed documentation for more information.



---

## **Distributed E-Commerce Platform Project Documentation** ## **Project Overview

## **Project overview** ### **1.
### **1. Project Objectives** #### **Distributed E-Commerce Platform Project Documentation
The distributed e-commerce platform aims to provide a comprehensive e-commerce experience that supports high concurrency, high availability and high scalability. The platform utilizes a microservice architecture and supports the following core functions:
- **User management**: provides registration, login, authentication and authorization mechanisms.
- **Commodity Browsing and Search**: supports commodity display, categorized browsing and search function.
- **Order Management**: supports placing orders, checking order status, and administrator can manage orders.
- **Payment System**: Support payment process and integrate third-party payment interface (e.g. Alipay, WeChat Pay).
- **Notification system**: real-time push order status updates and promotional information.
- **Recommendation system**: provides personalized recommendations based on user behavior.
- **Backend management system**: merchants and administrators can manage products, orders and users.

---

## **Technology Stack***
### **1. Front-end**: **React.
- **React.js**: for building responsive and dynamic pages.
- **Redux**: state management.
- **Axios**: handling API requests.
- **Material-UI**: for designing modern interfaces.

### **2. Back-end**: **Spring Boot**.
- **Spring Boot**: develop microservices to handle user requests and business logic.
- **Spring Cloud**: for service discovery, configuration management and load balancing.
- **JWT**: to realize user authentication and session management.
- **Kafka**: for asynchronous communication between microservices.
- **Nginx**: as a reverse proxy and load balancer.

### **3. Databases**: The following are examples of databases.
- **MySQL**: stores relational data (users, products, orders, etc.).
- **Redis**: serves as a cache to accelerate frequently queried data.
- **MongoDB** (optional): store non-relational data (e.g. product reviews).

### **4. Containerization and Deployment**
- **Docker**: Containerize services to ensure environment consistency.
- **Kubernetes**: manages automated deployment and scaling of containers.
- **AWS/GCP**: hosting platform to ensure high system availability.

### **5. CI/CD**.
- **Jenkins/GitHub Actions**: Enables automated testing, building, and deployment.
- **Git**: version management.

---

## **System Architecture** **Architecture
### **1. Microservices design**
The platform consists of the following microservices:
- **User Service**: handles user registration, login and role management.
- **Goods service**: manages goods information and categorization.
- **Order service**: handles order creation and status update.
- **Payment Service**: integrates with third-party payment interfaces.
- **Notification Service**: send order status updates and promotional information.
- **Recommendation Service**: generate recommendation based on user behavior.

### **2. API Gateway**: Generate recommendations based on user behavior.
- Use **Nginx** or **Spring Cloud Gateway** to manage the communication between front-end and back-end microservices.

### **3. Message queues**
- Use **Kafka** to handle asynchronous tasks between microservices, such as triggering notifications and inventory updates when orders are completed.

### **4. Data storage**
- **MySQL**: store core business data.
- **Redis**: caches high-frequency accessed data and reduces database load.

---

## **Development Process** **Development Process
### **1. Stage 1: Requirements Analysis** **Requirements Analysis
- **Functional requirements**:
    - User management: registration, login, role control.
    - Product browsing: display, categorization, search.
    - Order management: order placement, payment, status update.
    - Notification system: order status update push.
    - Recommendation system: recommend products based on user behavior.
- **Non-functional requirements**:
    - High Performance: Support high concurrency.
    - High Security: JWT and encrypted communication.
    - High Availability: Load balancing and containerization.

### Phase 2: Architecture Design**.
- Draw **System Architecture Diagram** and **Database Design Diagram**.
- Determine microservice communication protocols (REST API and Kafka).

### **3. Phase 3: Back-end Development** **3.
#### **3.1 User Services**
- **Technology**: Spring Boot + Spring Security + MySQL + JWT.
- **Functionality**:
    - User Registration.
    - User Login (JWT).
    - Role-Based Authority Control (RBAC).

#### **3.2 Merchandise Services**.
- **Technology**: Spring Boot + MySQL.
- **Functions**:
    - Commodity display, add, modify, delete.
    - Product classification.
    - Search optimization (optional Elasticsearch).

#### **3.3 Order Service** **Technology**: Spring Boot + MySQL.
- **Technology**: Spring Boot + Kafka + MySQL.
- **Functionality**:
    - Order creation.
    - Order status update.

#### **3.4 Payment Services** **Technology**: Spring Boot + Kafka + MySQL.
- **Technology**: Spring Boot + 3rd party payment interface.
- **Functionality**:
    - Payment integration (Alipay, WeChat Pay).
    - Payment status synchronization.

#### **3.5 Notification Service** **Technology**: Spring Boot + 3rd party payment interface.
- **Technology**: Spring Boot + Kafka.
- **Functionality**:
    - Push order status update.
    - Advertisement and promotion information push.

---.

### **4. Stage 4: Front-end development** **Framework**: React.js.
- **Framework**: React.js.
- **Functionality**:
    - Develop user interface: product display, order management.
    - Configure Redux to manage global state.
    - Use Axios to communicate with the back-end API.

----

### **5. Phase 5: Testing and Optimization** **5.1 Test**
#### **5.1 Testing**
- **Tools**:
    - **Postman**: test the API.
    - **JUnit**: unit testing.
    - **Selenium**: front-end automated tests.
- **Types of tests**:
    - Unit testing.
    - Integration testing.
    - Performance tests.

#### **5.2 Optimization**.
- Database indexing optimization.
- Redis caching of high-frequency queries.
- Nginx and Kubernetes for load balancing.

--- --- --- --- --- --- --- --- --- --- --- ---

## **Project directory structure
```
distributed-ecommerce-platform/
│
├── backend/ # Backend microservices section
│ ├── user-service/ # user management microservice
│ ├── product-service/ # Product management microservice
│ ├── order-service/ # order management microservice
│ ├── payment-service/ # payment system microservice
│ └── recommendation-service/ # Recommendation system microservice
│ └─ recommendation-service/ # recommendation-system microservice
├── frontend/ # Front-end part (React)
│ ├── src/ # Front-end part (React)
│ ├── public/
│ └── package.json
│
├── docker/ # Docker configuration
│ └── docker-compose.yml
│
├── k8s/ # Kubernetes Configuration File
│ └── deployment.yaml
│ └── README.yml │
└── README.md # Project description file
```
*** Translated with www.DeepL.com/Translator (free version) ***

