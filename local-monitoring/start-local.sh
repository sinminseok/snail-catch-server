#!/bin/bash

set -e

# 1. 프로젝트 루트 경로로 이동 (Dockerfile과 build.gradle이 있는 경로)
cd "$(dirname "$0")/.."

echo "✅ [1/3] Building JAR with Gradle..."
./gradlew clean build -x test

echo "✅ [2/3] Building Docker image..."
docker build -t snail-app:latest .

# 3. docker-compose.yml 있는 위치로 이동
cd local-monitoring

echo "✅ [3/3] Starting local stack with docker-compose..."
docker-compose up --build -d

echo "🚀 All services are up and running!"
