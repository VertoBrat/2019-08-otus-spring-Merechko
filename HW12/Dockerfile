#### BUILD image ###
FROM maven:3.6-jdk-11-slim as builder

RUN mkdir -p /build
WORKDIR /build
COPY pom.xml /build

RUN mvn -B dependency:resolve dependency:resolve-plugins
COPY src /build/src
RUN mvn package -Dmaven.test.skip=true

### RUN ###
FROM bellsoft/liberica-openjdk-alpine:11.0.6 as runtime

EXPOSE 8080
ENV APP_HOME /app
# Add docker-compose-wait tool -------------------
ENV WAIT_VERSION 2.7.2
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/$WAIT_VERSION/wait /wait
RUN chmod +x /wait

RUN mkdir $APP_HOME
RUN mkdir $APP_HOME/src
WORKDIR $APP_HOME

COPY --from=builder /build/src  $APP_HOME/src
COPY --from=builder /build/target $APP_HOME/target
COPY --from=builder /build/target/*.jar app.jar

CMD ["java","-Xms400m","-Xmx400m", "-jar", "app.jar"]