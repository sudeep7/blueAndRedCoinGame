package com.genesys.connect.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.genesys.connect.util.PlayerToken;

/*This class holds the state of the game and business logic to play the game.
 *  The data structure used for this implementation is an array list of stacks, 
 *  thus eliminating the need to remember the size of each column on the board*/
public class ConnectFive {

	List<Stack<PlayerToken>> connect5Board = new ArrayList<Stack<PlayerToken>>();

	List<Player> players = new ArrayList<Player>();

	Player currentPlayer;
	Player nextPlayer;
	 int lastMove = 0;
	 int turnCounter = 0;
	 final int noOfColumns = 9;
	 final int noOfRows = 6;

	public ConnectFive() {
		for (int i = 0; i < noOfColumns; i++) {
			connect5Board.add(new Stack<PlayerToken>());
		}
	}



	/*
	 * This method updates the status of the game to add players and assign them a
	 * player token
	 */
	public Player addPlayer(String player) {

		Player newPlayer = new Player();
		newPlayer.setPlayerName(player);
		if (players.size() == 0) {
			newPlayer.setPlayerToken(PlayerToken.X);
			setCurrentPlayer(newPlayer);
		} else {
			newPlayer.setPlayerToken(PlayerToken.O);
			setNextPlayer(newPlayer);

		}
		players.add(newPlayer);
		return newPlayer;

	}

	/*
	 * This method updates the board game and swaps the player to return the current
	 * player
	 */
	public void updateConnect5(PlayerToken playerToken, int column) {

		lastMove = column;

		connect5Board.get(column).push(playerToken);
		turnCounter++;

		for (Player player : players) {

			if (player.getPlayerToken().equals(playerToken)) {
				currentPlayer = player;
			} else {
				nextPlayer = player;
			}

		}

		Player temp = currentPlayer;
		currentPlayer = nextPlayer;
		nextPlayer = temp;

	}
	
	public boolean validateGameMove(int position) {
		
		if(position >= 0 && position < noOfColumns && 	(connect5Board.get(position).size() < noOfRows))
			return true;
			
		return false;
		
	}
	
	/*This method returns the current status of the game*/
	
	public Connect5ResponseDto getGameStatus(Player player) {
		
		Connect5ResponseDto connect5ResponseDto = new Connect5ResponseDto();

		if (player.equals(getCurrentPlayer())) {
			connect5ResponseDto.setMyTurn(true);
		} else {
			connect5ResponseDto.setMyTurn(false);
		}
		connect5ResponseDto.setConnect5Board(getConnect5Board());

		if (getLastMove() != 0) {
			connect5ResponseDto.setWinningConditionMet(isWinningConditionMet(getLastMove()));
		} else {
			connect5ResponseDto.setWinningConditionMet(false);
		}
		if (getPlayers().size() == 2) {
			connect5ResponseDto.setGameStarted(true);
		} else {
			connect5ResponseDto.setGameStarted(false);
		}
		connect5ResponseDto.setCurrentPlayer(getCurrentPlayer());
		connect5ResponseDto.setNextPlayer(getNextPlayer());
		connect5ResponseDto.setPlayers(getPlayers());
		
		return connect5ResponseDto;
	}

	/*
	 * This method returns if winning condition is met, if either a row, column or
	 * diagonal match with 5 consecutive player tokens is found
	 */
	public boolean isWinningConditionMet(int lastMove) {

		return (checkConnect5Row(lastMove) || checkConnect5Column(lastMove) || checkConnect5ForwardDiagonal(lastMove)
				|| checkConnect5BackwardDiagonal(lastMove));

	}

	/*
	 * This method keeps a tab of the maximum number of turns in a game and if a
	 * draw has been reached.
	 */
	public boolean isDrawConditionMet() {

		if (turnCounter == (noOfColumns * noOfRows))
			return true;

		return false;
	}

	/*
	 * This method takes the last play column position as an input and checks if 5
	 * tokens in a row are met, using the current position as base and checking on
	 * either side.
	 */
	public boolean checkConnect5Row(int lastMove) {

		int tokencounter = 1;
		boolean isWinConditionMet = false;
		int row = connect5Board.get(lastMove).size() - 1;
		PlayerToken currentPlayer = connect5Board.get(lastMove).peek();

		for (int i = lastMove - 1; i >= 0; i--) {
			Stack<PlayerToken> columnleft = connect5Board.get(i);

			if (columnleft.size() > row && columnleft.get(row).equals(currentPlayer)) {
				tokencounter++;
			} else {
				break;
			}
		}

		for (int i = lastMove + 1; i < noOfColumns; i++) {
			Stack<PlayerToken> columnRight = connect5Board.get(i);

			if (columnRight.size() > row && columnRight.get(row).equals(currentPlayer)) {
				tokencounter++;
			} else {
				break;
			}
		}

		if (tokencounter > 4) {
			isWinConditionMet = true;
		}
		return isWinConditionMet;

	}

