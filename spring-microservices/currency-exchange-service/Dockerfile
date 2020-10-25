FROM openjdk:8-jdk-alpine

#Create logs directory
RUN mkdir logs

RUN addgroup -S spring && adduser -S spring -G spring

RUN chmod -R 755 logs
RUN chown -R spring logs

USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} currency-exchange-service.jar

# Note that using json format of ENTRYPOINT won't enable JAVA_OPTS and hence anything in application.properties will only take effet.
# ENTRYPOINT ["java","-jar","/currency-exchange-service.jar"]

# Starting the jar with ENTRYPOINT exec java $JAVA_OPTS, we can successfully enable changing memory settings using Docker environment variables
ENTRYPOINT exec java $JAVA_OPTS -jar /currency-exchange-service.jar

#Exposing the default port
EXPOSE 8000