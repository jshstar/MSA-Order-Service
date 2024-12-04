# Java 17 기반 이미지 사용
FROM eclipse-temurin:17-jdk-alpine

# 작업 디렉토리 설정
WORKDIR /app

# 빌드 시 JAR 파일 이름을 동적으로 전달
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# JAR 파일 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]
