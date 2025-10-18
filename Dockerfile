FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests=true

FROM openjdk:17-alpine

WORKDIR /app

COPY . .

COPY --from=builder /app/target/*.jar /app/OrderService.jar

#RUN apt-get update && apt-get install -y curl
RUN apk update && apk add curl

CMD ["java", "-jar", "OrderService.jar"]