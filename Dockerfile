# base image
FROM --platform=linux/amd64 openjdk:21-jdk-slim
# 작업 디렉토리 설정
WORKDIR /app

# 빌드된 jar 파일을 이미지에 복사
ARG JAR_FILE=build/libs/server-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# 환경변수 (선택)
ENV JAVA_OPTS=""

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app.jar"]