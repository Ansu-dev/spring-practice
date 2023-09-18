# Stage 1: 애플리케이션 빌드
FROM gradle:7.2.0-jdk11 AS build

WORKDIR /app

 # gradlew 복사
COPY gradlew .

# gradle 복사
COPY gradle gradle

# build.gradle 복사
COPY build.gradle .

# settings.gradle 복사
COPY settings.gradle .

# 웹 어플리케이션 소스 복사
COPY src src

# gradlew 실행권한 부여
RUN chmod +x ./gradlew

# gradlew를 사용하여 실행 가능한 jar 파일 생성
RUN ./gradlew bootJar

# Stage 2: 런타임 이미지를 생성한다.
# 사용할 베이스 이미지를 선택합니다. Java 11을 사용하는 이미지를 선택합니다.
FROM adoptopenjdk/openjdk11:latest as runtime

# 호스트 머신에서 JAR 파일을 컨테이너로 복사합니다.
COPY build/libs/*.jar app.jar

# 서버를 띄울 port
EXPOSE 8080

# 컨테이너가 시작될 때 실행할 명령어를 설정합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]