FROM openjdk:17-alpine
WORKDIR /opt/app
COPY target/webshop-0.0.1-SNAPSHOT.jar webshopapp.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/opt/app/webshopapp.jar"]