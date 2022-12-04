# Original Question : https://stackoverflow.com/q/74668939/1366871

#!/bin/bash
set -e
set -u -o pipefail

if [[ $# -ne 3 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Realm Name> <AccessToken>" >&2
        exit 1
fi

KEYCLOAK_HOST="$1"
REALM_NAME="$2"
ACCESS_TOKEN="$3"

curl -k -sS	-X GET "http://$KEYCLOAK_HOST/auth/realms/$REALM_NAME/protocol/openid-connect/userinfo" \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer $ACCESS_TOKEN"
