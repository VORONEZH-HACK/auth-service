FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace/app
COPY . .
RUN ./gradlew build


FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /workspace/app/build /build
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
