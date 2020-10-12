@ECHO OFF

SET ZIPKIN_JAR_PATH=.\zipkin-server-2.21.7\zipkin-server-2.21.7-exec.jar
echo ZIPKIN_JAR_PATH: %ZIPKIN_JAR_PATH%


:init
    java -XshowSettings:properties -version 2>&1 | findstr "java.home"
	if errorlevel 1 (GOTO end)

:startZipkin
    echo.
	echo Starting zipkin-server...
	echo JAVA_HOME: %JAVA_HOME%
	
	:: To start zipkin-server
	::java -jar %ZIPKIN_JAR_PATH%
	
	:: To start zipkin-server connected to rabbit mq
	SET RABBIT_URI=amqp://localhost
	java -jar %ZIPKIN_JAR_PATH%
	
	if errorlevel 1 (GOTO end)
	
:end
  echo Exiting..
  ::exit