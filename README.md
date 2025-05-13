
# Book Store API

---
### RESTful API for managing a physical bookstore, including book filtering, genre classification, and cart functionality.

---

## 🚀 Core features
- User authentication with JWT (Login/Register)
- Role-based authorization with predefined roles: `ADMIN`, `USER` & `DEVELOPER`
- Full CRUD operations for Books, Genres & Users
- Dynamic book search and filtering
- Shopping cart logic
## 🛠️ Technologies used
- Spring Boot & Spring Security
- Swagger (OpenAPI) API documentation
- Custom global exception handling
- My SQL
- Docker for containerization
### Swagger doc URL
```bash
http://localhost:8080/swagger-ui/index.html#
```
### API Testing (cURL examples)
## 🧪 API Test with cURL

### 🔐 Auth Endpoints

#### 📥 Register User
```bash
curl -X POST "http://localhost:8080/auth/register" `
-H "Content-Type: application/json" `
-d "{ "username":"adminuser",
      "email":"admin@example.com",
      "password":"adminpass",
      "role":["ADMIN","DEVELOPER"]
    }"
```
#### 📥 Login User
```bash
curl -X POST "http://localhost:8080/auth/login" `
-H "Content-Type: application/json" `
-d "{\"username\":\"newuser\",\"password\":\"password123\"}"
```
## 📚 BookController – cURL Examples

### 🔍 Get all books (paginated, sorted)
```bash
curl -X GET "http://localhost:8080/public/book?pageNumber=0&pageSize=5&sortBy=title&sortOrder=asc" `
-H "Authorization: Bearer YOUR_JWT_TOKEN"
```
### ➕ Add new book (requires token)
```bash
curl -X POST "http://localhost:8080/public/book?genreId=1" \
-H "Content-Type: application/json" \
-H "Authorization: Bearer YOUR_JWT_TOKEN" \
-d '{
"title": "The Hobbit",
"description": "Fantasy novel",
"author": "J.R.R. Tolkien",
"price": 29.99
}'
```