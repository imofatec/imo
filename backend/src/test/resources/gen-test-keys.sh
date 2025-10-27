
#! /bin/bash

openssl genrsa -out src/test/resources/test-private-key.pem 2048
openssl rsa -in src/test/resources/test-private-key.pem -pubout -out src/test/resources/test-public-key.pem
