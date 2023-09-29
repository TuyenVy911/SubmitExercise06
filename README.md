# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###
![image1](https://as1.ftcdn.net/v2/jpg/02/73/16/24/1000_F_273162497_ShAAB1TH0vhM4UUWbhBuao8jtGBDubwD.jpg)

* Quick summary: The project is an automation framework for web using java + selenium
* Version: 1.0

### :clapper: Scenarios ###

* Open ecommerce website say [https://www.powerbuy.co.th/th](https://www.powerbuy.co.th/th)

* Change language to EN

* Search by using the keyword “TV”

* Filter TV with Screen Size Group (inches) “44 – 55 inches” screen size and add one item to the cart from the return list

* Filter TV with “56 – 65 inches” screen size and add one item to the cart from the return list

* Check the cart on https://www.powerbuy.co.th/en/cart and validate results of items in the cart that match with the items that were added in the previous steps or not.

### How do I get set up? ###

* Summary of set up

```
Make sure you have installed all of the following prerequisites on your development machine:

* JDK 19 - [Download and install JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
* Maven - [Download & Install Maven](https://maven.apache.org/download.cgi)
* IntelliJ - [Download & Install IntelliJ Community](https://www.jetbrains.com/idea/download/#section=windows)
```

* Configuration
## :checkered_flag: Starting ##
 The file "setting.xml" located in maven/config package needs attention: Add your project path in ***localRepository*** tag

```
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <localRepository>/_(your project path)_/PowerBuyWebAndMobileTesting/maven/repo</localRepository>
  <interactiveMode>true</interactiveMode>
  <usePluginRegistry>false</usePluginRegistry>
  <offline>false</offline>

</settings>

```

* Dependencies
`pom.xml`

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>org.example</groupId>
  <artifactId>PowerBuyWebAndMobileTesting</artifactId>
  <version>1.0-SNAPSHOT</version>

  <properties>
    <maven.compiler.source>19</maven.compiler.source>
    <maven.compiler.target>19</maven.compiler.target>
    <log4j.version>1.2.17</log4j.version>
    <slf4j.verion>2.0.5</slf4j.verion>
    <webdrivermanager.version>5.5.3</webdrivermanager.version>
    <selenium.version>4.13.0</selenium.version>
    <testng.version>7.8.0</testng.version>
    <allure-testng.version>2.23.0</allure-testng.version>
    <aspectj.version>1.9.6</aspectj.version>
    <json-path.version>2.8.0</json-path.version>
    <cucumber-java.version>7.14.0</cucumber-java.version>
    <cucumber-testng.version>7.14.0</cucumber-testng.version>
    <allure.cucumber7.jvm.version>2.24.0</allure.cucumber7.jvm.version>
    <cucumber-picocontainer.version>7.14.0</cucumber-picocontainer.version>
    <cucumber.gherkin.version>26.2.0</cucumber.gherkin.version>
    <maven-compiler-plugin.version>3.11.0</maven-compiler-plugin.version>
    <maven-surefire-plugin-version>3.0.0-M5</maven-surefire-plugin-version>
    <commons-lang3-version>3.12.0</commons-lang3-version>
    <apache-poi-version>5.2.3</apache-poi-version>
    <apache-poi-ooxml-version>5.2.3</apache-poi-ooxml-version>
    <log4j-to-slf4j-version>2.8.2</log4j-to-slf4j-version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>${log4j.version}</version>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>${slf4j.verion}</version>
    </dependency>

    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-reload4j</artifactId>
      <version>${slf4j.verion}</version>
    </dependency>

    <dependency>
      <groupId>org.seleniumhq.selenium</groupId>
      <artifactId>selenium-java</artifactId>
      <version>${selenium.version}</version>
    </dependency>

    <dependency>
      <groupId>org.testng</groupId>
      <artifactId>testng</artifactId>
      <version>${testng.version}</version>
      <scope>compile</scope>
    </dependency>

    <dependency>
      <groupId>io.github.bonigarcia</groupId>
      <artifactId>webdrivermanager</artifactId>
      <version>${webdrivermanager.version}</version>
    </dependency>

    <dependency>
      <groupId>io.qameta.allure</groupId>
      <artifactId>allure-testng</artifactId>
      <version>${allure-testng.version}</version>
    </dependency>

    <dependency>
      <groupId>com.jayway.jsonpath</groupId>
      <artifactId>json-path</artifactId>
      <version>${json-path.version}</version>
    </dependency>

    <dependency>
      <groupId>io.cucumber</groupId>
      <artifactId>cucumber-java</artifactId>
      <version>${cucumber-java.version}</version>
    </dependency>

    <dependency>
      <groupId>io.cucumber</groupId>
      <artifactId>cucumber-testng</artifactId>
      <version>${cucumber-testng.version}</version>
    </dependency>

    <dependency>
      <groupId>io.cucumber</groupId>
      <artifactId>cucumber-picocontainer</artifactId>
      <version>${cucumber-picocontainer.version}</version>
    </dependency>

    <dependency>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>${maven-compiler-plugin.version}</version>
    </dependency>

    <dependency>
      <groupId>io.qameta.allure</groupId>
      <artifactId>allure-cucumber7-jvm</artifactId>
      <version>${allure.cucumber7.jvm.version}</version>
    </dependency>

    <dependency>
      <groupId>io.cucumber</groupId>
      <artifactId>gherkin</artifactId>
      <version>${cucumber.gherkin.version}</version>
    </dependency>

    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-lang3</artifactId>
      <version>${commons-lang3-version}</version>
    </dependency>

    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi</artifactId>
      <version>${apache-poi-version}</version>
    </dependency>

    <dependency>
      <groupId>org.apache.poi</groupId>
      <artifactId>poi-ooxml</artifactId>
      <version>${apache-poi-ooxml-version}</version>
    </dependency>

    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-to-slf4j</artifactId>
      <version>${log4j-to-slf4j-version}</version>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <configuration>
          <source>11</source> <!--For JAVA 8 use 1.8-->
          <target>11</target> <!--For JAVA 8 use 1.8-->
        </configuration>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>${maven-surefire-plugin-version}</version>
        <configuration>
          <suiteXmlFiles>
            <suiteXmlFile>${project.basedir}/src/main/resources/testsuites/${testsuite}.xml</suiteXmlFile>
          </suiteXmlFiles>
          <argLine>
            -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
            -Dcucumber.options="--plugin io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
          </argLine>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>${aspectj.version}</version>
          </dependency>
        </dependencies>
      </plugin>
    </plugins>
  </build>

</project>
```

### How to run tests ###
There are a couple of ways to execute the code

* Using TestNG there is 2 way: 
 To run test project, simply right-click on ItemsInCart.xml and select "Run"

 Typing this line to terminal `mvn --settings ./maven/config/settings.xml test -Dtestsuite=ItemsInCart`

  Please ensure that you add the required code to the pom.xml file

```
<suiteXmlFiles>
      <suiteXmlFile>${project.basedir}/src/main/resources/testsuites/${testsuite}.xml</suiteXmlFile>
</suiteXmlFiles>
```
* Using Cucumber framework: right-click on TestNGRunner.java and select "Run"

### Deployment instructions ###

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### :thinking: :e-mail: Who do I talk to? ###

* Kim Tuyen [kimtuyentoothpickee@gmail.com](kimtuyentoothpickee@gmail.com)
* Luu Minh Tuyen (Mentor)
