#!/bin/bash

echo "====docker-compose cache clean===="
docker-compose build --no-cache

echo "====gradle clean build======"
./gradlew clean build

echo "======docker-compose up======="
docker-compose up --build
