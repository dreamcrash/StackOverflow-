KEYCLOAK_IP=$1
ADMIN_USERNAME=$2
ADMIN_PASSWORD=$3
REALM_NAME=$4
USER_JSON_DATA=$(cat $5)

ADMIN_TOKEN="$(./getAdminToken.sh "$KEYCLOAK_IP" "$ADMIN_USERNAME" "$ADMIN_PASSWORD")"
ACCESS_TOKEN="$(echo $ADMIN_TOKEN | jq -r .access_token)"


username=$(echo $USER_JSON_DATA | jq -r .username)

curl -k -sS -X POST $KEYCLOAK_IP/auth/admin/realms/$REALM_NAME/users \
        -H "Content-Type: application/json" \
        -H "Authorization: Bearer $ACCESS_TOKEN" \
        -d "$USER_JSON_DATA"
 







