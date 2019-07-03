# Akka-Kafka-producer
Simple application that provides a kafka producer using an Akka http server

## Build the app 
- Clone the repository
- With the sbt shell, run assembly to compile and package the app 

## Run 
- Set your own parameters in the config.yml file 
- launch the server with a jvm : `java -jar package_name.jar config.yml`
- send a message to the server :  
`curl -H "Content-Type: application/json" -X POST -d 'test message' http://localhost:8080/sendMessage`
