#!/bin/sh

#DB POSTGRES RELATED
docker rm -f db-postgres-lumaq
docker rmi -f db-postgres-lumaq-image

docker volume rm $(docker volume ls --filter name=postgres-data -qf dangling=true)
docker volume rm $(docker volume ls --filter name=postgres-logs -qf dangling=true)

#APP LUMAQ RELATED
docker rm -f app-lumaq
docker rmi -f app-lumaq-image


