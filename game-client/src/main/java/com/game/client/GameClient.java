package com.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.client.dto.Connect5ResponseDto;
import com.game.client.util.MessageConstants;
import com.game.client.util.PlayerToken;

public class GameClient {

	static Properties prop = new Properties();

	public static void main(String[] args) throws IOException, InterruptedException {

		final CloseableHttpClient httpClient = HttpClients.createDefault();
		loadConfidproperties();

		/* Building URLS */
		String addPlayerUrl = MessageConstants.HTTP.concat("://").concat(prop.getProperty("client.property.host"))
				.concat(":").concat(prop.getProperty("client.property.port")).concat("/")
				.concat(MessageConstants.ENDPOINT_ADD_PLAYER);

		String gameStatusUrl = MessageConstants.HTTP.concat("://").concat(prop.getProperty("client.property.host"))
				.concat(":")
				.concat(prop.getProperty("client.property.port").concat("/").concat(MessageConstants.GAME_STATUS));

		String takeTurnUrl = MessageConstants.HTTP.concat("://").concat(prop.getProperty("client.property.host"))
				.concat(":")
				.concat(prop.getProperty("client.property.port").concat("/").concat(MessageConstants.TAKE_TURN));

		/* Adding a shutdown hook if client loses connection */
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println(MessageConstants.GAME_OVER);
			}
		});

		System.out.println(MessageConstants.WELCOME_MESSAGE);
		System.out.println(MessageConstants.ENTER_NAME);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String name = reader.readLine();

		/* Adding a player to the game */
		HttpPost addPlayerRequest = new HttpPost(addPlayerUrl);

		List<NameValuePair> requestParams = new ArrayList<>();
		requestParams.add(new BasicNameValuePair("name", name));
		addPlayerRequest.setEntity(new UrlEncodedFormEntity(requestParams));

		try (CloseableHttpResponse response = httpClient.execute(addPlayerRequest)) {
			System.out.println(EntityUtils.toString(response.getEntity()));
		}

		/* Check and wait for 2 players to join before starting game */
		HttpGet getRequestGameStatus = new HttpGet(gameStatusUrl);

		try {

			Connect5ResponseDto responseDto = new Connect5ResponseDto();
			do {
				CloseableHttpResponse response = httpClient.execute(getRequestGameStatus);

				ObjectMapper mapper = new ObjectMapper();
				responseDto = mapper.readValue(response.getEntity().getContent(),
						new TypeReference<Connect5ResponseDto>() {
						});

				if (responseDto.getPlayers().size() == 2) {
					System.out.println(MessageConstants.START_PLAY);
				} else {
					Thread.sleep(5000);
					System.out.println(MessageConstants.WAIT_ON_PLAYER_TO_JOIN);
				}

			} while (!responseDto.isGameStarted());

			boolean isWinningConditionMet = false, isDrawConditionMet = false;

			/* Play game till there is a win or a draw */
			do {
				responseDto = getGameStatus(httpClient, gameStatusUrl);

				if (responseDto.isWinningConditionMet()) {
					displayConnect5Board(responseDto.getConnect5Board());
					break;
				}

				if (responseDto.isMyTurn()) {

					boolean isPositionValid = false;
					String position;
					boolean isTurnTaken = false;

					displayConnect5Board(responseDto.getConnect5Board());

					// get a valid user input
					do {
						System.out.println(
								responseDto.getCurrentPlayer().getPlayerName() + MessageConstants.ENTER_GAME_POSITION);
						position = reader.readLine();
						isPositionValid = validatePosition(position, isPositionValid, responseDto.getConnect5Board());

						// take the turn and ensure the user does not enter a token for an already full
						// column in board
						if (isPositionValid) {

							isTurnTaken = takeTurn(position, responseDto.getCurrentPlayer().getPlayerToken().toString(),
									httpClient, takeTurnUrl);
							if (isTurnTaken) {
								break;
							} else {
								isPositionValid = false;
								continue;
							}
						}

					} while (!isPositionValid);

					responseDto = getGameStatus(httpClient, gameStatusUrl);
					displayConnect5Board(responseDto.getConnect5Board());

					isWinningConditionMet = responseDto.isWinningConditionMet();

					isDrawConditionMet = responseDto.isDrawConditionMet();
				} else {
					Thread.sleep(5000);
					System.out.println(MessageConstants.PLEASE_WAIT + responseDto.getCurrentPlayer().getPlayerName()
							+ MessageConstants.TAKES_TURN);
				}
			} while (!isWinningConditionMet || !isDrawConditionMet);

			if (isDrawConditionMet) {
				System.out.println(MessageConstants.GAME_DRAW);
			} else {
				if (isWinningConditionMet && responseDto.isMyTurn()) {
					System.out.println(responseDto.getCurrentPlayer().getPlayerName() + MessageConstants.WINS_GAME);
				} else {
					System.out.println(responseDto.getNextPlayer().getPlayerName() + MessageConstants.WINS_GAME);
				}
			}

		} finally {
			System.out.println("Game over");
		}

	}

	/* This method loads the Properties file with server hostname and port */
	private static void loadConfidproperties() throws IOException {

		InputStream input = GameClient.class.getClassLoader().getResourceAsStream("config.properties");

		if (input == null) {
			System.out.println("Sorry, unable to find config.properties");
			return;
		}

		prop.load(input);

	}

	/* This method validates user input for game move position */
	private static boolean validatePosition(String position, boolean isPositionValid,
			List<Stack<PlayerToken>> connect5Board) {

		try {
			Integer pos = Integer.parseInt(position);
			if (pos >= 1 && pos <= 9 && connect5Board.get(pos - 1).size() < 6) {
				isPositionValid = true;
			}

		} catch (NumberFormatException ex) {
			isPositionValid = false;
		}
		return isPositionValid;
	}

	/* This method gets the game status */
	public static Connect5ResponseDto getGameStatus(CloseableHttpClient httpClient, String gameStatusUrl)
			throws IOException {

		HttpGet getRequestGameStatus = new HttpGet(gameStatusUrl);
		Connect5ResponseDto connect5ResponseDto;
		try (CloseableHttpResponse response = httpClient.execute(getRequestGameStatus)) {

			ObjectMapper mapper = new ObjectMapper();
			connect5ResponseDto = mapper.readValue(response.getEntity().getContent(),
					new TypeReference<Connect5ResponseDto>() {
					});

		}
		return connect5ResponseDto;
	}

	/* This method displays the current game board */
	public static void displayConnect5Board(List<Stack<PlayerToken>> board) {

		for (int j = 5; j >= 0; j--) {

			for (int i = 0; i < 9; i++) {

				if (board.get(i).size() > j)
					System.out.print("[" + board.get(i).get(j) + "]");
				else
					System.out.print("[ ]");
			}
			System.out.println();
		}

	}

	/* This method allows client to take a turn */
	public static boolean takeTurn(String position, String player, CloseableHttpClient httpClient, String takeTurnurl)
			throws IOException {

		HttpPost takeTurnRequest = new HttpPost(takeTurnurl);
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("position", position));

		takeTurnRequest.setEntity(new UrlEncodedFormEntity(params));

		try (CloseableHttpResponse response = httpClient.execute(takeTurnRequest)) {

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return true;
			} else {
				return false;
			}
		}

	}

}
