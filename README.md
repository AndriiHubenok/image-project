# Microservice Backend of Monitoring Barrier-Free Environments

This project implements a microservice-based backend designed for image storage, action auditing, and automatic moderation of text descriptions using artificial intelligence. The system specializes in processing data for monitoring infrastructure objects and barrier-free environments. The architecture is built using synchronous Spring MVC controllers for the REST API and asynchronous event exchange via a message broker.

## 🏗 Architecture and Microservices

The project is divided into three main microservices:

1. **Image Storage Service** (`image-storage-service`)
   * Responsible for uploading, retrieving, and deleting images.
   * Integrated with the **MinIO** object storage.
   * Asynchronously publishes `imageUpload` and `imageDelete` events to RabbitMQ.

2. **Text Moderation Service** (`text-moderation-service`)
   * Uses **Spring AI** and integrates with the `gpt-4o-mini` model from OpenAI.
   * Analyzes text descriptions, rejecting spam, insults, or texts unrelated to infrastructure (returns a `SAFE` or `UNSAFE` status with an explanation).

3. **Image Audit Service** (`image-audit-service`)
   * Keeps an audit of image uploads and deletions, storing records with the corresponding status (e.g., `UPLOADED`, `DELETED`) in the database.
   * Processes text moderation results, updating the request status to `PUBLISHED` or `REJECTED` (saving the reason for rejection) in PostgreSQL.

## 🛠 Technology Stack

* **Language & Framework:** Java 21, Spring Boot 3/4
* **Artificial Intelligence:** Spring AI (OpenAI API)
* **Database:** PostgreSQL 16
* **Message Broker:** RabbitMQ 3
* **Object Storage:** MinIO
* **Containerization:** Docker and Docker Compose
* **API Documentation:** Springdoc OpenAPI (Swagger UI)

## 🚀 Running the Project

The project is deployed in a single `microservice-network` using Docker Compose.

### 1. Environment Configuration
Before starting, you need to create `.env` files in each of the service directories. The `.env` files are loaded by the Docker Compose configuration:

* **`image-storage-service/.env`**: Specify access configuration for RabbitMQ and MinIO (user, passwords, bucket name, and access keys).
* **`image-audit-service/.env`**: Specify access for RabbitMQ and PostgreSQL database connection parameters (URL, user, password).
* **`text-moderation-service/.env`**: Specify access for RabbitMQ and the mandatory `OPENAI_API_KEY` for moderation to work.

### 2. Starting Containers
In the root of the project (where the `docker-compose.yml` file is located), run the following command:

```bash
docker-compose up --build -d
```

This command will spin up the following containers and expose ports on the host:
* `image-service` — port **8089**
* `moderation-service` — port **8093**
* `image-audit-service` — port **8091**
* `image-storage` (MinIO) — API on **9002**, Console on **9003** (Login: `minioadmin`, Password: `minioadmin123`)
* `message-broker` (RabbitMQ) — port **5672** and Management Console on **15672**
* `audit-db` (PostgreSQL) — port **5433** (database: `audit_db`, user: `postgres`)

## 📡 Main API Endpoints

### Image Storage Service (8089)
* `POST /images` — upload an image via form (multipart/form-data).
* `GET /images/{filename}` — retrieve an image from MinIO storage.
* `DELETE /images/{filename}` — delete an image.

### Audit and Text Response Service (8091)
* `GET /record` and `GET /record/{filename}` — retrieve image upload and deletion logs.
* `POST /response` — submit a text description for moderation (receives an initial `PENDING` status).
* `GET /response` — view all descriptions and their current statuses after AI verification.
* `GET /response/rejected` — view records rejected by the moderator along with the block reason.
