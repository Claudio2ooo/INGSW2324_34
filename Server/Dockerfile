FROM maven:3.8.4-openjdk-17-slim AS builder
COPY . /app
WORKDIR /app
RUN mvn clean verify sonar:sonar -Dsonar.projectKey=INGSW2324-Server -Dsonar.projectName='INGSW2324-Server' -Dsonar.host.url=http://host.docker.internal:9000 -Dsonar.token= #insert your token here

FROM amazoncorretto:21
COPY --from=builder /app/target/Server-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

