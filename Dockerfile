FROM openjdk:8-jdk-alpine

WORKDIR /usr/app

COPY ./target/WebSocketServer-0.0.1-SNAPSHOT.jar ./

CMD ["java", "-jar", "WebSocketServer-0.0.1-SNAPSHOT.jar"]