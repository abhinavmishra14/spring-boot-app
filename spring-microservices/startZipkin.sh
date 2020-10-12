#!/bin/sh

export ZIPKIN_JAR_PATH="${PWD}/zipkin-server-2.21.7/zipkin-server-2.21.7-exec.jar"
echo ZIPKIN_JAR_PATH: $ZIPKIN_JAR_PATH

init () {
    java -XshowSettings:properties -version 2>&1 | findstr "java.home"
	if [ "?" != "0"]; then
	   end
	fi
	
	startZipkin
}

startZipkin () {
    echo.
	echo Starting zipkin-server...
	echo JAVA_HOME: $JAVA_HOME
	
	#To start zipkin-server
	#java -jar $ZIPKIN_JAR_PATH
	
	#To start zipkin-server connected to rabbit mq
	#export RABBIT_URI=amqp://localhost
	#java -jar $ZIPKIN_JAR_PATH
	RABBIT_URI=amqp://localhost java -jar $ZIPKIN_JAR_PATH
	
	if [ "?" != "0"]; then
	   end
	fi
}

end () {
  echo Exiting..
  exit 0
}

init