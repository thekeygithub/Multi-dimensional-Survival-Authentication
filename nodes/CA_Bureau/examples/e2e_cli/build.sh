#!/bin/bash +x
rm -rf crypto-config
rm -rf channel-artifacts
rm -f crypto-config.tar
rm -f channel-artifacts.tar
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi --force `docker images | grep dev | awk '{print $3}'` 
