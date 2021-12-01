package com.game.client.dto;

import java.util.List;
import java.util.Stack;

import com.game.client.util.PlayerToken;

public class Connect5ResponseDto {

	private List<Stack<PlayerToken>> connect5Board;
	private boolean winningConditionMet;
	private PlayerDto currentPlayer;
	private PlayerDto nextPlayer;
	private List<PlayerDto> players;
	private boolean myTurn;
	private boolean gameStarted;
	private boolean drawConditionMet;

	public Connect5ResponseDto() {

	}

	public Connect5ResponseDto(List<Stack<PlayerToken>> connect5Board, boolean winningConditionMet,
			PlayerDto currentPlayer, PlayerDto nextPlayer, List<PlayerDto> players, boolean myTurn, boolean gameStarted,
			boolean drawConditionMet) {
		super();
		this.connect5Board = connect5Board;
		this.winningConditionMet = winningConditionMet;
		this.currentPlayer = currentPlayer;
		this.nextPlayer = nextPlayer;
		this.players = players;
		this.myTurn = myTurn;
		this.gameStarted = gameStarted;
		this.drawConditionMet = drawConditionMet;
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

	public PlayerDto getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(PlayerDto currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public PlayerDto getNextPlayer() {
		return nextPlayer;
	}

	public void setNextPlayer(PlayerDto nextPlayer) {
		this.nextPlayer = nextPlayer;
	}

	public List<PlayerDto> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerDto> players) {
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
