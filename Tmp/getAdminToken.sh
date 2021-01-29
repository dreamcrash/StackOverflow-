#!/bin/bash
KEYCLOAK_IP=$1
ADMIN_NAME=$2
ADMIN_PASSWORD=$3

 

if [[ $# -ne 3 ]]; then
        tput setaf 1; echo "Wrong number of parameters"
        tput setaf 1; echo "Usage: $0 <Keycloak IP> <Admin User Name> <Admin Password>"
        tput setaf 2;
        exit 1
fi

 

TOKEN=$(curl -k -sS     -d "client_id=admin-cli" \
                        -d "username=$ADMIN_NAME" \
                        -d "password=$ADMIN_PASSWORD" \
                        -d "grant_type=password" \
                        http://$KEYCLOAK_IP/auth/realms/master/protocol/openid-connect/token)
echo $TOKEN
