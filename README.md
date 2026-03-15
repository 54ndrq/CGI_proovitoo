# Restaurant Reservation System

## Prerequisites

- Java 25 (Required for backend)
- Node.js 20+ (Required for frontend)
- Maven (Required for backend build/run, but is automatically handled by the included wrapper)

## How to run Backend

The backend is a Spring Boot application.

### Standard method (Bash / Zsh / Git Bash)

1. Open a terminal in the `backend/` directory.
2. Run the wrapper script:
   ```bash
   ./mvnw spring-boot:run
   ```

### Windows (Command Prompt / PowerShell)

1. Open a terminal in the `backend/` directory.
2. Run the wrapper script:
   ```cmd
   mvnw.cmd spring-boot:run
   ```
   *Or in PowerShell:* `.\mvnw.cmd spring-boot:run`

### Manual method (if Maven is installed globally)

1. `cd backend`
2. `mvn spring-boot:run`
   *Ensure your JAVA_HOME points to JDK 25!*

*The H2 database will be populated automatically. You can view it at http://localhost:8080/h2-console*

## How to run Frontend

1. cd `frontend`
2. `npm install`
3. `npm run dev`
   *Access the UI at http://localhost:5173*

### Process

I built this project over roughly 4 days, including learning about Spring and React, since I don't have a lot
of experience with either.
I wanted to use Gradle at first, but struggled with compatibility to Java 25, so I decided to switch to
Maven instead to save time.
In the future, I would also spend more time on writing unit tests using JUnit, which I didn't focus on right now.

### Other

Usage of outside sources documented in OUTSIDE_USAGE.md.
