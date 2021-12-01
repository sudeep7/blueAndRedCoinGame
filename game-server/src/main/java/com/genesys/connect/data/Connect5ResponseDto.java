package com.genesys.connect.data;

import java.util.List;
import java.util.Stack;

import com.genesys.connect.util.PlayerToken;

public class Connect5ResponseDto {

	private List<Stack<PlayerToken>> connect5Board;
	private boolean winningConditionMet;
	private Player currentPlayer;
	private Player nextPlayer;
	private List<Player> players;
	private boolean myTurn;
	private boolean gameStarted;
	private boolean drawConditionMet;

	public Connect5ResponseDto() {

	}

	public List<Stack<PlayerToken>> getConnect5Board() {
		return connect5Board;
	}

	public void setConnect5Board(List<Stack<PlayerToken>> connect5Board) {
		this.connect5Board = connect5Board;
	}

	public boolean isWinningConditionMet() {
		return winningConditionMet;
	}

	public void setWinningConditionMet(boolean winningConditionMet) {
		this.winningConditionMet = winningConditionMet;
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

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public boolean isDrawConditionMet() {
		return drawConditionMet;
	}

	public void setDrawConditionMet(boolean drawConditionMet) {
		this.drawConditionMet = drawConditionMet;
	}

}
