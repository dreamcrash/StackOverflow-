# Original Question: https://stackoverflow.com/questions/64929487
 
#!/bin/bash
KEYCLOAK_HOST=$1
ADMIN_NAME=$2
ADMIN_PASSWORD=$3
REALM_NAME=$4
CLIENT_ID=$5

if [[ $# -ne 5 ]]; then
        echo "Wrong number of parameters"
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Client ID>"
        exit 1
fi

ADMIN_TOKEN=$(sh ../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)

CLIENT=$(sh getClient.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD" "$REALM_NAME" "$CLIENT_ID")

ID=$(echo $CLIENT | jq -r .id)
curl -k -sS	-X GET http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/clients/$ID/client-secret \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer $ACCESS_TOKEN"


sh ../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
