FROM openjdk:10-jre-slim
COPY ./target/react-demo-0.0.1-SNAPSHOT.jar /usr/src/react-demo/
WORKDIR /usr/src/react-demo
EXPOSE 8080
CMD ["java", "-jar", "react-demo-0.0.1-SNAPSHOT.jar"]