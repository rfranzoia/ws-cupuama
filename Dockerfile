FROM openjdk:8-jdk-alpine
MAINTAINER Romeu Franzoia Jr <rfranzoia@gmail.com>
VOLUME /tmp
EXPOSE 8080
ADD target/cupuama-app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]