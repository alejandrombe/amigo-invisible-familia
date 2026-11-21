# Etapa 1: Construcción (Build)
FROM eclipse-temurin:17-jdk-jammy AS build
WORKDIR /app
# Copiamos los archivos de configuración de Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Le damos permisos de ejecución al wrapper (evita errores de Linux)
RUN chmod +x mvnw
# Copiamos el código fuente y compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecución (Run)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
# Copiamos el archivo .jar generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar
# Exponemos el puerto de Spring Boot
EXPOSE 8080
# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]