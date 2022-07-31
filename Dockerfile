FROM amazoncorretto:11-alpine-jdk

EXPOSE 8080
WORKDIR /app/
COPY ./build/libs/app.jar /app/app.jar

CMD ["/usr/bin/java", "-jar", "app.jar"]
