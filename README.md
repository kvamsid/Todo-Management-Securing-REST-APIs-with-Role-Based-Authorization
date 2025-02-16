# Todo Management REST API

This project is a Spring Boot-based REST API for managing todos. It provides secure CRUD operations with role-based access control and JWT authentication.

## Features

- **CRUD Operations:** Create, Read, Update, and Delete todos.
- **Role-Based Authorization:** Access control for `ADMIN` and `USER` roles.
- **JWT Authentication:** Secure API endpoints using JSON Web Tokens.
- **User Onboarding:** Login and registration APIs for user authentication.

## Technologies Used

- **Spring Boot** - Backend framework
- **Spring Security** - Authentication and authorization
- **JWT (JSON Web Token)** - Token-based security
- **Hibernate** - ORM for database interaction
- **MySQL/PostgreSQL** - Database

## Installation & Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/todo-management-api.git
   cd todo-management-api
   ```

2. Configure the database in `application.properties`.

3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Authenticate and receive a JWT token

### Todo Management (Requires Authentication)

- `GET /api/todos` - Get all todos(Admin/User access)
- `GET /api/todos/{id}` - Get todo by Id(Admin/User access)
- `POST /api/todos` - Create a new todo (Admin/User access)
- `PUT /api/todos/{id}` - Update a todo (Admin only)
- `DELETE /api/todos/{id}` - Delete a todo (Admin only)
- `PATCH /api/todos/{id}` - Complete a todo (Admin/User access)
- `PATCH /api/todos/{id}` - Incomplete a todo (Admin/User access)

## Security

- All APIs require authentication via JWT.
- Users must include the token in the `Authorization` header as:
  ```sh
  Authorization: Bearer <token>
  ```
- Role-based access ensures only admins can perform certain actions.

## License
This project is licensed under the MIT License.
