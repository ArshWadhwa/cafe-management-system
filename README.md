# ☕ Cafe Management System

A **Spring Boot** application for managing a café, focusing on coffee inventory management, cart operations, and bill calculation.

## 🚀 Features
- ☕ **Coffee Management** - Add, update, delete, and list coffee items
- 🛒 **Cart Management** - Add items to cart and manage quantities
- 🧾 **Order Processing** - View cart contents and calculate total bill
- 💾 **Database Management** - PostgreSQL with Flyway migrations
- 🔄 **RESTful APIs** - Complete CRUD operations

## 🛠️ Tech Stack
- **Backend**: Java 17, Spring Boot 3.3.3
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA
- **Build Tool**: Gradle
- **Migrations**: Flyway 7.3.1
- **Lombok**: For reducing boilerplate code

## 📋 Prerequisites
- Java 17 or higher
- PostgreSQL 12 or higher
- Gradle 7.0 or higher

## ⚙️ Setup Instructions

### 1. **Clone the Repository**
```bash
git clone https://github.com/your-username/cafe-management-system.git
cd cafe-management-system
```

### 2. **Database Setup**
```bash
# Create PostgreSQL database
createdb cafe

# Or using psql
psql -U postgres
CREATE DATABASE cafe;
\q
```

### 3. **Environment Variables (Optional)**
```bash
export DB_USER=postgres
export DB_PASSWORD=your_password
export DB_NAME=cafe
```

### 4. **Run Migrations**
```bash
./gradlew flywayMigrate
```

### 5. **Start the Application**
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## 📚 API Documentation

### Base URL
```
http://localhost:8080
```

### Coffee Management APIs

#### 1. **Add Coffee Item**
- **Endpoint**: `POST /coffee`
- **Content-Type**: `application/json`

**Request Body:**
```json
{
  "coffee": {
    "name": "Espresso",
    "description": "Strong Italian coffee",
    "price": 3.50,
    "imgURL": "https://example.com/espresso.jpg"
  }
}
```

**Response:**
```json
{
  "coffee": {
    "cartId": null,
    "id": 1,
    "name": "Espresso",
    "price": 3.50,
    "description": "Strong Italian coffee",
    "imgURL": "https://example.com/espresso.jpg"
  }
}
```

#### 2. **Get All Coffee Items**
- **Endpoint**: `GET /coffee`
- **Content-Type**: `application/json`

**Response:**
```json
{
  "coffeeList": [
    {
      "cartId": null,
      "id": 1,
      "name": "Espresso",
      "price": 3.50,
      "description": "Strong Italian coffee",
      "imgURL": "https://example.com/espresso.jpg"
    },
    {
      "cartId": null,
      "id": 2,
      "name": "Cappuccino",
      "price": 4.50,
      "description": "Italian coffee with steamed milk",
      "imgURL": "https://example.com/cappuccino.jpg"
    }
  ]
}
```

#### 3. **Update Coffee Item**
- **Endpoint**: `PUT /coffee/update?id={coffee_id}`
- **Content-Type**: `application/json`

**Request Body:**
```json
{
  "newName": "Updated Espresso",
  "newPrice": 4.00,
  "newDescription": "Updated description",
  "newImgURL": "https://example.com/updated-espresso.jpg"
}
```

**Response**: No content (204)

#### 4. **Delete Coffee Item**
- **Endpoint**: `DELETE /coffee/delete`
- **Content-Type**: `application/json`

**Request Body:**
```json
{
  "coffee_id": 1
}
```

**Response:**
```json
"Coffee item with id 1 deleted successfully"
```

### Cart Management APIs

#### 1. **Add Item to Cart**
- **Endpoint**: `POST /cart`
- **Content-Type**: `application/json`

**Request Body:**
```json
{
  "cart": {
    "coffee_id": 1,
    "quantity": 2
  }
}
```

**Response**: No content (204)

#### 2. **Get Cart Contents**
- **Endpoint**: `GET /cart`
- **Content-Type**: `application/json`

**Response:**
```json
{
  "coffeeCart": [
    {
      "coffee": {
        "cartId": 1,
        "id": 1,
        "name": "Espresso",
        "price": 3.50,
        "description": "Strong Italian coffee",
        "imgURL": "https://example.com/espresso.jpg"
      },
      "quantity": 2
    },
    {
      "coffee": {
        "cartId": 2,
        "id": 2,
        "name": "Cappuccino",
        "price": 4.50,
        "description": "Italian coffee with steamed milk",
        "imgURL": "https://example.com/cappuccino.jpg"
      },
      "quantity": 1
    }
  ],
  "bill": 11.50
}
```

#### 3. **Remove Item from Cart**
- **Endpoint**: `DELETE /cart`
- **Content-Type**: `application/json`

**Request Body:**
```json
{
  "coffee_id": 1
}
```

**Response:**
```json
"Coffee item with id 1 deleted successfully"
```

## 🗄️ Database Schema

### Coffee Table
```sql
CREATE TABLE coffee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image VARCHAR,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

### Cart Table
```sql
CREATE TABLE cart (
    id SERIAL PRIMARY KEY,
    coffee_id SERIAL,
    quantity INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## 🏗️ Project Structure
```
src/
├── main/
│   ├── java/cafe/
│   │   ├── controller/
│   │   │   ├── CafeController.java      # Coffee management endpoints
│   │   │   └── CartController.java      # Cart management endpoints
│   │   ├── entity/
│   │   │   ├── CoffeeEntity.java        # Coffee JPA entity
│   │   │   └── CartEntity.java          # Cart JPA entity
│   │   ├── cofeeData/
│   │   │   ├── db/
│   │   │   │   ├── CoffeeDbRepository.java  # Coffee repository
│   │   │   │   └── CartDbRepository.java    # Cart repository
│   │   │   ├── Coffee.java              # Coffee DTO
│   │   │   ├── Cart.java                # Cart DTO
│   │   │   ├── CoffeeCart.java          # Cart with coffee details
│   │   │   ├── AddCoffeeRequest.java    # Add coffee request
│   │   │   ├── AddCoffeeResponse.java   # Add coffee response
│   │   │   ├── GetAllCoffeeResponse.java # Get all coffee response
│   │   │   ├── UpdateCoffeeRequest.java # Update coffee request
│   │   │   ├── DeleteCoffee.java        # Delete coffee request
│   │   │   ├── AddCartRequest.java      # Add cart request
│   │   │   └── GetAllCartResponse.java  # Get cart response
│   │   └── Main.java                    # Spring Boot application
│   └── resources/
│       ├── application.yaml             # Application configuration
│       └── migrations/
│           ├── V1__cafe_table.sql       # Coffee table migration
│           └── V3__cart_table.sql       # Cart table migration
```

## 🧪 Testing

### Build the Project
```bash
./gradlew build
```

### Run Tests
```bash
./gradlew test
```

### Run Application
```bash
./gradlew bootRun
```

## 🔧 Configuration

### Application Properties
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/cafe
    username: postgres
    password: postgres

server:
  port: 8080
```

### CORS Configuration
The application is configured to accept requests from `http://127.0.0.1:5502` for frontend integration.

## 📝 Notes

- All timestamps are automatically managed by the application
- The bill calculation includes the total price of all items in the cart
- Coffee items can be updated but their creation timestamp remains unchanged
- Cart items are linked to coffee items via `coffee_id`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

---

**Happy Coding! ☕**
