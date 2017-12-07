FROM openjdk:8-jdk-alpine

RUN mkdir /app

WORKDIR /app

ADD ./target/ponudniki-storitev-0.1-SNAPSHOT.jar /app

EXPOSE 8081:8081

CMD ["java", "-jar", "ponudniki-storitev-0.1-SNAPSHOT.jar"]
