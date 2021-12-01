package com.genesys.connect.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import com.genesys.connect.data.Connect5ResponseDto;
import com.genesys.connect.data.Player;
import com.genesys.connect.util.PlayerToken;

public class Connect5ControllerTest {

	/* This tests if a player can be added successfully */
	@Test
	public void testAddPlayer() {

		Connect5Controller connect5Controller = new Connect5Controller();
		HttpSession session = new MockHttpSession();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(session);
		ResponseEntity<String> result = connect5Controller.addPlayer("neha", request);

		assertEquals(HttpStatus.OK, result.getStatusCode());
		ResponseEntity<Connect5ResponseDto> response = connect5Controller.gameStatus(request);
		assertEquals(1, response.getBody().getPlayers().size());
		assertEquals("neha", response.getBody().getPlayers().get(0).getPlayerName());
		assertEquals(PlayerToken.X, response.getBody().getPlayers().get(0).getPlayerToken());
	}

	/*
	 * This tests that game starts when 2 players are added 
	 */
	@Test
	public void testAdd2PlayersAndStartGame() {

		Connect5Controller connect5Controller = new Connect5Controller();
		HttpSession sessionOne = new MockHttpSession();
		MockHttpServletRequest requestOne = new MockHttpServletRequest();
		requestOne.setSession(sessionOne);
		ResponseEntity<String> resultOne = connect5Controller.addPlayer("neha", requestOne);
		assertEquals(HttpStatus.OK, resultOne.getStatusCode());

		HttpSession sessionTwo = new MockHttpSession();
		MockHttpServletRequest requestTwo = new MockHttpServletRequest();
		requestOne.setSession(sessionTwo);
		ResponseEntity<String> resultTwo = connect5Controller.addPlayer("robin", requestTwo);
		assertEquals(HttpStatus.OK, resultTwo.getStatusCode());

		ResponseEntity<Connect5ResponseDto> gameStatusDto = connect5Controller.gameStatus(requestTwo);

		assert (gameStatusDto.getBody().getPlayers().size() == 2);
		assertTrue(gameStatusDto.getBody().isGameStarted());
		assertTrue(!gameStatusDto.getBody().isMyTurn());

	}
	
	/*
	 * This tests that game starts when 2 players are added and if a 3rd player tries
	 * to join, they get a Service Unavailable Response
	 */
	@Test
	public void testAdd3PlayersAndCheckStatus() {

		Connect5Controller connect5Controller = new Connect5Controller();
		HttpSession sessionOne = new MockHttpSession();
		MockHttpServletRequest requestOne = new MockHttpServletRequest();
		requestOne.setSession(sessionOne);
		ResponseEntity<String> resultOne = connect5Controller.addPlayer("neha", requestOne);
		assertEquals(HttpStatus.OK, resultOne.getStatusCode());

		HttpSession sessionTwo = new MockHttpSession();
		MockHttpServletRequest requestTwo = new MockHttpServletRequest();
		requestOne.setSession(sessionTwo);
		ResponseEntity<String> resultTwo = connect5Controller.addPlayer("robin", requestTwo);
		assertEquals(HttpStatus.OK, resultTwo.getStatusCode());

		ResponseEntity<Connect5ResponseDto> gameStatusDto = connect5Controller.gameStatus(requestTwo);

		assert (gameStatusDto.getBody().getPlayers().size() == 2);
		assertTrue(gameStatusDto.getBody().isGameStarted());
		assertTrue(!gameStatusDto.getBody().isMyTurn());

		HttpSession sessionThree = new MockHttpSession();
		MockHttpServletRequest requestThree = new MockHttpServletRequest();
		requestOne.setSession(sessionThree);
		ResponseEntity<String> resultThree = connect5Controller.addPlayer("robin", requestThree);
		assertEquals(HttpStatus.SERVICE_UNAVAILABLE, resultThree.getStatusCode());

	}

