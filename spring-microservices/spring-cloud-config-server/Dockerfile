FROM openjdk:8-jdk-alpine

#Create logs directory
RUN mkdir logs
RUN mkdir config-repo

#Install git
RUN apk add --no-cache git

#import cert into java
COPY src/main/resources/certs/server.crt /opt/workdir/
RUN cd /opt/workdir && ls -l
 
RUN keytool -import -alias springconfig -keystore $JAVA_HOME/jre/lib/security/cacerts -trustcacerts -file /opt/workdir/server.crt -storepass changeit -noprompt

#RUN addgroup -S spring && adduser -S spring -G spring
#RUN chown -R spring config-repo
#RUN chmod -R 777 logs
#RUN chmod -R 777 config-repo
#RUN chown -R spring logs
#RUN chown -R spring config-repo
#USER spring:spring



ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} spring-cloud-config-server.jar

# Note that using json format of ENTRYPOINT won't enable JAVA_OPTS and hence anything in application.properties will only take effet.
# ENTRYPOINT ["java","-jar","/spring-cloud-config-server.jar"]

# Starting the jar with ENTRYPOINT exec java $JAVA_OPTS, we can successfully enable changing memory settings using Docker environment variables
ENTRYPOINT exec java $JAVA_OPTS -jar /spring-cloud-config-server.jar

#Exposing the default port
EXPOSE 8888