apt update
apt install maven

## Build Jar
mvn install -f "/app/docgen/pom.xml"
mvn clean compile assembly:single

## Run 
java -jar prism_convertor.jar 01_pta_3branch.xml