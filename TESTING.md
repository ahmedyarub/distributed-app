# Testing Instructions

This project includes a Docker Compose setup to run the microservices with an Nginx reverse proxy.

## Prerequisites

- Docker
- Docker Compose

## Running the Services

1.  Build and start the containers:
    ```bash
    docker compose up -d --build
    ```

2.  Verify the services are running:
    ```bash
    docker compose ps
    ```

## Testing the Endpoints

The Nginx server exposes port 80. You can access the caller services via the following paths:

### Caller Feign Client
- **URL**: `http://localhost/caller_feign_client/annotation/lower/{param}`
- **Example**:
    ```bash
    curl http://localhost/caller_feign_client/annotation/lower/TEST
    ```
- **Expected Output**: `test` (converted to lowercase)

### Caller Rest Template
- **URL**: `http://localhost/caller_rest_template/rest_template_client_router/{param}`
- **Example**:
    ```bash
    curl http://localhost/caller_rest_template/rest_template_client_router/TEST
    ```
- **Expected Output**: `test`

### Caller Web Client
- **URL**: `http://localhost/caller_web_client/web_client_annotation/{param}`
- **Example**:
    ```bash
    curl http://localhost/caller_web_client/web_client_annotation/TEST
    ```
- **Expected Output**: `test`

## Verifying Security

The responder services are not directly accessible from the host.

- Trying to access a responder directly should fail or be blocked by Nginx:
    ```bash
    curl -I http://localhost/responder/
    curl -I http://localhost/responder_mvc_annotation/
    ```
- **Expected Output**: `403 Forbidden` or `404 Not Found` (depending on Nginx config match).

## Stopping the Services

To stop and remove the containers:
```bash
docker compose down
```
