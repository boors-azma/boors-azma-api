FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY boors-azma-api/pom.xml ./pom.xml
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp -DskipTests dependency:go-offline

COPY boors-azma-api/src ./src
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -ntp -DskipTests clean package && \
    JAR_FILE="$(find target -maxdepth 1 -type f -name '*.jar' ! -name '*.original' | head -n 1)" && \
    test -n "$JAR_FILE" && \
    cp "$JAR_FILE" /app/app.jar

FROM gcr.io/distroless/java21-debian12:nonroot
WORKDIR /app

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

COPY --from=build --chown=65532:65532 /app/app.jar /app/app.jar
COPY --chown=65532:65532 --chmod=0444 market.csv /app/market.csv

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
