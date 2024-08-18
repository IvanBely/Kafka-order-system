FROM openjdk:21-jdk
LABEL maintainer="Ivan Bely"
COPY target/notification-service-0.0.1-SNAPSHOT.jar notification-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notification-service.jar"]