# Insecure API Project

## Project Overview
The **Insecure API** is a project designed to showcase common coding mistakes that can lead to logical errors and vulnerabilities in APIs. The API contains examples of both incorrect and correct implementations, offering a practical guide to recognizing and mitigating security flaws. This project also includes security implementations in the CI/CD pipeline using GitHub Actions, which automate the building and pushing of Docker images to Docker Hub.

The project is a work in progress and will evolve over time to achieve perfection in terms of demonstrating API security and best practices.

## Installation Instructions
The API is built with Spring Boot and containerized as a Docker image for easy deployment across various systems.

To get started:
1. Pull the Docker image:
   ```bash
   docker pull eclecticsinfosec/insecureapi
   ```

2. Run the container:
   ```bash
   docker run -p 9093:9093 eclecticsinfosec/insecureapi
   ```

3. Access the Swagger documentation for API guidance and testing by navigating to:
   ```
   http://localhost:9093/swagger-ui.html
   ```

## Technologies Used
- **Spring Boot**: Framework used to develop the API.
- **GitHub Actions**: Automates the CI/CD pipeline, including building the Docker image.
- **Docker**: Containerization platform for running the API in different environments.
