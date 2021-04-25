# Original Question: https://stackoverflow.com/questions/65404692
 
#!/bin/bash

set -e 
set -u -o pipefail

if [[ $# -ne 6 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Username> <Role Name>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
USERNAME="$5"
ROLE_NAME="$6"

ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)

USER=$(sh $SCRIPT_DIR/../Users/getUserByName.sh $KEYCLOAK_HOST $ADMIN_NAME $ADMIN_PASSWORD $REALM_NAME $USERNAME)
USER_ID=$(echo $USER | jq -r .id)

ROLE=$(sh $SCRIPT_DIR/getRealmRoleByName.sh $KEYCLOAK_HOST $ADMIN_NAME $ADMIN_PASSWORD $REALM_NAME $ROLE_NAME)
ROLE_ID=$(echo $ROLE | jq -r .id)
ROLE_JSON="[{\"id\":\"$ROLE_ID\",\"name\":\"$ROLE_NAME\"}]"

curl -k -sS	-X POST "http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/users/$USER_ID/role-mappings/realm" \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer $ACCESS_TOKEN" \
		-d "$ROLE_JSON"

sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
