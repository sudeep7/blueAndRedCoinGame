# About

1. This application holds the client side logic to implement the Connect5 game requirements provided by Genesys. 

2. This has been written as a java text based application with the main class as Connect5Client.java

3. The client makes a request to join the game, and based on response is added to the game. 

4. The game starts when 2 players have joined. The game will display a message to ask the client to wait till the 2 players have joined. 

5. The game will prompt the first joined player to start by taking a turn. If the turn is valid, it will be completed, and the player will be shown status of the board after taking their turn and before taking their turn. 

6. The game will continue until either a winning condition or draw condition has been met. 


# Build and Run

1. Check out the application Connect5 from the repo and naviagte to `file location`/Connect5Client in the Console

2. Run the command `mvn exec:java`.  

