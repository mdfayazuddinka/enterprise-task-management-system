FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar
COPY nginx.conf /etc/nginx/nginx.conf

RUN apk add --no-cache nginx

EXPOSE 80

CMD sh -c "java -jar app.jar & nginx -g 'daemon off;'"
