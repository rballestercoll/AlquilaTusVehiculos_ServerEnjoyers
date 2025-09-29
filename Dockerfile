# ====== Build stage ======
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiamos solo el pom primero para aprovechar la cache de dependencias
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Ahora copiamos el código
COPY src ./src

# Compilamos
RUN mvn -B -e -DskipTests package

# ====== Runtime stage (imagen ligera) ======
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el JAR desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Permite pasar opciones vía JAVA_OPTS si las necesitas
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
