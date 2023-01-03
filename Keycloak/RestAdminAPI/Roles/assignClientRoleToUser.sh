# Original Question: https://stackoverflow.com/questions/71317507
 
#!/bin/bash

set -e 
set -u -o pipefail

if [[ $# -ne 7 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name> <Client Name> <Username> <Role Name>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"
CLIENT_NAME="$5"
USERNAME="$6"
ROLE_NAME="$7"

ADMIN_TOKEN="$(sh "${SCRIPT_DIR}/../getAdminToken.sh" "${KEYCLOAK_HOST}" "${ADMIN_NAME}" "${ADMIN_PASSWORD}")"
ACCESS_TOKEN="$(echo "${ADMIN_TOKEN}" | jq -r .access_token)"

USER="$(sh "${SCRIPT_DIR}/../Users/getPerfectMatchUserByName.sh" "${KEYCLOAK_HOST}" "${ADMIN_NAME}" "${ADMIN_PASSWORD}" "${REALM_NAME}" "${USERNAME}")"
USER_ID="$(echo "${USER}" | jq -r .id)"

CLIENT_ROLE="$(sh "${SCRIPT_DIR}/getClientRoleByName.sh" "${KEYCLOAK_HOST}" "${ADMIN_NAME}" "${ADMIN_PASSWORD}" "${REALM_NAME}" "${CLIENT_NAME}" "${ROLE_NAME}" | jq -c [.])"

ID_OF_CLIENT="$(echo "${CLIENT_ROLE}" | jq -r .[].containerId)"

curl -k -sS     -X POST "http://${KEYCLOAK_HOST}/auth/admin/realms/${REALM_NAME}/users/${USER_ID}/role-mappings/clients/${ID_OF_CLIENT}" \
		-H "Content-Type: application/json" \
		-H "Authorization: Bearer ${ACCESS_TOKEN}" \
		-d "${CLIENT_ROLE}"

sh "${SCRIPT_DIR}/../logoutAdminSession.sh" "${KEYCLOAK_HOST}" "${ADMIN_TOKEN}"
