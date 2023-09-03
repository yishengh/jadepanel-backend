# Use a base image with Java 20 installed
FROM openjdk:20

# Set the working directory
WORKDIR /app

# Copy your JAR files to the container
COPY ./jade-bills/target/jade-bills-0.0.1-SNAPSHOT.jar ./app/
COPY ./jade-equity/target/jade-equity-0.0.1-SNAPSHOT.jar ./app/
COPY ./jade-finance/target/jade-finance-0.0.1-SNAPSHOT.jar ./app/
COPY ./jade-gateway/target/jade-gateway-0.0.1-SNAPSHOT.jar ./app/
COPY ./jade-registry/target/jade-registry-0.0.1-SNAPSHOT.jar ./app/
COPY ./jade-report/target/jade-report-0.0.1-SNAPSHOT.jar ./app/
COPY ./jade-user/jade-user-service/target/jade-user-service-0.0.1-SNAPSHOT.jar ./app/

# Create a "public" folder
RUN mkdir public

# Expose ports
EXPOSE 10010
EXPOSE 8082
EXPOSE 8081
EXPOSE 8083
EXPOSE 8085
EXPOSE 8086
EXPOSE 10086

# Define the startup command to run all JAR files
CMD ["java", "-jar", "jade-bills-0.0.1-SNAPSHOT.jar", "jade-equity-0.0.1-SNAPSHOT.jar", "jade-finance-0.0.1-SNAPSHOT.jar", "jade-gateway-0.0.1-SNAPSHOT.jar", "jade-registry-0.0.1-SNAPSHOT.jar", "jade-report-0.0.1-SNAPSHOT.jar", "jade-user-service-0.0.1-SNAPSHOT.jar"]