	/*This tests the game status when two players have joined*/
	@Test
	public void testGameStatusStart() {

		Connect5Controller connect5Controller = new Connect5Controller();
		MockHttpServletRequest requestOne = new MockHttpServletRequest();
		ResponseEntity<String> resultOne = connect5Controller.addPlayer("neha", requestOne);
		assertEquals(HttpStatus.OK, resultOne.getStatusCode());

		MockHttpServletRequest requestTwo = new MockHttpServletRequest();
		ResponseEntity<String> resultTwo = connect5Controller.addPlayer("robin", requestTwo);
		assertEquals(HttpStatus.OK, resultTwo.getStatusCode());

		HttpSession sessionOne = new MockHttpSession();
		sessionOne.setAttribute("player", new Player("neha", PlayerToken.X));
		MockHttpServletRequest requestThree = new MockHttpServletRequest();
		requestThree.setSession(sessionOne);
		ResponseEntity<Connect5ResponseDto> gameStatusDto = connect5Controller.gameStatus(requestThree);
		Connect5ResponseDto body = gameStatusDto.getBody();
		assertTrue(body.isGameStarted());
		assertTrue(body.isMyTurn());
		assertFalse(body.isWinningConditionMet());
		assertFalse(body.isDrawConditionMet());
		assertEquals(2, body.getPlayers().size());
		assertEquals("neha", body.getCurrentPlayer().getPlayerName());
		assertEquals(PlayerToken.X, body.getCurrentPlayer().getPlayerToken());
		assertEquals("robin", body.getNextPlayer().getPlayerName());
		assertEquals(PlayerToken.O, body.getNextPlayer().getPlayerToken());
	}
	
	/*This tests the game status when game is in progress*/
	@Test
	public void testGameStatusInProgress() {

		Connect5Controller connect5Controller = new Connect5Controller();

		HttpSession sessionOne = new MockHttpSession();
		sessionOne.setAttribute("player", new Player("neha", PlayerToken.X));

		HttpSession sessionTwo = new MockHttpSession();
		sessionTwo.setAttribute("player", new Player("robin", PlayerToken.O));

		MockHttpServletRequest requestOne = new MockHttpServletRequest();
		ResponseEntity<String> resultOne = connect5Controller.addPlayer("neha", requestOne);
		assertEquals(HttpStatus.OK, resultOne.getStatusCode());

		MockHttpServletRequest requestTwo = new MockHttpServletRequest();
		ResponseEntity<String> resultTwo = connect5Controller.addPlayer("robin", requestTwo);
		assertEquals(HttpStatus.OK, resultTwo.getStatusCode());

		MockHttpServletRequest requestThree = new MockHttpServletRequest();
		requestThree.setSession(sessionOne);
		connect5Controller.takeTurn("1", requestThree);
		
		MockHttpServletRequest requestFour = new MockHttpServletRequest();
		requestFour.setSession(sessionTwo);
		connect5Controller.takeTurn("1", requestFour);
		
		ResponseEntity<Connect5ResponseDto> gameStatusDto = connect5Controller.gameStatus(requestFour);
		Connect5ResponseDto body = gameStatusDto.getBody();
		assertTrue(body.isGameStarted());
		assertFalse(body.isMyTurn());
		assertFalse(body.isWinningConditionMet());
		assertFalse(body.isDrawConditionMet());
		assertEquals(2, body.getPlayers().size());
		assertEquals("neha", body.getCurrentPlayer().getPlayerName());
		assertEquals(PlayerToken.X, body.getCurrentPlayer().getPlayerToken());
		assertEquals("robin", body.getNextPlayer().getPlayerName());
		assertEquals(PlayerToken.O, body.getNextPlayer().getPlayerToken());
		assertEquals(2, body.getConnect5Board().get(0).size());		
		
	}
	
	
	/*This tests the game status when game is in progress*/
	@Test
	public void testInvalidTurn() {
		
		HttpSession session = new MockHttpSession();
		session.setAttribute("player", new Player("neha", PlayerToken.X));
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setSession(session);

		Connect5Controller connect5Controller = new Connect5Controller();
		ResponseEntity<String> result = connect5Controller.takeTurn("-1", request);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
		}
	
	/*This tests when the client sends a message through shut down hook*/
	@Test
	public void testDisconnect() {
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("player", new Player("neha", PlayerToken.X));
		request.setSession(session);
		Connect5Controller connect5Controller = new Connect5Controller();
		ResponseEntity<String> result = connect5Controller.disconnectGame(request);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		}
	
	}
