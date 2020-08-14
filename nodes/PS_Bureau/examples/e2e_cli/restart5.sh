#!/bin/bash +x
tar -xvf crypto-config.tar
tar -xvf channel-artifacts.tar
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi --force `docker images | grep dev | awk '{print $3}'`
cd base
docker-compose -f kafka5.yaml up -d
cd ..
docker-compose -f orderer4.yaml up -d
docker-compose -f peer0org5.yaml up -d
docker-compose -f peer1org5.yaml up -d

