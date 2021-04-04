# Original Question: https://stackoverflow.com/questions/65215817
 
#!/bin/bash
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
EXPORT_CLIENTS="$5" # true or false
EXPORT_GROUP_AND_ROLES="$6" # true or false

set -e 
set -u -o pipefail

SCRIPT_DIR="$(dirname "$0")"

if [[ $# -ne 6 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Export Clients> <Export groups and Roles>" >&2
        exit 1
fi

if [[ "$EXPORT_CLIENTS" != "true" && "$EXPORT_CLIENTS" != "false" ]]; then
	echo "The exportClients  must be either \"true\" or \"false\"" >&2
	exit 1
fi

if [[ "$EXPORT_GROUP_AND_ROLES" != "true" && "$EXPORT_GROUP_AND_ROLES" != "false" ]]; then
        echo "The exportGroupsAndRoles must be either \"true\" or \"false\"" >&2
        exit 1
fi


ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)

ENDPOINT_URL="http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/partial-export?exportClients=$EXPORT_CLIENTS&exportGroupsAndRoles=$EXPORT_GROUP_AND_ROLES"


curl -k -sS 	-X POST $ENDPOINT_URL \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer $ACCESS_TOKEN"

sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
