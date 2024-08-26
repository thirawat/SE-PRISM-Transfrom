# Prism Convertor

Prism Convertor is a Java project developed as part of the master's project by Thirawat Sutalungka (ID: 6272041021). This tool is designed to facilitate the transformation of XML data from UPPAAL models into PTA (Probabilistic Timed Automata) models compatible with PRISM, enabling more efficient data processing and analysis.

## Features

- **UPPAAL to PTA PRISM Transformation:** Converts UPPAAL model data from XML format into PTA models compatible with PRISM.

## Demo

Check out the live demo of the Prism Convertor at [https://prism-transform.demosolution.app](https://prism-transform.demosolution.app).

To see an example file, you can find it in the `prism_convertor/xml/pta.xml` file.

## Prerequisites

- Java 8 or higher
- Maven 3.6 or higher

## Installation Steps

To install and run the Java project, follow these steps:

1. **Clone the repository to your local machine:**
    ```bash
    git clone https://github.com/thirawat/SE-PRISM-Transfrom.git
    ```

2. **Navigate to the project directory:**
    ```bash
    cd SE-PRISM-Transfrom/prism_convertor
    ```

3. **Build the JAR file using Maven:**
    ```bash
    mvn install -f "./pom.xml"
    mvn clean compile assembly:single
    ```

4. **Run the application:**
    ```bash
    java -jar target/prism_convertor.jar
    ```

## Usage

After running the application, you can transform XML files related to UPPAAL models into PTA PRISM models by specifying the input and output files as arguments:

```bash
java -jar target/prism_convertor.jar input.xml 
```