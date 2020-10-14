# Microservices with Spring Boot and Spring Cloud


## Ports

|     Application       |     Port          |
| ------------- | ------------- |
| Limits Service | 8080, 8081, ... |
| Spring Cloud Config Server | 8888 |
|  |  |
| Currency Exchange Service | 8000, 8001, 8002, ..  |
| Currency Conversion Service | 8100, 8101, 8102, ... |
| Netflix Eureka Naming Server | 8761 |
| Netflix Zuul API Gateway Server | 8765 |
| Zipkin Distributed Tracing Server | 9411 |


## Order in which you can start all apps:

1- Start Spring cloud config server

2- Start Netflix Eureka naming server

3- Start zipkin server (Use '.\startZipkin.bat' or './startZipkin.sh' to start zipkin server. Once it is started, check the zipkin dashboard here: http://localhost:9411/zipkin)

4- Make RabbitMQ is running as well and zipkin is connected to the rabbit mq. See '.\startZipkin.bat' or './startZipkin.sh' for details on commands.
 
5- Start Netflix zuul api gateway server

6- Start Limits service

7- Start Currency exchange service

8- Start Currency conversion service  
   

## URLs

|     Application       |     URL          |
| ------------- | ------------- |
| Limits Service | http://localhost:8080/limits http://localhost:8080/actuator/refresh  (POST)|
|Spring Cloud Config Server| http://localhost:8888/limits-service/default http://localhost:8888/limits-service/dev |
|  Currency Converter Service - Feign| http://localhost:8100/currency-converter/from/EUR/to/INR/amount/10000|
| Currency Exchange Service | http://localhost:8000/currency-exchange/from/EUR/to/INR http://localhost:8001/currency-exchange/from/USD/to/INR|
| Eureka | http://localhost:8761/|
| Zuul - Currency Exchange & Exchange Services | http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/INR http://localhost:8765/currency-conversion-service/currency-converter/from/USD/to/INR/amount/10|
| Zipkin | http://localhost:9411/zipkin/ |
| Spring Cloud Bus Refresh | http://localhost:8080/actuator/bus-refresh (POST)|


## Install RabbitMQ

```
https://www.rabbitmq.com/install-windows.html
```

#### Download and install Erlang & RabbitMQ for windows:
```

Download and install Erlang (RabbitMQ dependency, it needs to be installed before RabbitMQ installation):
http://erlang.org/download/otp_win64_23.1.exe


Download and install after Erlang is installed:
https://github.com/rabbitmq/rabbitmq-server/releases/download/v3.8.9/rabbitmq-server-3.8.9.exe

```

## Refresh the configs using actuator

```
For one instance running on port 8080 refresh the config:

curl -X POST localhost:8080/actuator/refresh

For all instances running on any ports, refresh the config:

curl -X POST localhost:8080/actuator/bus-refresh
```

## Zipkin Installation

Quick Start Page
- https://zipkin.io/pages/quickstart

Downloading Zipkin Jar
- https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec

Command to run (also see startZipkin.bat/startZipkin.sh)
```
set RABBIT_URI=amqp://localhost
java -jar zipkin-server-2.21.7-exec.jar 
```

## How to set VM Argument?

-Dserver.port=8001

## Spring Cloud Configuration

```
spring.cloud.config.failFast=true

```

## More about Microservices
- Design and Governance of Microservices
    - https://martinfowler.com/microservices/
- 12 Factor App 
    - https://12factor.net/
    - https://dzone.com/articles/the-12-factor-app-a-java-developers-perspective
- Spring Cloud
    - http://projects.spring.io/spring-cloud/
