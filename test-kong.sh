#!/bin/bash

export KONG_URL="http://localhost:8000"
export API_KEY="secret-api-key-123"

echo "--------------------------------------------------------"
echo "  Testing Kong API Gateway Features                     "
echo "--------------------------------------------------------"

echo "1. Testing UNAUTHORIZED access (No API Key)"
echo "Requesting /caller_feign_client/call without key..."
curl -s -o /dev/null -w "HTTP Status: %{http_code}\n" $KONG_URL/caller_feign_client/call
echo ""

echo "2. Testing AUTHORIZED access to all 3 callers (With API Key)"
echo "Requesting /caller_feign_client/call..."
curl -s -H "apikey: $API_KEY" $KONG_URL/caller_feign_client/call
echo -e "\n"

echo "Requesting /caller_rest_template/call..."
curl -s -H "apikey: $API_KEY" $KONG_URL/caller_rest_template/call
echo -e "\n"

echo "Requesting /caller_web_client/call..."
curl -s -H "apikey: $API_KEY" $KONG_URL/caller_web_client/call
echo -e "\n"

echo "--------------------------------------------------------"
echo "  Testing Plugins                                       "
echo "--------------------------------------------------------"

echo "3. Testing Response Transformation Plugin (caller-web-client)"
echo "We expect to see 'x-kong-transformed: true' injected in the response headers"
# We'll use -I to get headers.
curl -s -I -H "apikey: $API_KEY" $KONG_URL/caller_web_client/call | grep "x-kong"
echo ""

echo "4. Testing Proxy Cache Plugin (caller-rest-template)"
echo "First request (Cache Miss expected)"
curl -s -I -H "apikey: $API_KEY" $KONG_URL/caller_rest_template/call | grep "X-Cache-Status"
echo "Second request (Cache Hit expected)"
curl -s -I -H "apikey: $API_KEY" $KONG_URL/caller_rest_template/call | grep "X-Cache-Status"
echo ""

echo "5. Testing Rate Limiting Plugin (caller-feign-client)"
echo "The limit is set to 10 requests per minute. Making 12 rapid requests..."
for i in {1..12}; do
  STATUS=$(curl -s -o /dev/null -w "%{http_code}" -H "apikey: $API_KEY" $KONG_URL/caller_feign_client/call)
  echo "Request $i: HTTP $STATUS"
done

echo ""
echo "--------------------------------------------------------"
echo "  Testing Complete                                      "
echo "--------------------------------------------------------"
