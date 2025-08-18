# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file into the container
COPY build/libs/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

# NOTE:
# When running this container, you MUST provide Postgres connection environment variables:
#   SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/wizardjournal
#   SPRING_DATASOURCE_USERNAME=youruser
#   SPRING_DATASOURCE_PASSWORD=yourpassword
# Example:
# docker run -p 8080:8080 \
#   -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/wizardjournal \
#   -e SPRING_DATASOURCE_USERNAME=youruser \
#   -e SPRING_DATASOURCE_PASSWORD=yourpassword \
#   wizardjournal
#
# The Postgres user (SPRING_DATASOURCE_USERNAME) must exist in the database.
# If you use the docker command below, the user and database will be created automatically:
# docker run --name wizardjournal-postgres \
#   -e POSTGRES_DB=wizardjournal \
#   -e POSTGRES_USER=wizarduser \
#   -e POSTGRES_PASSWORD=wizardpass \
#   -p 5432:5432 \
#   -d postgres:15
#
# Then run your Spring Boot container with:
# docker run -p 8080:8080 \
#   -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/wizardjournal \
#   -e SPRING_DATASOURCE_USERNAME=wizarduser \
#   -e SPRING_DATASOURCE_PASSWORD=wizardpass \
#   wizardjournal
