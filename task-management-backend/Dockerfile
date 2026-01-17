FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

RUN apk add --no-cache nginx

COPY target/*.jar app.jar
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

CMD ["sh", "-c", "java -jar app.jar & nginx -g 'daemon off;'"]

