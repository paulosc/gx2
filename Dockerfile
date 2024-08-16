# Use a base image with OpenJDK
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle settings.gradle ./

# Copy the Gradle wrapper and gradle folder
COPY gradle gradle
COPY gradlew .

# Give execute permission to gradlew
RUN chmod +x gradlew

# Copy the source code
COPY src src

# Run the Gradle build to package the application
RUN ./gradlew build --no-daemon

# Expose the port your application runs on
EXPOSE 8083

# Specify the command to run the application
CMD ["java", "-jar", "build/libs/gx2-0.0.1-SNAPSHOT.jar"]
