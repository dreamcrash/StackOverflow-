# Original Question: https://stackoverflow.com/questions/64246753
 
#!/bin/bash
set -e
set -u -o pipefail

if [[ $# -ne 5 ]]; then 
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Group Name>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
GROUP_NAME="$5"

ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)


GROUP=$(curl -k -sS	-X GET "http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/groups?search=$GROUP_NAME" \
			-H "Content-Type: application/json" \
			-H "Authorization: Bearer $ACCESS_TOKEN")

ID_GROUP=$(echo $GROUP | jq -r .[0].id)


NUMBERS=$(curl -k -sS	-X GET http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/groups/{$ID_GROUP}/members?max=-1 \
                	-H "Content-Type: application/json" \
                	-H "Authorization: Bearer $ACCESS_TOKEN")

echo $NUMBERS | jq length

sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
