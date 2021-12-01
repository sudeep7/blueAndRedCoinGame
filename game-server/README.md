# About

1. This application holds the served side logic to implement the Connect5 game requirements provided by Genesys. 

2. Spring Boot has been used to write this as it provides a bundled Tomcat server and allows for easy setup and execution. 

3. The design logic for the board game has been constructed to use an Array List of Stacks. This decision has been made as it allows the data structure to take care of any tokens in the current column. This is present in the class com.genesys.connect.data.ConnectFive. 

4. The above class also holds the logic to add players to the game, take a turn, return the game status and check for winning and draw conditions. 

5. The REStful endpoints exposed are present in com.genesys.connect.controllers.Connect5Controller. APIs provided include functionality to add a player, get game status, take a turn and disconnect from the game. 

6. Junit and Mockito have been used for testing. The Jacoco plugin in Spring Boot allows for seeing test coverage and at the time of writing this, this stands at 94.7%. The tests can be executed using the command `mvn test`. 

7. To view the test coverage, run the command `mvn clean verify`. Then open the reports at location `Project Location>/target/site/jacoco/index.html`

# Build and Run

1. Check out the application Connect5 from the repo and naviagte to <file location>/Connect5 in the Console

2. Run the command `mvn spring-boot:run`.  

