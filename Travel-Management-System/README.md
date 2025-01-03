# TravelWebApplication
This project is to develop a travel planner system, which describes about the climatic conditions, interesting places to visit, top hotels in the area and some additional features like travel reminders depending on the user’s destination. We have used Angular JS, HTML 5 for front end and Spring boot, MySQL for backend.


Installation Guide:

******* SOFTWARE NEEDS TO BE INSTALLED **********

1. Install Spring STS IDE from https://spring.io/tools/sts/all
   NOTE: If you are using Eclipse IDE install Spring Boot jars
2. Install Java 1.7 (JDK or JRE)
3. Install Apache Maven in your system (Version Used: 3.3.3)
4. Install MySql Server Workbench database(Version Used: 6.3)

******* SET UP INSTRUCTION **********

1. Open MySql Server 
   Run the script file as "Open Sql Script"

2. Open Spring STS
   Download the source file to your desktop
   Import travelApp src folder as Existing Maven Project 

3. Open application.properties file
   change DataBase Name, UserName and Password of your database

4. Two API's were used
   a) FourSquare API
   Get Client key and client secret key from https://developer.foursquare.com/docs/
   b) DarkSky API
   Get Client Key from https://darksky.net/dev/docs

5. Open BaseFactory.js
   Assign clientId and clientSecret variable with the keys obtained from FourSquare
   Assign weatherKey variable with key obtained from DarkSky

6. Build your Project as File--> MavenBuild so that it can download all the jars.

7. Run the Project as Run As --> Spring Boot App

8. *** You are Good to go.. Play with our Application ******
