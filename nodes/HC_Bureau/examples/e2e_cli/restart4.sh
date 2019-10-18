#!/bin/bash +x
tar -xvf crypto-config.tar
tar -xvf channel-artifacts.tar
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi --force `docker images | grep dev | awk '{print $3}'`
cd base
docker-compose -f kafka4.yaml up -d
cd ..
docker-compose -f orderer3.yaml up -d
docker-compose -f peer0org4.yaml up -d
docker-compose -f peer1org4.yaml up -d

