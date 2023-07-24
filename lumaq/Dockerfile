############################################################################
# Copyright 2023 Leandro Rezende (fortbit.com.br)                          #
#                                                                          #
# Licensed under the Apache License, Version 2.0 (the "License");          #
# you may not use this file except in compliance with the License.         #
# You may obtain a copy of the License at                                  #
#                                                                          #
#     http:#www.apache.org/licenses/LICENSE-2.0                            #
#                                                                          #
# Unless required by applicable law or agreed to in writing, software      #
# distributed under the License is distributed on an "AS IS" BASIS,        #
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. #
# See the License for the specific language governing permissions and      #
# limitations under the License.                                           #
############################################################################

## Set the base image to ubuntu
FROM eclipse-temurin:17-jdk-alpine

# File Author / Maintainer
MAINTAINER Leandro Rezende

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

COPY target/*.jar app.jar

ENTRYPOINT ["java","-Djava.awt.headless=true -XX:+UseSerialGC -Xverify:none -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -Xss512k -XX:MaxRAM=512m","-jar","/app.jar"]