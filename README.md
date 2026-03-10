# Audio Transcriber - Spring AI + React

A simple full-stack project that **transcribes audio files into text** using **Spring AI and OpenAI**.

This project was created to explore **how AI can be integrated into real applications using Java, Spring Boot, and React**.

---

## Features

- Upload audio files from the frontend
- Convert audio to text using OpenAI
- Backend built with **Spring Boot + Spring AI**
- Frontend built with **ReactJS**
- Displays transcription result instantly
- Simple and clean UI

---

## Tech Stack

### Backend
- Java
- Spring Boot
- Spring AI
- Maven

### Frontend
- ReactJS
- Vite
- Axios

### AI Service
- OpenAI Audio Transcription API

---

## Project Structure


audio-transcriber
│
├── audio-transcribe
│ └── Spring Boot Backend
│
├── audio-transcribe-frontend
│ └── React Frontend


---

# Setup Instructions

## 1. Clone the Repository

```bash
git clone https://github.com/Niranjanaonrock/audio-transcriber.git
cd audio-transcriber
2. Backend Setup (Spring Boot)
Step 1: Navigate to the backend folder
cd audio-transcribe
Step 2: Set your OpenAI API Key

Do not commit your API key to GitHub. Use an environment variable instead.

# Windows PowerShell
setx OPENAI_API_KEY "sk-XXXXX-YOUR-KEY-HERE"

Replace:

sk-XXXXX-YOUR-KEY-HERE

with your actual OpenAI API key.

Step 3: Build the project
mvn clean install
Step 4: Run the Spring Boot application
mvn spring-boot:run

The backend server will start at:

http://localhost:8080

API Endpoint:

POST /api/transcribe
3. Frontend Setup (React)
Step 1: Navigate to the frontend folder
cd audio-transcribe-frontend
Step 2: Install dependencies
npm install
Step 3: Run the React development server
npm run dev

The application will run at:

http://localhost:5173

Updating Soon..
