package com.game.client.dto;

public class PlayerDto {
	
	private String playerName;
	private Character playerToken;
	
	public PlayerDto() {
		
	}
	
	public PlayerDto(String playerName, Character playerToken) {
		this.playerName = playerName;
		this.playerToken = playerToken;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Character getPlayerToken() {
		return playerToken;
	}

	public void setPlayerToken(Character playerToken) {
		this.playerToken = playerToken;
	}

	
	

}
