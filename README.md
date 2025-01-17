# Demo HTTP Server

This project is a simple HTTP server implemented in Java. It serves static files from the `public` directory.

## Features

- Serves HTML, CSS and image files.
- Handles 404 errors for missing files.
- Logs incoming requests to the console.

## Project Structure

- `public/index.html`: The main HTML file served by the server.
- `public/styles.css`: The CSS file for styling the HTML content.
- `src/CentralServer.java`: The Java source file containing the server implementation.
- `public/assets `: The folder containing media files.  
## How to Run

1. Compile the Java source code:
    ```sh
    javac src/CentralServer.java
    ```

2. Run the server:
    ```sh
    java -cp src CentralServer
    ```

3. Open a web browser and navigate to `http://localhost:7070` to see the served content.

## Dependencies

- Java Development Kit (JDK) 8 or higher.
