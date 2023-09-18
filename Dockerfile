# Stage 1: 애플리케이션 빌드
FROM gradle:7.2.0-jdk11 AS build

WORKDIR /home/gradle/src/

COPY --chown=gradle:gradle . /home/gradle/src

USER root

RUN chown -R gradle /home/gradle/src

COPY . .

RUN gradle clean bootJar

# Stage 2: 런타임 이미지를 생성한다.
# 사용할 베이스 이미지를 선택합니다. Java 11을 사용하는 이미지를 선택합니다.
FROM adoptopenjdk/openjdk11:latest as runtime

WORKDIR /app

# 호스트 머신에서 JAR 파일을 컨테이너로 복사합니다.
COPY build/libs/*.jar app.jar

# 컨테이너가 시작될 때 실행할 명령어를 설정합니다.
ENTRYPOINT ["java", "-jar", "app.jar"]