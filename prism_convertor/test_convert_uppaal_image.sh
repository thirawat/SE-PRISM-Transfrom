cd ./uppaal_core 
# ./uppaal --export xml/pta.xml output.pdf
java -jar uppaal.jar -t 1 -o output.pdf ../xml/pta.xml
cd ..