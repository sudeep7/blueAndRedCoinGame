package com.genesys.connect.data;

import com.genesys.connect.util.PlayerToken;

/*This is a POJO to keep a track of players, play token and names*/
public class Player {

	private String playerName;
	private PlayerToken playerToken;

	public Player() {

	}

	public Player(String playerName, PlayerToken playerToken) {
		this.playerName = playerName;
		this.playerToken = playerToken;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public PlayerToken getPlayerToken() {
		return playerToken;
	}

	public void setPlayerToken(PlayerToken playerToken) {
		this.playerToken = playerToken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((playerToken == null) ? 0 : playerToken.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (playerName == null) {
			if (other.playerName != null)
				return false;
		} else if (!playerName.equals(other.playerName))
			return false;
		if (playerToken != other.playerToken)
			return false;
		return true;
	}

}
