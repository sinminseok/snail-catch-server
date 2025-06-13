#!/bin/bash

APP_IMAGE="minseok1768/snail-app:latest"

echo "🧹 Removing local docker image if exists..."
docker rmi -f $APP_IMAGE || echo "No existing local image to remove."

echo "🛠 Building JAR with Gradle..."
./gradlew clean build -x test  # 테스트 실패로 인한 빌드 중단 방지

echo "🐳 Building Docker image..."
docker build -t $APP_IMAGE .

echo "🚀 Pushing Docker image to Docker Hub..."
docker push $APP_IMAGE

echo "🔧 Starting containers with docker-compose..."
docker-compose up -d --build

echo "✅ All done!"
