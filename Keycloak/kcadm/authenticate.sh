#!bin/bash
KEYCLOAK_IP=$1
ADMIN_NAME=$2
ADMIN_PASSWORD=$3

if [[ $# -ne 3 ]]; then
	echo "Wrong number of parameters"
	echo "Usage: $0 <keycloak_ip> <admin_name> <admin_password>"
	exit 1
fi


./kcadm.sh config credentials --server https://$KEYCLOAK_IP/auth --realm master --user $ADMIN_NAME --password $ADMIN_PASSWORD
