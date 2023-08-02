FROM ubuntu:latest
LABEL authors="david.zuluaga_pragma"
EXPOSE 8081
 # Instalar Java
RUN apt-get update && apt-get install -y openjdk-17-jdk
ADD target/user-micro-docker.jar user-micro-docker.jar
ENTRYPOINT ["java","-jar", "/user-micro-docker.jar"]