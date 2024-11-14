# Use OpenJDK 11 as the base image
FROM core.harbor.apps.n2ocp-pclus-02.india.airtel.itm/library/openjdk:8-jdk-alpine

ENV TZ=Asia/Kolkata 
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone

ADD Kolkata /usr/share/zoneinfo/Asia/Kolkata

WORKDIR /u01/spring-apps-home/airtelboeservice

# Metadata as a label
LABEL maintainer="a_nishant.sinha@airtel.com" version="1.0" description="Airtel Boe MicroService"

# Copy the application JAR into the container
COPY target/airtelboeservice-0.0.1-SNAPSHOT.jar /u01/spring-apps-home/airtelboeservice/airtelboeservice-0.0.1-SNAPSHOT.jar

# Set environment variable for context path
#ENV CONTEXT_PATH="/accountmaintenance"

###  "-Dserver.servlet.context-path=${CONTEXT_PATH}"
# Command to run the application
CMD ["java", "-jar", "/u01/spring-apps-home/airtelboeservice/airtelboeservice-0.0.1-SNAPSHOT.jar"]