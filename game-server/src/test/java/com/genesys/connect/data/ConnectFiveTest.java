package com.genesys.connect.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.genesys.connect.util.PlayerToken;

public class ConnectFiveTest {

	/*
	 * This tests the validation for valid moves on server
	 */
	@Test
	public void testValidMoves() {

		ConnectFive connectFive = new ConnectFive();

		assertFalse(connectFive.validateGameMove(-1));
		final int columns = connectFive.getNoofcolumns();
		for (int i = 0; i < columns; i++) {
			assertTrue(connectFive.validateGameMove(i));
		}

	}

	/*
	 * This tests the validation for invalid moves on server
	 */
	@Test
	public void testInValidMoves() {

		ConnectFive connectFive = new ConnectFive();

		assertFalse(connectFive.validateGameMove(-1));
		assertFalse(connectFive.validateGameMove(-10));
		assertFalse(connectFive.validateGameMove(-100));

	}

	/*
	 * This tests win condition for row win
	 */
	@Test
	public void testRowWinOne() {

		ConnectFive connectFive = new ConnectFive();

		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 1);
		connectFive.updateConnect5(PlayerToken.X, 2);
		connectFive.updateConnect5(PlayerToken.X, 3);
		connectFive.updateConnect5(PlayerToken.X, 4);
		assertTrue(connectFive.isWinningConditionMet(4));
		assertFalse(connectFive.isDrawConditionMet());

	}

	/*
	 * This tests win condition for row win involving checking on either side from
	 * last
	 */
	@Test
	public void testRowWinTwo() {

		ConnectFive connectFive = new ConnectFive();

		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 1);
		connectFive.updateConnect5(PlayerToken.X, 3);
		connectFive.updateConnect5(PlayerToken.X, 4);
		connectFive.updateConnect5(PlayerToken.X, 2);
		assertTrue(connectFive.isWinningConditionMet(2));
		assertFalse(connectFive.isDrawConditionMet());

	}

	/*
	 * This tests win condition for column win
	 */
	@Test
	public void testColumnWinOne() {

		ConnectFive connectFive = new ConnectFive();

		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		assertTrue(connectFive.isWinningConditionMet(0));
		assertFalse(connectFive.isDrawConditionMet());

	}

	/*
	 * This tests win condition for column win involving all 6 rows
	 */
	@Test
	public void testColumnWinTwo() {

		ConnectFive connectFive = new ConnectFive();
		connectFive.updateConnect5(PlayerToken.O, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		assertTrue(connectFive.isWinningConditionMet(0));
		assertFalse(connectFive.isDrawConditionMet());

	}

	/*
	 * This tests win condition for column win
	 */
	@Test
	public void testColumnForwardDiagonalWin() {

		ConnectFive connectFive = new ConnectFive();

		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.O, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);
		connectFive.updateConnect5(PlayerToken.X, 0);

		connectFive.updateConnect5(PlayerToken.X, 2);
		connectFive.updateConnect5(PlayerToken.O, 2);
		connectFive.updateConnect5(PlayerToken.X, 2);
		connectFive.updateConnect5(PlayerToken.O, 2);
		assertFalse(connectFive.isWinningConditionMet(2));
		assertFalse(connectFive.isDrawConditionMet());

		connectFive.updateConnect5(PlayerToken.O, 3);
		connectFive.updateConnect5(PlayerToken.X, 3);
		connectFive.updateConnect5(PlayerToken.O, 3);
		assertFalse(connectFive.isWinningConditionMet(3));
		assertFalse(connectFive.isDrawConditionMet());

		connectFive.updateConnect5(PlayerToken.X, 4);
		assertFalse(connectFive.isWinningConditionMet(4));
		assertFalse(connectFive.isDrawConditionMet());

		connectFive.updateConnect5(PlayerToken.O, 1);
		connectFive.updateConnect5(PlayerToken.X, 1);
		connectFive.updateConnect5(PlayerToken.O, 1);
		connectFive.updateConnect5(PlayerToken.X, 1);
		assertTrue(connectFive.isWinningConditionMet(1));
		assertFalse(connectFive.isDrawConditionMet());

	}

	/*
	 * This tests win condition for column win
	 */
	@Test
	public void testColumnBackwardDiagonalWin() {

		ConnectFive connectFive = new ConnectFive();

		connectFive.updateConnect5(PlayerToken.X, 4);
		assertFalse(connectFive.isWinningConditionMet(4));
		assertFalse(connectFive.isDrawConditionMet());

		connectFive.updateConnect5(PlayerToken.O, 5);
		connectFive.updateConnect5(PlayerToken.X, 5);
		connectFive.updateConnect5(PlayerToken.O, 5);
		connectFive.updateConnect5(PlayerToken.X, 5);
		assertFalse(connectFive.isWinningConditionMet(5));
		assertFalse(connectFive.isDrawConditionMet());

		connectFive.updateConnect5(PlayerToken.X, 6);
		connectFive.updateConnect5(PlayerToken.O, 6);
		connectFive.updateConnect5(PlayerToken.X, 6);
		connectFive.updateConnect5(PlayerToken.O, 6);
		connectFive.updateConnect5(PlayerToken.X, 6);
		assertFalse(connectFive.isWinningConditionMet(6));
		assertFalse(connectFive.isDrawConditionMet());

		connectFive.updateConnect5(PlayerToken.O, 7);
		connectFive.updateConnect5(PlayerToken.O, 7);
		connectFive.updateConnect5(PlayerToken.X, 7);
		connectFive.updateConnect5(PlayerToken.X, 7);
		assertFalse(connectFive.isWinningConditionMet(7));
		assertFalse(connectFive.isDrawConditionMet());

		connectFive.updateConnect5(PlayerToken.X, 8);
		connectFive.updateConnect5(PlayerToken.O, 8);
		connectFive.updateConnect5(PlayerToken.X, 8);
		connectFive.updateConnect5(PlayerToken.O, 8);
		connectFive.updateConnect5(PlayerToken.X, 8);
		assertTrue(connectFive.isWinningConditionMet(8));
		assertFalse(connectFive.isDrawConditionMet());

	}
	/*
	 * This tests draw condition by using a pattern as below XOXXOXXOX XOXXOXXOX
	 * OXOOXOOXO OXOOXOOXO XOOXOOXOO XOXXOXXOX
	 */

	@Test
	public void testDrawCondition() {

		ConnectFive connectFive = new ConnectFive();

		for (int i = 0; i < 9; i++) {
			if (i % 2 == 0) {
				connectFive.updateConnect5(PlayerToken.X, i);
				connectFive.updateConnect5(PlayerToken.X, i);
				connectFive.updateConnect5(PlayerToken.O, i);
				connectFive.updateConnect5(PlayerToken.O, i);
				connectFive.updateConnect5(PlayerToken.X, i);
				connectFive.updateConnect5(PlayerToken.X, i);

			} else {
				connectFive.updateConnect5(PlayerToken.O, i);
				connectFive.updateConnect5(PlayerToken.O, i);
				connectFive.updateConnect5(PlayerToken.X, i);
				connectFive.updateConnect5(PlayerToken.X, i);
				connectFive.updateConnect5(PlayerToken.O, i);
				connectFive.updateConnect5(PlayerToken.O, i);
			}
		}

		assertTrue(connectFive.isDrawConditionMet());

	}

	/* This tests valid game move positions */
	@Test
	public void testValidMovePositions() {

		ConnectFive connectFive = new ConnectFive();
		assertTrue(connectFive.validateGameMove(0));
		assertTrue(connectFive.validateGameMove(1));
		assertTrue(connectFive.validateGameMove(2));
		assertTrue(connectFive.validateGameMove(3));
		assertTrue(connectFive.validateGameMove(4));
		assertTrue(connectFive.validateGameMove(5));
		assertTrue(connectFive.validateGameMove(6));
		assertTrue(connectFive.validateGameMove(7));
		assertTrue(connectFive.validateGameMove(8));

	}

	/* This tests invalid game move positions */
	@Test
	public void testInvalidMovePositions() {

		ConnectFive connectFive = new ConnectFive();
		assertFalse(connectFive.validateGameMove(9));
		assertFalse(connectFive.validateGameMove(-1));
		assertFalse(connectFive.validateGameMove(20));
		assertFalse(connectFive.validateGameMove(30));
		assertFalse(connectFive.validateGameMove(44));
		assertFalse(connectFive.validateGameMove(54));
		assertFalse(connectFive.validateGameMove(66));
		assertFalse(connectFive.validateGameMove(77));
		assertFalse(connectFive.validateGameMove(88));

	}

}
