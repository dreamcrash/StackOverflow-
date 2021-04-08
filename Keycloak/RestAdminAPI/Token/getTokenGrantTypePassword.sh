# Original Question : https://stackoverflow.com/questions/65220747

#!/bin/bash
KEYCLOAK_HOST="$1"
REALM_NAME="$2"
CLIENT_ID="$3"
USERNAME="$4"
PASSWORD="$5"


set -e
set -u -o pipefail

if [[ $# -ne 5 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Realm Name> <Client ID> <Username> <User Password>" >&2
        exit 1
fi

curl -k -sS	--request POST \
  		--url http://$KEYCLOAK_HOST/auth/realms/$REALM_NAME/protocol/openid-connect/token \
  		--data client_id=$CLIENT_ID \
  		--data username=$USERNAME \
  		--data password=$PASSWORD \
  		--data grant_type=password
