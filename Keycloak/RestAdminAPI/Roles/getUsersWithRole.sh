# Original Question: https://stackoverflow.com/questions/65292454
 
#!/bin/bash
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
ROLE_NAME="$5"

set -e 
set -u -o pipefail

SCRIPT_DIR="$(dirname "$0")"

if [[ $# -ne 5 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Role Name>" >&2
        exit 1
fi

ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)


curl -k -sS	-X GET "http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/roles/$ROLE_NAME/users" \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer $ACCESS_TOKEN"


sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
