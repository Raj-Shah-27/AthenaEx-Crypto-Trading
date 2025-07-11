# 🏛️ AthenaEx – Crypto Trading Platform Backend

**AthenaEx** is a secure and scalable backend application for a cryptocurrency trading platform. It provides essential features such as user authentication (JWT + 2FA), wallet operations, transaction tracking, and real-time crypto data integration. Built with Spring Boot and PostgreSQL, AthenaEx is ready to power production-grade crypto applications.

---

## 🚀 Features

- ✅ User authentication with **JWT** and **Two-Factor Authentication (2FA)**
- ✅ **Buy/Sell** cryptocurrency (API-based logic)
- ✅ **Wallet operations**: add balance, wallet-to-wallet transfers, and bank withdrawals
- ✅ **Transaction history** and audit logs
- ✅ **Coin search** and real-time pricing using **CoinGecko API**
- ✅ **Payment gateway integration** using Razorpay for wallet top-up
- ✅ CORS-enabled for frontend integration
- ✅ API tested using **Postman**

---

## 🛠️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Security** (JWT, 2FA)
- **PostgreSQL**
- **Razorpay API** (payment integration)
- **CoinGecko API** (crypto market data)
- **Postman** (for testing REST APIs)

---

## 📁 Project Structure

```

athenaex-backend/
├── controller/           # REST controllers (API endpoints)
├── service/              # Business logic
├── repository/           # JPA repositories
├── model/                # Entity and DTO classes
├── config/               # Security, CORS, and app configuration
├── resources/
│   ├── application-sample.properties  # Sample config (no secrets)
└── AthenaexApplication.java           # Main class

````

---

## 🔐 Security Features

- **JWT-based authentication** for secure, stateless API access
- **2FA (Two-Factor Authentication)** using email OTP
- **BCrypt** password hashing
- Secure **CORS configuration** for browser-based clients

---

## 🔗 External Integrations

- **Razorpay** – for secure wallet top-ups
- **CoinGecko API** – for real-time crypto prices and metadata

---

## 📦 Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/athenaex-backend.git
cd athenaex-backend
````

### 2. Configure environment

Copy the sample config and fill in your secrets:

```bash
cp src/main/resources/application-sample.properties src/main/resources/application.properties
```

Edit the file and provide:

* PostgreSQL DB credentials
* JWT secret
* Razorpay keys
* CoinGecko API base URL

### 3. Run the application

```bash
mvn spring-boot:run
```


## 📮 API Testing

All endpoints are tested using **Postman**.

---

## 📌 To-Do / Future Enhancements

* [ ] Add staking and trading analytics
* [ ] Implement notification service
* [ ] Unit + integration test coverage
* [ ] Deploy to cloud (Render/Heroku/AWS)

---

## 📄 License

This project is for educational and demo purposes only. All rights to third-party APIs and services belong to their respective owners.

---
