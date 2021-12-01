package com.genesys.connect.util;

public enum PlayerToken {

	X('X'), O('O');

	public char asChar() {
		return asChar;
	}

	private final char asChar;

	PlayerToken(char asChar) {
		this.asChar = asChar;
	}

}
