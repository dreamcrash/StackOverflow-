# Original Question: https://stackoverflow.com/questions/64929487
 
#!/bin/bash
set -e 
set -u -o pipefail

if [[ $# -ne 4 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password> <Realm Name>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"
REALM_NAME="$4"

REALM=$(sh $SCRIPT_DIR/getRealm.sh $KEYCLOAK_HOST $ADMIN_NAME $ADMIN_PASSWORD $REALM_NAME)
echo $REALM | jq -r .passwordPolicy
