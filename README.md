# 💳 Banking Simulation System (Spring Boot)

A full-featured backend project simulating a basic banking system using Java Spring Boot. Includes RESTful APIs for user management, accounts, transactions, statements, interest calculation, and loan management.

---

## 🚀 Tech Stack

- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (can switch to MySQL/PostgreSQL)
- REST APIs
- Maven
- Lombok

---

## 📦 Features

- User registration and retrieval
- Create savings/checking accounts
- Deposit, withdraw, and transfer money
- View transactions and monthly statements
- Interest calculation on savings accounts
- Request and repay loans
- Error handling and validation

---

## 📘 API Reference

### 👤 User APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users` | Create a new user |
| `GET`  | `/api/users/{id}` | Get user by ID |

---

### 🏦 Account APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/accounts` | Create a new bank account |
| `GET`  | `/api/accounts/{userId}` | Get all accounts for a user |
| `GET`  | `/api/accounts/details/{accountId}` | Get account details with recent transactions |
| `DELETE` | `/api/accounts/{accountId}` | Delete account if balance is 0 and no transactions |
| `POST` | `/api/accounts/calculate-interest?rate={rate}` | Apply interest to all savings accounts (default 3%) |

---

### 💰 Transaction APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/transactions/deposit` | Deposit money to an account |
| `POST` | `/api/transactions/withdraw` | Withdraw money from an account |
| `POST` | `/api/transactions/transfer` | Transfer money between accounts |
| `GET`  | `/api/transactions/{accountId}` | Get all transactions for an account |
| `GET`  | `/api/transactions/statements/{accountId}` | Get monthly grouped statement |

---

### 🏛 Loan APIs

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/loans/request` | Request a loan for an account |
| `POST` | `/api/loans/repay` | Repay a loan |

---

## 🔐 Security

> Spring Security is disabled for development. You can later enable JWT or Basic Auth.

---

## ⚙️ Running the App

### Prerequisites:
- Java 17 or 21
- Maven

### Steps:
```bash
git clone https://github.com/YOUR_USERNAME/banking-system.git
cd banking-system
mvn spring-boot:run
