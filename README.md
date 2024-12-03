# Java HTTP Server

A lightweight HTTP server built in Java, capable of handling GET and POST requests, serving static files, and supporting dynamic routing. This project was completed as part of the CodeCrafters "Build Your Own HTTP Server" challenge.

## Features
- **Static and Dynamic Routing**:
  - Supports both static routes and dynamic routes with path parameters (e.g., `/echo/{str}`).
- **Concurrency**:
  - Handles multiple client connections using a thread pool.
- **File Handling**:
  - Serve and write files dynamically to/from a specified directory.
- **Gzip Compression**:
  - Supports gzip compression for response bodies.
- **Request and Response Management**:
  - Easy-to-use abstractions for HTTP requests and responses.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 21 or higher
- Maven or any Java build tool of your choice

### Usage
1. Clone the repository:
   ```bash
   git clone https://github.com/A7bari/codecrafters-http-server-java.git
   cd codecrafters-http-server-java
   ```
2. Compile and run the server:
   ```bash
   javac Main.java
   java Main --directory=/path/to/serve/
   ```
3. Access the server at `http://localhost:4221`.

## Example Endpoints
- **GET /**: Returns a `200 OK` response.
- **GET /echo/{str}**: Echoes the given string.
- **GET /user-agent**: Returns the client's `User-Agent` header.
- **GET /files/{filename}**: Serves a file from the specified directory.
- **POST /files/{filename}**: Writes a file to the specified directory with the content in the request body.

## Project Structure
- **HttpServer**: Main server class for managing client connections.
- **Router**: Handles routing for different HTTP methods and paths.
- **HttpRequest** and **HttpResponse**: Abstractions for HTTP request/response.
- **FileUtil**: Utility for file I/O operations.
- **ReqHandler**: Parses incoming HTTP requests.

## Future Enhancements
- Add support for HTTPS.
- Implement additional HTTP methods (PUT, DELETE).
- Integrate structured logging.
- Introduce automated testing (unit and integration tests).

## License
This project is licensed under the MIT License.
