FROM openjdk:8-jdk-alpine

#Install git
RUN apk add --no-cache git


#Setup config repo here as cloning on startup causing multiple issues
RUN git clone https://github.com/abhinavmishra14/spring-microservices-config-repo.git
#Check if repo is cloned successfully and has all the required files
RUN cd spring-microservices-config-repo && ls -l && cd ..

#Create spring user and group and provide permission to 'spring-microservices-config-repo' directory and all sub-folder/files
RUN addgroup -S spring && adduser -S spring -G spring
RUN chown -R spring spring-microservices-config-repo
RUN chmod -R 777 spring-microservices-config-repo

USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} spring-cloud-config-server.jar

# Note that using json format of ENTRYPOINT won't enable JAVA_OPTS and hence anything in application.properties will only take effet.
# ENTRYPOINT ["java","-jar","/spring-cloud-config-server.jar"]

# Starting the jar with ENTRYPOINT exec java $JAVA_OPTS, we can successfully enable changing memory settings using Docker environment variables
ENTRYPOINT exec java $JAVA_OPTS -jar /spring-cloud-config-server.jar

#Exposing the default port
EXPOSE 8888