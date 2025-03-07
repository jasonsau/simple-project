FROM maven AS package
WORKDIR /app
COPY ./domain/ ./domain
COPY ./api/ ./api
RUN mvn clean install -f ./domain
RUN mvn clean install -f ./api

FROM openjdk:17-jdk-alpine AS builder
WORKDIR /builder
COPY --from=package /app/api/target/*.jar application.jar
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

FROM openjdk:17-jdk-alpine AS runner
VOLUME /tmp
EXPOSE 8080
WORKDIR /application
COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./
WORKDIR /
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev","-Duser.timezone=America/El_Salvador","-Djava.awt.headless=true", "/application/application.jar"]
