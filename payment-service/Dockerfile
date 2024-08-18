FROM openjdk:21-jdk
LABEL maintainer="Ivan Bely"
COPY target/payment-service-0.0.1-SNAPSHOT.jar payment-service.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "payment-service.jar"]
