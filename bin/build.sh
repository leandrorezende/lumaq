#!/bin/sh

#Build App
mvn -f ../lumaq clean install -DskipTests

#Run Services
docker-compose -f ../docker-compose.yml up -d