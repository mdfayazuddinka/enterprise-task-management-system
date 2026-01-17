FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Install Maven
RUN apk add --no-cache maven nginx

# Copy your pom.xml and src folder first
COPY pom.xml .
COPY src ./src

# Build the JAR inside Docker
RUN mvn clean package -DskipTests

# Copy nginx config
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD sh -c "java -jar target/*.jar --server.port=$PORT & nginx -g 'daemon off;'"

