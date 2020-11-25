#!/bin/bash

if [ -f "./.env" ]; then
    sed -i 's/\r//'   ./.env
    . ./.env
    docker cp LabStore.war ${COMPOSE_PROJECT_NAME}_server_1:/usr/local/tomcat/webapps/ROOT.war
    echo 'done'
else
    echo "not exist .env" && exit 1
fi