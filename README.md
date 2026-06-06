# Hort Manager

Hort Manager is a full-stack web application for managing after-school care groups, children, attendance status and child notes.

It was created after a teacher colleague described the need for a better way to manage daycare groups, children, attendance and notes instead of relying on Excel spreadsheets. The project provides a simple full-stack web application for educators to organize this information in one central system.

![Login screen](docs/screenshots/login.png)


![Dashboard](docs/screenshots/Dashboard.png)


![Dashboard2](docs/screenshots/DashboardHort2.png)

## Features

* User registration and login
* JWT-based authentication
* Password hashing with BCrypt
* Group management
* Child management
* Attendance status tracking
* Notes for individual children
* Search function for children
* Overview card showing the total number of children
* PostgreSQL persistence
* REST API with validation
* React + TypeScript frontend
* Docker Compose setup
* Unit and controller tests
* GitHub Actions CI workflow





## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* PostgreSQL
* Maven
* JUnit 5
* Mockito
* MockMvc
* Docker
* Docker Compose
* GitHub Actions

### Frontend

* React
* TypeScript
* Vite
* CSS
* Fetch API

## Project Structure

```text
.
├── frontend
│   ├── src
│   │   ├── App.tsx
│   │   ├── App.css
│   │   ├── api.ts
│   │   └── types.ts
│   └── package.json
│
├── src/main/java/com/schoolms/school_management
│   ├── auth        # Registration, login and JWT generation
│   ├── child       # Child management, attendance and notes
│   ├── common      # Shared helpers / health endpoint
│   ├── config      # Security configuration
│   ├── hortgroup   # Group management
│   └── user        # User entity and repository
│
├── src/test/java/com/schoolms/school_management
│   ├── auth
│   ├── child
│   └── group
│
├── docker-compose.yml
├── Dockerfile
├── pom.xml
└── README.md
```

## Security

The application uses JWT-based authentication.

Public endpoints:

```text
POST /auth/register
POST /auth/login
GET  /health
```

All other protected endpoints require a valid JWT Bearer Token:

```http
Authorization: Bearer <token>
```

Passwords are not stored in plain text. They are hashed with BCrypt before being persisted.

JWT secrets and database credentials are provided through environment variables and are not committed to the repository.

## Environment Variables

Create a local `.env` file based on `.env.example`:

```bash
cp .env.example .env
```

Example:

```env
POSTGRES_USER=user
POSTGRES_PASSWORD=password
POSTGRES_DB=schooldb
JWT_SECRET=replace-with-your-own-long-secret-key
```

Generate a local JWT secret:

```bash
openssl rand -base64 32
```

The `.env` file contains local secrets and must not be committed.

## Run the Backend with Docker Compose

Start the backend and PostgreSQL database:

```bash
docker compose up -d --build
```

The backend will be available at:

```text
http://localhost:8080
```

Stop the containers:

```bash
docker compose down
```

Stop the containers and remove the database volume:

```bash
docker compose down -v
```

## Run the Frontend

Go into the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the Vite development server:

```bash
npm run dev
```

The frontend will usually be available at:

```text
http://localhost:5173
```

## Run Tests

Using the Maven wrapper:

```bash
./mvnw test
```

Or with local Maven:

```bash
mvn test
```

## Build Application

```bash
./mvnw clean package
```

Or:

```bash
mvn clean package
```

## Frontend Build

```bash
cd frontend
npm run build
```

## API Examples

### Register User

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"teacher1","password":"password123"}'
```

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"teacher1","password":"password123"}'
```

The response contains a JWT token.

### Store Token in Fish Shell

```fish
set TOKEN "paste-token-here"
```

### Store Token in Bash/Zsh

```bash
export TOKEN="paste-token-here"
```


## Testing Approach

The project contains tests for the controller and service layers.

Examples:

* Auth controller tests with MockMvc
* Auth service tests with Mockito
* Child controller and service tests
* Hort group controller and service tests

The tests focus on:

* HTTP status codes
* JSON response structure
* request validation
* service logic
* repository interaction through mocks
* authentication flow behavior

## CI/CD

The project includes a GitHub Actions workflow that runs automatically on push and pull requests.

The workflow:

* checks out the repository
* sets up Java 21
* caches Maven dependencies
* runs tests
* builds the application

This ensures that pushed changes are automatically verified.

## Possible Future Improvements

* Edit and delete groups
* Edit and delete children
* Filter children by group
* Add role-based authorization
* Improve error handling in the frontend
* Add integration tests with Testcontainers
* Add production deployment configuration
