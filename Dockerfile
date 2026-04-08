# 1. Amazon Corretto 17 (Java 17) 사용 - 가장 안정적입니다.
FROM amazoncorretto:17-alpine-jdk

# 2. 빌드된 jar 파일을 컨테이너 안으로 복사
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 3. 앱 실행 명령어
ENTRYPOINT ["java", "-jar", "/app.jar"]