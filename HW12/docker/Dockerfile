FROM bellsoft/liberica-openjdk-alpine:11.0.6
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} app.jar
CMD ["java","-Xms400m","-Xmx400m", "-jar", "app.jar"]