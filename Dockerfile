# FROM maven:3.5.2-jdk-8-alpine AS builder
FROM maven:3-jdk-11 AS builder


COPY . /home/processador
WORKDIR /home/processador

# RUN maven
RUN mvn clean package

# FROM openjdk:8-jre
FROM adoptopenjdk/openjdk11:alpine 

RUN mkdir -p /app

COPY --from=builder /home/processador/target/app.jar /app/app.jar

# Ports exposing
EXPOSE 10001

# COPY src/main/resources/config.properties /opt/config.properties

ENTRYPOINT echo JAVA_OPTS=$JAVA_OPTS && java -jar /app/app.jar

# para buildar a imagem
# docker build -t ms-mongo-image .

# para rodar o container a partir da imagem ms-mongo-image (tag)
# docker run --name ms-mongo-container -p 10001:10001 ms-mongo-image