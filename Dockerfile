# Build Stage - Trigger cache invalidation by adding a dummy file
FROM openjdk:8-jdk-alpine as cache-invalidator
WORKDIR /workspace/cache
RUN echo "Cache invalidator" > dummy.txt

# Main Build Stage
FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
RUN chmod +x ./mvnw
# Copy the cache invalidator to invalidate Docker cache
COPY --from=cache-invalidator /workspace/cache/dummy.txt /workspace/cache/
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Final Stage
FROM openjdk:8-jdk-alpine

# Set up volumes and arguments
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency

# Copy dependencies and classes
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Entry Point for Starting the Application
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.example.demo.DemoApplication"]
