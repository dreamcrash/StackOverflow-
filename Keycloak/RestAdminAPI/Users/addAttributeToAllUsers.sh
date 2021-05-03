# Original Question: https://stackoverflow.com/questions/67369260
 
#!/bin/bash
set -e 
set -u -o pipefail

if [[ $# -ne 5 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Attribute>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
ATTRIBUTES="$5"

# ATTRIBUTES Example : '{"ExternalID":["<THE_EXTERNAL_ID>"]}'

ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)
USER=$(sh $SCRIPT_DIR/getUsers.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD" "$REALM_NAME")

echo $USER | jq -c '.[]' | while read user; do
	USERNAME=$(echo $user | jq -r .username)
	sh $SCRIPT_DIR/addUserAttribute.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD" "$REALM_NAME" "$USERNAME" "$ATTRIBUTES"
done

sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
