#! /bin/bash

openssl genrsa -out ./src/main/resources/app.key 2048
openssl rsa -in ./src/main/resources/app.key -pubout -out ./src/main/resources/app.pub
