#!/bin/bash

echo "Performing a clean Maven build"
mvn clean package -DskipTests=true

cd zuul-gateway
mvn clean package -DskipTests=true
cd ..

echo "Building the AUTH-SERVER"
cd auth-server
docker build --tag rformagio/auth-server .
cd ..

echo "Building the ZUUL-GATEWAY"
cd zuul-gateway
docker build --tag rformagio/zuul-gateway .
cd ..

echo "Building the APP-CEP"
cd app-cep
docker build --tag rformagio/app-cep .
cd ..

echo "Building the APP-PERSON"
cd app-person
docker build --tag rformagio/app-person .
cd ..

echo "Building the EUREKA-SERVER"
cd eureka-server
docker build --tag rformagio/eureka-server .
cd ..
