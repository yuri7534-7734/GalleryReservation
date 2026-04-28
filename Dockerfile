# 17에서 21로 변경합니다.
FROM amazoncorretto:21-alpine-jdk

# 2. 나머지는 그대로 둡니다.
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]