# Imagen base ligera con OpenJDK 21
FROM eclipse-temurin:21-jdk-jammy

COPY target/nave-0.0.1-SNAPSHOT.jar nave.jar
# Expone el puerto en el que escucha tu aplicación
EXPOSE 8082

#ENTRYPOINT ["java", "-jar", "app.jar"]
ENTRYPOINT ["java", "-jar", "nave.jar"]