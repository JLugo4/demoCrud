# Build Stage
FROM maven:3.8.4-openjdk-17 as build
WORKDIR /workspace/app
COPY . .
RUN mvn clean install -DskipTests

# Final Stage
FROM openjdk:17-jdk-alpine

# Set up volumes and arguments
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency

# Copy dependencies and classes
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Entry Point for Starting the Application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.example.demo.DemoApplication"]
