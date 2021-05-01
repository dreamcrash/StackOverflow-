# Original Question: https://stackoverflow.com/questions/65772984
 
#!/bin/bash
set -e 
set -u -o pipefail

if [[ $# -ne 5 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Username>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
USERNAME="$5"

ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)


USERNAME=$(sh getUserByName.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD" "$REALM_NAME" "$USERNAME")
USER_ID=$(echo $USERNAME | jq -r .id)

curl -k -sS	-X DELETE "http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/users/$USER_ID" \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer $ACCESS_TOKEN"

sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
