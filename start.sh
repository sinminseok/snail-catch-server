echo "🛠 Building JAR with Gradle..."
./gradlew clean build

echo "🐳 Building Docker containers..."
docker-compose up --build