FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apk update && apk add --no-cache maven && \
    mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/stock-control-app-1.0.0.jar"]
