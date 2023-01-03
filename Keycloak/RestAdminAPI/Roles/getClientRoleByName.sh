# Original Question: https://stackoverflow.com/questions/71317507
 
#!/bin/bash

set -e 
set -u -o pipefail

if [[ $# -ne 6 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Client Name> <Role Name>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
CLIENT_NAME="$5"
ROLE_NAME="$6"

ADMIN_TOKEN="$(sh "${SCRIPT_DIR}/../getAdminToken.sh" "${KEYCLOAK_HOST}" "${ADMIN_NAME}" "${ADMIN_PASSWORD}")"
ACCESS_TOKEN="$(echo "${ADMIN_TOKEN}" | jq -r .access_token)"

CLIENT="$(sh "${SCRIPT_DIR}/../Clients/getClient.sh" "${KEYCLOAK_HOST}" "${ADMIN_NAME}" "${ADMIN_PASSWORD}" "${REALM_NAME}" "${CLIENT_NAME}")"

ID_OF_CLIENT="$(echo "${CLIENT}" | jq -r .id)"

curl -k -sS	-X GET "http://${KEYCLOAK_HOST}/auth/admin/realms/${REALM_NAME}/clients/${ID_OF_CLIENT}/roles/${ROLE_NAME}" \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer ${ACCESS_TOKEN}"


sh "${SCRIPT_DIR}/../logoutAdminSession.sh" "${KEYCLOAK_HOST}" "${ADMIN_TOKEN}"
