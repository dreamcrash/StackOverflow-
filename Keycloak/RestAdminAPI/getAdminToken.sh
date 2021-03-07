#!/bin/bash
KEYCLOAK_HOST=$1
ADMIN_NAME=$2
ADMIN_PASSWORD=$3

if [[ $# -ne 3 ]]; then
        echo "Wrong number of parameters"
    	echo "Usage: $0 <Keycloak Host> <Admin User Name> <Admin Password>"
        exit 1
fi

curl -k -sS	--request POST \
  		--url http://$KEYCLOAK_HOST/auth/realms/master/protocol/openid-connect/token \
  		--data client_id=admin-cli \
  		--data username=$ADMIN_NAME \
  		--data password=$ADMIN_PASSWORD \
  		--data grant_type=password