	/*
	 * This method takes the last play column position as an input and checks if 5
	 * tokens in a column are met, using the current position as base and checking
	 * below itself.
	 */
	public boolean checkConnect5Column(int lastMove) {

		boolean isWinConditionMet = false;
		PlayerToken currentPlayer = connect5Board.get(lastMove).peek();
		int row = connect5Board.get(lastMove).size() - 1;
		int tokencounter = 1;

		for (int i = row - 1; i >= 0; i--) {

			if (connect5Board.get(lastMove).get(i).equals(currentPlayer)) {
				tokencounter++;
			} else {
				break;
			}
		}

		if (tokencounter > 4) {
			isWinConditionMet = true;
		}
		return isWinConditionMet;

	}

	/*
	 * This method takes the last play column position as an input and checks if 5
	 * tokens in a forward(\)diagonal are met, using the current position as base
	 * and checking on either side.
	 */

	public boolean checkConnect5ForwardDiagonal(int lastMove) {

		boolean isWinConditionMet = false;
		PlayerToken currentPlayer = connect5Board.get(lastMove).peek();
		int currentRow = connect5Board.get(lastMove).size() - 1;
		int currentColumn = lastMove - 1;
		int tokencounter = 1;

		for (int i = currentRow - 1; i >= 0 && currentColumn >= 0; i--) {

			Stack<PlayerToken> columnleft = connect5Board.get(currentColumn);

			if (columnleft.size() > i && columnleft.get(i).equals(currentPlayer)) {
				tokencounter = tokencounter + 1;
			}

			currentColumn = currentColumn - 1;

		}

		for (int i = currentRow + 1; i < noOfRows; i++) {

			Stack<PlayerToken> columnRight = connect5Board.get(i);

			if (columnRight.size() > i && columnRight.get(i).equals(currentPlayer)) {
				tokencounter = tokencounter + 1;
			}

			currentColumn = currentColumn + 1;

		}

		if (tokencounter > 4) {
			isWinConditionMet = true;
		}

		return isWinConditionMet;
	}

	/*
	 * This method takes the last play column position as an input and checks if 5
	 * tokens in a backward(/)diagonal are met, using the current position as base
	 * and checking on either side.
	 */

	public boolean checkConnect5BackwardDiagonal(int lastMove) {

		boolean isWinConditionMet = false;
		PlayerToken currentPlayer = connect5Board.get(lastMove).peek();
		int currentRow = connect5Board.get(lastMove).size() - 1;
		int currentColumn = lastMove - 1;

		int tokencounter = 1;

		for (int i = currentRow + 1; i < noOfRows && currentColumn >= 0; i++) {

			Stack<PlayerToken> columnleft = connect5Board.get(currentColumn);

			if (columnleft.size() > i && columnleft.get(i).equals(currentPlayer)) {
				tokencounter = tokencounter + 1;
			} else {
				break;
			}

			currentColumn = currentColumn - 1;

		}

		currentColumn = lastMove + 1;
		for (int i = currentRow - 1; i >= 0 && currentColumn < noOfColumns; i--) {

			Stack<PlayerToken> columnRight = connect5Board.get(currentColumn);

			if (columnRight.size() > i && columnRight.get(i).equals(currentPlayer)) {
				tokencounter = tokencounter + 1;
			} else {
				break;
			}

			currentColumn = currentColumn + 1;

		}

		if (tokencounter > 4) {
			isWinConditionMet = true;
		}

		return isWinConditionMet;
	}

	public int getLastMove() {
		return lastMove;
	}

	public List<Player> getPlayers() {
		return players;
	}


	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public List<Stack<PlayerToken>> getConnect5Board() {
		return connect5Board;
	}

	public void setConnect5Board(List<Stack<PlayerToken>> connect5Board) {
		this.connect5Board = connect5Board;
	}

	public  int getNoofcolumns() {
		return noOfColumns;
	}

	public  int getNoofrows() {
		return noOfRows;
	}
	

}
