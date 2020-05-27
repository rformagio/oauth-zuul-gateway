FROM adoptopenjdk:8u212-b04-jre-hotspot-bionic
COPY target/zuul-gateway.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
EXPOSE 9090