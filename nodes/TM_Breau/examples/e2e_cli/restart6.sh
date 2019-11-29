#!/bin/bash +x
tar -xvf crypto-config.tar
tar -xvf channel-artifacts.tar
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi --force `docker images | grep dev | awk '{print $3}'`
cd base
docker-compose -f kafka6.yaml up -d
cd ..
docker-compose -f orderer5.yaml up -d
docker-compose -f peer0org6.yaml up -d
docker-compose -f peer1org6.yaml up -d

