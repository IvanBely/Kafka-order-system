FROM openjdk:21-jdk
LABEL maintainer="Ivan Bely"
COPY target/shipping-service-0.0.1-SNAPSHOT.jar shipping-service.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "shipping-service.jar"]
