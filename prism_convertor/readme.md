apt update
apt install maven

## Build Jar
mvn install -f "/app/docgen/pom.xml"
mvn clean compile assembly:single

java -jar docgen2.jar Loo_Retail_template.docx Test_result4.pdf looRetail.json