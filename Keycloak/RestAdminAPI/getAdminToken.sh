#!/bin/bash
set -e
set -u -o pipefail

if [[ $# -ne 3 ]]; then
        echo "Wrong number of parameters" >&2
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password>" >&2
        exit 1
fi

SCRIPT_DIR="$(dirname "$0")"
KEYCLOAK_HOST="$1"
ADMIN_NAME="$2"
ADMIN_PASSWORD="$3"

sh $SCRIPT_DIR/Token/getTokenGrantTypePassword.sh $KEYCLOAK_HOST "master" "admin-cli" $ADMIN_NAME $ADMIN_PASSWORD
