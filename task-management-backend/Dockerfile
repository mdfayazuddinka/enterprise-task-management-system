# Use Eclipse Temurin JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Install Maven
RUN apk add --no-cache maven

# Copy Maven config & source code
COPY pom.xml . 
COPY src ./src

# Build the JAR inside Docker
RUN mvn clean package -DskipTests

# Expose Render's default port
EXPOSE 8080

# Run Spring Boot on the port Render provides
CMD ["sh", "-c", "java -jar target/*.jar --server.port=${PORT}"]
