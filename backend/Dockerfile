FROM maven:3-eclipse-temurin-22-alpine AS MAVEN_BUILD
COPY ./ ./
# skipping tests because db connection test fails and prevents building .jar
RUN mvn clean package -Dmaven.test.skip

# using eclipse image, as the official openjdk images are deprecated https://hub.docker.com/_/openjdk#:~:text=Tags-,DEPRECATION%20NOTICE,-This%20image%20is
FROM eclipse-temurin:22-jdk-jammy
ARG APP_VERSION=0.0.1-SNAPSHOT
ENV APP_VERSION=${APP_VERSION}
COPY --from=MAVEN_BUILD target/PatentConnector.server-${APP_VERSION}.jar /volontario-server.jar

COPY ./entrypoint.sh /

RUN apt-get update && chmod 755 /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]