FROM openjdk:8-jdk-alpine

#Create logs directory
RUN mkdir logs

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} restful-webservices.jar

# Note that using json format of ENTRYPOINT won't enable JAVA_OPTS and hence anything in application.properties will only take effet.
# ENTRYPOINT ["java","-jar","/restful-webservices.jar"]

# Starting the jar with ENTRYPOINT exec java $JAVA_OPTS, we can successfully enable changing memory settings using Docker environment variables
ENTRYPOINT exec java $JAVA_OPTS -jar /restful-webservices.jar

#Exposing the default port
EXPOSE 8181