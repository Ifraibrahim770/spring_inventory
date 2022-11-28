# AS <NAME> to name this stage as maven
FROM maven:3.8.4 AS maven

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package

# For Java 17,
FROM openjdk:17-oracle

ARG JAR_FILE=inventory-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app


COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/
EXPOSE 8080
ENTRYPOINT ["java","-jar","inventory-0.0.1-SNAPSHOT.jar"]

