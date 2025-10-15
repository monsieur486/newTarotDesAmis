# Build
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . /app
RUN mvn -q -B -DskipTests package

# Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app
COPY --from=build /app/target/tda-ws-http-1.0.0.jar /opt/app/app.jar
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseZGC" TZ=Europe/Paris
ENV SPRING_PROFILES_ACTIVE=prod
ENV APP_ALLOWED_ORIGINS=https://tda.mr486.com
EXPOSE 9001
HEALTHCHECK --interval=15s --timeout=3s --retries=5 CMD wget -qO- http://localhost:9001/actuator/health || exit 1
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /opt/app/app.jar"]
