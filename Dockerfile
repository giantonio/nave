FROM openjdk:21-jdk-slim
COPY target/nave-0.0.1-SNAPSHOT.jar nave.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "nave.jar"]