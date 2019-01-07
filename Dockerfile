# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 15000
RUN mkdir -p /opt/mass_upload

# The application's jar file
ARG JAR_FILE=target/mass_upload-1.0.4.jar

# Add the application's jar to the container
ADD ${JAR_FILE} /opt/mass_upload/mass_upload.jar

# Run the jar file
ENTRYPOINT exec java -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$PROFILE -jar opt/mass_upload/mass_upload.jar

