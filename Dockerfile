# Stage 1: 애플리케이션 빌드
FROM gradle:7.2.0-jdk11 AS build

# 빌드 작업 디렉토리 지정
WORKDIR /build

# Gradle 사용자에게 소스 코드 디렉토리의 권한을 부여합니다.
COPY --chown=gradle:gradle . /build

# 빌드 단계에서 일시적으로 root 권한을 사용합니다.
USER root

# 권한 설정
RUN chown -R gradle /home/gradle/src

# 현재 디렉토리의 모든 파일을 /build 디렉토리로 복사합니다.
COPY . .

# clean 명령으로 이전 빌드 결과를 정리하고, bootJar 명령으로 Spring Boot 애플리케이션을 패키징한 JAR 파일을 생성합니다.
RUN gradle clean bootJar

# Stage 2: 런타임 이미지를 생성한다.
# 사용할 베이스 이미지를 선택합니다. Java 11을 사용하는 이미지를 선택합니다.
FROM adoptopenjdk/openjdk11:latest as runtime

# 애플리케이션 작업 디렉토리 설정
WORKDIR /app

# 호스트 머신에서 JAR 파일을 컨테이너로 복사합니다.
COPY build/libs/*.jar app.jar

# 컨테이너가 시작될 때 실행할 명령어를 설정합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]