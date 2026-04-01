@echo off
set KONG_URL=http://localhost:8000
set API_KEY=secret-api-key-123

echo --------------------------------------------------------
echo   Testing Kong API Gateway Features
echo --------------------------------------------------------

echo 1. Testing UNAUTHORIZED access (No API Key)
echo Requesting /caller_feign_client/call without key...
curl -s -o NUL -w "HTTP Status: %%{http_code}\n" %KONG_URL%/caller_feign_client/call
echo.

echo 2. Testing AUTHORIZED access to all 3 callers (With API Key)
echo Requesting /caller_feign_client/call...
curl -s -H "apikey: %API_KEY%" %KONG_URL%/caller_feign_client/call
echo.
echo.

echo Requesting /caller_rest_template/call...
curl -s -H "apikey: %API_KEY%" %KONG_URL%/caller_rest_template/call
echo.
echo.

echo Requesting /caller_web_client/call...
curl -s -H "apikey: %API_KEY%" %KONG_URL%/caller_web_client/call
echo.
echo.

echo --------------------------------------------------------
echo   Testing Plugins
echo --------------------------------------------------------

echo 3. Testing Response Transformation Plugin (caller-web-client)
echo We expect to see 'x-kong-transformed: true' injected in the response headers
curl -s -I -H "apikey: %API_KEY%" %KONG_URL%/caller_web_client/call | findstr /I "x-kong"
echo.

echo 4. Testing Proxy Cache Plugin (caller-rest-template)
echo First request (Cache Miss expected)
curl -s -I -H "apikey: %API_KEY%" %KONG_URL%/caller_rest_template/call | findstr /I "X-Cache-Status"
echo Second request (Cache Hit expected)
curl -s -I -H "apikey: %API_KEY%" %KONG_URL%/caller_rest_template/call | findstr /I "X-Cache-Status"
echo.

echo 5. Testing Rate Limiting Plugin (caller-feign-client)
echo The limit is set to 10 requests per minute. Making 12 rapid requests...
setlocal enabledelayedexpansion
for /L %%i in (1,1,12) do (
  for /f "delims=" %%a in ('curl -s -o NUL -w "%%{http_code}" -H "apikey: %API_KEY%" %KONG_URL%/caller_feign_client/call') do set STATUS=%%a
  echo Request %%i: HTTP !STATUS!
)
endlocal

echo.
echo --------------------------------------------------------
echo   Testing Complete
echo --------------------------------------------------------
