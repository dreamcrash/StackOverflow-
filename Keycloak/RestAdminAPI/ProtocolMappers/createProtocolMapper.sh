# Original Question: https://stackoverflow.com/questions/66912222/how-to-create-mapper-for-each-user-attribute-in-keycloak-via-rest-api/
set -e 
set -u -o pipefail

if [[ $# -ne 6 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Client ID> <Mapper Json Representation>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
CLIENT_ID="$5"
MAPPER_JSON="$6"

ADMIN_TOKEN=$(sh $SCRIPT_DIR/../getAdminToken.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD")
ACCESS_TOKEN=$(echo $ADMIN_TOKEN | jq -r .access_token)
CLIENT=$(./$SCRIPT_DIR/../Clients/getClient.sh "$KEYCLOAK_HOST" "$ADMIN_NAME" "$ADMIN_PASSWORD" "$REALM_NAME" "$CLIENT_ID")
ID_OF_CLIENT=$(echo $CLIENT | jq -r .id)

curl -k -sS	-X POST "http://$KEYCLOAK_HOST/auth/admin/realms/$REALM_NAME/clients/$ID_OF_CLIENT/protocol-mappers/models" \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer $ACCESS_TOKEN" \
		-d "$MAPPER_JSON"

sh $SCRIPT_DIR/../logoutAdminSession.sh "$KEYCLOAK_HOST" "$ADMIN_TOKEN"
