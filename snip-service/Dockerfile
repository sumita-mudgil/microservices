FROM openjdk:8-jdk-alpine
ADD target/snip-service.jar snip-service.jar
EXPOSE 2222
ENTRYPOINT ["java", "-jar", "/snip-service.jar"]