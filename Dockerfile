# Step 1: Use a Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Step 2: Create a directory for the app
WORKDIR /app

# Step 3: Copy all files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# --- ADD THIS LINE HERE ---
RUN chmod +x gradlew
# --------------------------

# Step 4: Build the application inside the container
RUN ./gradlew clean bootJar

# Step 5: Run the application
ENTRYPOINT ["java", "-jar", "build/libs/smart_campus-0.0.1-SNAPSHOT.jar"]
