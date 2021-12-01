package com.genesys.connect.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.genesys.connect.data.Connect5ResponseDto;
import com.genesys.connect.data.ConnectFive;
import com.genesys.connect.data.Player;

@RestController
public class Connect5Controller {

	static final int NO_OF_COLUMNS = 9;
	ConnectFive connectFive = new ConnectFive();

	/*
	 * This POST API allows a player to connect to the game if the max no of players
	 * has not been reached.
	 */
	@RequestMapping(value = "/addPlayer", method = RequestMethod.POST)
	public ResponseEntity<String> addPlayer(@RequestParam String name, HttpServletRequest request) {

		List<Player> players = connectFive.getPlayers();

		if (players.size() < 2) {

			if (request.getSession().getAttribute("player") == null) {
				Player newPlayer = connectFive.addPlayer(name);
				request.getSession().setAttribute("player", newPlayer);

				return new ResponseEntity<>("You have been added to the game " + name, HttpStatus.OK);
			}
		}

		return new ResponseEntity<>("Max players for this game", HttpStatus.SERVICE_UNAVAILABLE);

	}

	/*
	 * This GEt API returns the current game status, including the game board, the
	 * players, the current and next player, game start and win condition
	 */

	@RequestMapping(value = "/gameStatus", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Connect5ResponseDto> gameStatus(HttpServletRequest request) {

		ResponseEntity<Connect5ResponseDto> response;
		Object attribute = request.getSession().getAttribute("player");
		if (attribute instanceof Player) {
			Player player = (Player) attribute;
			Connect5ResponseDto connect5ResponseDto = connectFive.getGameStatus(player);
			response = ResponseEntity.ok(connect5ResponseDto);
		} else {
			response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return response;

	}

	/*
	 * This POST API allows the client to submit the current player token and the
	 * move position. The API updates the Game Board, if the column is not full and
	 * returns a success or a bad request status
	 */
	@RequestMapping(value = "/takeTurn", method = RequestMethod.POST)
	public ResponseEntity<String> takeTurn(@RequestParam String position, HttpServletRequest request) {

		Object attribute = request.getSession().getAttribute("player");
		if (attribute instanceof Player) {
			Player player = (Player) attribute;

			Integer pos = Integer.parseInt(position);
			boolean isMoveValid = connectFive.validateGameMove(pos - 1);

			if (isMoveValid) {
				connectFive.updateConnect5(player.getPlayerToken(), pos - 1);
				return new ResponseEntity<>(player.getPlayerName() + " has taken their turn", HttpStatus.OK);

			} else {
				return new ResponseEntity<>(player.getPlayerName() + ", this turn is not valid.",
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

	/* This GET API is invoked from the shutdown hook from the client */
	@RequestMapping(value = "/disconnectGame", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> disconnectGame(HttpServletRequest request) {

		Object attribute = request.getSession().getAttribute("player");
		if (attribute instanceof Player) {
			Player player = (Player) attribute;
		    return new ResponseEntity<>(player.getPlayerName() + " has disconnected and this game is now over.", HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

}
