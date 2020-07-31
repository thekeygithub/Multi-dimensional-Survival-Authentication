#!/bin/bash +x
tar -xvf crypto-config.tar
tar -xvf channel-artifacts.tar
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi --force `docker images | grep dev | awk '{print $3}'`
cd base
docker-compose -f zookeeper1.yaml up -d
docker-compose -f kafka2.yaml up -d
cd ..
docker-compose -f orderer1.yaml up -d
docker-compose -f peer0org2.yaml up -d
docker-compose -f peer1org2.yaml up -d

