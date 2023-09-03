# Use a base image with Java 20 installed
FROM openjdk:20

# Copy your JAR files to the container
COPY ./jade-registry/target/jade-registry-0.0.1-SNAPSHOT.jar jade-registry-0.0.1-SNAPSHOT.jar

# Create a "public" folder
RUN mkdir public
RUN java -version
# Expose ports
EXPOSE 10086

# Define the startup command to run only the registry JAR file and list files
ENTRYPOINT ["java", "-jar", "jade-registry-0.0.1-SNAPSHOT.jar"]
