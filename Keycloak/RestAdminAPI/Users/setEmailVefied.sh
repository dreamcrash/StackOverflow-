# Original Question: https://stackoverflow.com/questions/64872813
 
#!/bin/bash
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
USERNAME="$5"
EMAIL_VERIFIED="$6"

set -e 
set -u -o pipefail

SCRIPT_DIR="$(dirname "$0")"

if [[ $# -ne 6 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Username> <emailVerified>" >&2
        exit 1
fi

if [[ "$EMAIL_VERIFIED" != "true" && "$EMAIL_VERIFIED" != "false" ]]; then
	echo "The emailVerified must be either \"true\" or \"false\"" >&2
	exit 1
fi

ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)


USERNAME=$(sh $SCRIPT_DIR/getUser.sh $KEYCLOAK_HOST $ADMIN_NAME $ADMIN_PASSWORD $REALM_NAME $USERNAME)

USER_ID=$(echo $USERNAME | jq -r .id)

curl -k -sS -X PUT http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/users/$USER_ID \
        -H "Content-Type: application/json" \
        -H "Authorization: bearer $ACCESS_TOKEN" \
        -d "{\"emailVerified\":${EMAIL_VERIFIED}}"

sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
