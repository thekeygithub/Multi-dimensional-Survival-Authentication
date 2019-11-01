#!/bin/bash +x
tar -xvf crypto-config.tar
tar -xvf channel-artifacts.tar
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi --force `docker images | grep dev | awk '{print $3}'`
cd base
docker-compose -f zookeeper2.yaml up -d
docker-compose -f kafka3.yaml up -d
cd ..
docker-compose -f orderer2.yaml up -d
docker-compose -f peer0org3.yaml up -d
docker-compose -f peer1org3.yaml up -d
