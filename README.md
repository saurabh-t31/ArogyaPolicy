
# 🏥 ArogyaPolicy – Insurance Policy Management System

A **Java Spring Boot-based web application** designed to **modernize and automate insurance policy management**.
It streamlines the process of policy enrollment, scheme management, claim processing, and feedback tracking for both **customers** and **administrators**.

---

## 📌 Features

### 👨‍💼 Admin Panel

* Create, update, and delete **schemes and policies and claims**
* Manage user claims, approvals, and rejections
* Monitor customer feedback
* View and manage registered users

### 👥 User Panel

* Secure **registration and login** (Spring Security)
* Enroll in available policies and schemes
* Submit claims online
* Reset password with **OTP-based email verification**
* Google Sign-in (OAuth) for quick login

---

## 🏗️ Project Architecture & Main Components

* **Spring Boot (Backend)** → Core application framework
* **Spring Data JPA + MySQL** → Persistent storage of policies, users, claims
* **Spring Security + OAuth2** → Authentication & authorization (email login + Google login)
* **Spring Mail API (SMTP)** → OTP-based password reset & email communication
* **Thymeleaf + Bootstrap** → Frontend templates with responsive design
* **REST APIs** → Exposed endpoints for integration and testing
* **Dockerized Deployment** → Containerized for easy setup & scaling
* **AWS EC2 (previous hosting)** → Cloud deployment for real-world testing

**Flow in Simple Terms:**

1. User registers/login (Spring Security & OAuth).
2. User browses available **schemes/policies** and can **enroll**.
3. Admin manages the lifecycle of **schemes/policies**.
4. Claims are submitted by users and processed by the admin.
5. Email notifications (OTP/updates) sent via **Spring Mail**.
6. Data is securely stored and retrieved from **MySQL DB**.

---

## ⚙️ Installation & Setup

### 🔧 Prerequisites

* Java 17+
* Maven
* MySQL 8+
* Git

### 🖥️ Run Locally

1. Clone the repository:

   ```bash
   git clone https://github.com/saurabh-t31/ArogyaPolicy.git
   cd ArogyaPolicy
   ```
2. Configure MySQL in `application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/policydb
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Run the project:

   ```bash
   mvn spring-boot:run
   ```
4. Open in browser 👉 `http://localhost:8080/`

---

## 🐳 Docker Deployment

1. Build Docker image:

   ```bash
   docker build -t arogya-policy .
   ```
2. Run container:

   ```bash
   docker run -p 8080:8080 arogya-policy
   ```
3. Application available at: 👉 `http://localhost:8080/`

📌 *Note:* A pre-configured `Dockerfile` is included in the project for deployment.

---

## ☁️ Deployment on AWS (Previously Hosted)

* Application deployed on **AWS EC2 instance**
* Connected with **MySQL DB (AWS RDS / local DB)**
* Docker container used for simplified deployment
---

## 📊 Project Impact

* Eliminated manual delays in policy/claim handling
* Improved customer transparency & trust
* Enabled digital-first insurance operations
* Reduced admin workload with automation

---

## 🔮 Future Enhancements

* 💳 **Payment Gateway Integration** (Razorpay/Stripe/PayPal) for online premium payments
* 🧾 **Payment Receipt Storage & History** (downloadable PDFs for users, admin tracking)
* 📑 **Detailed Policy/Scheme Records** (cover details, claim conditions, history of claims)
* 🔔 Push/email notifications for policy updates and claims

---

## 🙌 Acknowledgment

This project was developed as part of the **Infosys Springboard Java Development Internship**, where real-world problem-solving and end-to-end project building were practiced — covering **backend, frontend, authentication, database, Docker, and cloud deployment**.

---

✨ *ArogyaPolicy is a digital step toward smarter, faster, and more transparent insurance policy management.*

