# Use a lightweight JDK base image
FROM eclipse-temurin:17-jdk-alpine

# Create a user (optional security)
RUN adduser -D springuser
USER springuser

# Set working dir
WORKDIR /app

# Copy jar from build context
COPY build/libs/app.jar app.jar

# Expose app port
EXPOSE 8080

# Run Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
