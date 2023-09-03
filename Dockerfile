# Use a base image with Java 20 installed
FROM openjdk:20

# Set the working directory
WORKDIR /app

# Copy your JAR files to the container
COPY ./jade-registry/target/jade-registry-0.0.1-SNAPSHOT.jar ./app/jade-registry-0.0.1-SNAPSHOT.jar

# Create a "public" folder
RUN mkdir /app/public
RUN java -version
RUN chmod 777 *.jar
# Expose ports
EXPOSE 10086

# Define the startup command to run only the registry JAR file and list files
CMD java -jar /app/jade-registry-0.0.1-SNAPSHOT.jar
