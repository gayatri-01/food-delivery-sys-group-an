# Build stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . /app/
RUN mvn clean package

# Package stage
FROM openjdk:17-alpine
WORKDIR /app

# Define arguments without default values
ARG SERVICE_NAME
ARG PORT

# Ensure SERVICE_NAME and PORT are passed as arguments at build time
RUN if [ -z "$SERVICE_NAME" ]; then echo "SERVICE_NAME is required" && exit 1; fi
RUN if [ -z "$PORT" ]; then echo "PORT is required" && exit 1; fi

# Copy the JAR file based on the service name
COPY --from=build /app/${SERVICE_NAME}/target/${SERVICE_NAME}-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port from the argument
EXPOSE ${PORT}

# Start the application with the dynamic port
ENTRYPOINT ["java", "-jar", "app.jar"]