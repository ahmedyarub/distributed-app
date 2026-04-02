@echo off
echo Starting microservices and Kong Gateway...
docker compose -f docker-compose-kong.yml up -d --build

echo.
echo Waiting for services to be ready...
timeout /t 15 /nobreak > NUL

echo Kong Gateway is available at:
echo   - Proxy: http://localhost:8000
echo   - Admin API: http://localhost:8001
echo.
echo You can test the endpoints using the test-kong.bat script.
