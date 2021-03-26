#!bin/bash
KEYCLOAK_IP=$1
ADMIN_NAME=$2
ADMIN_PASSWORD=$3

set -e
set -u -o pipefail
SCRIPT_DIR="$(dirname "$0")"

if [[ $# -ne 3 ]]; then
	echo "Wrong number of parameters" >&2
	echo "Usage: $0 <keycloak_ip> <admin_name> <admin_password>" >&2
	exit 1
fi


$SCRIPT_DIR/kcadm.sh config credentials --server https://$KEYCLOAK_IP/auth --realm master --user $ADMIN_NAME --password $ADMIN_PASSWORD
