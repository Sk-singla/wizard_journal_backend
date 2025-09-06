# Wizard Journal - AI Powered Journal App

## Web APP: https://wizournal.vercel.app

Wizard Journal is a Spring Boot application that allows users to create and manage their journals with the help of AI. The application leverages the power of Google's Gemini API to generate journal entries based on user prompts.<br>
Hosted on render: https://wizard-journal-backend.onrender.com<br>
Android app repo: https://github.com/Sk-singla/WizardJournal
Web app repo: https://github.com/Sk-singla/wizournal-web-app

## Tech Stack

- **Framework**: Spring Boot 3
- **Language**: Java 21
- **Authentication**: Spring Security with JWT
- **Database**: PostgreSQL
- **API**: RESTful API
- **AI**: Google Gemini API
- **Rate Limiting**: Bucket4j (Token Bucket Algorithm)
- **Build Tool**: Gradle

## Architecture

The application follows the Model-View-Controller (MVC) architectural pattern:

- **Model**: Represents the data and business logic of the application. Consists of JPA entities, repositories, and services.
- **View**: The user interface of the application. In this case, it's a RESTful API that returns JSON data.
- **Controller**: Handles incoming HTTP requests, interacts with the service layer, and returns the appropriate response.

## Features

- **User Authentication**: Secure user registration and login using JWT (JSON Web Tokens).
- **Rate Limiting**: To prevent abuse, the API is protected with a token bucket rate-limiting algorithm.
- **AI Journal Generation**: Users can generate journal entries by providing a prompt. The application then uses the Gemini API to create a unique and creative journal entry.
- **CRUD Operations**: Standard Create, Read, Update, and Delete operations for journals.
- **Personalized Content**: The AI-generated content is tailored to the user's input, providing a unique experience.

## API Endpoints

| Method | Endpoint                | Description                                |
| ------ | ----------------------- | ------------------------------------------ |
| POST   | `/api/auth/register`    | Register a new user.                       |
| POST   | `/api/auth/login`       | Authenticate a user and get a JWT.         |
| POST   | `/api/journals/generate`| Generate a new journal entry using AI.     |
| GET    | `/api/journals`         | Get all journals for the authenticated user. |
| GET    | `/api/journals/{id}`    | Get a specific journal by its ID.          |
| PUT    | `/api/journals/{id}`    | Update an existing journal.                |
| DELETE | `/api/journals/{id}`    | Delete a journal.                          |

