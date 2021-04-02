#!/bin/bash
KEYCLOAK_HOST="$1"
TOKEN="$2"

set -e
set -u -o pipefail

if [[ $# -ne 2 ]]; then
   	echo "Wrong number of parameters" >&2
   	echo "Usage: $0 <Keycloak Host> <Token>" >&2
        exit 1
fi

ACCESS_TOKEN=$(echo $TOKEN | jq -r .access_token)
SESSION_STATE=$(echo $TOKEN | jq -r .session_state)

curl -k -sS	-X DELETE http://$KEYCLOAK_HOST/auth/admin/realms/master/sessions/$SESSION_STATE \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer $ACCESS_TOKEN"
 

