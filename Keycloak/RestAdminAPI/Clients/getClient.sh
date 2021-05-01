# Original Question: https://stackoverflow.com/questions/64929487
 
#!/bin/bash
set -e 
set -u -o pipefail

if [[ $# -ne 5 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Client ID>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
CLIENT_ID="$5"


ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)


CLIENT=$(curl -k -sS	-X GET "http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/clients?clientId=$CLIENT_ID" \
			-H "Content-Type: application/json" \
			-H "Authorization: Bearer $ACCESS_TOKEN")

sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
RESULT="$(echo "$CLIENT" | jq -r type)"
if [ "$RESULT" == "array" ]; then
	echo "$CLIENT" | jq -r .[0]
else
        echo "$CLIENT">&2
	exit 1
fi
