package application;

import model.Board;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;

public class Game {
	public static final int MULTIPLAYER_GAME_MODE = 1;
	public static final int SINGLEPLAYER_GAME_MODE = 2;
	public static final int NETWORK_GAME_MODE = 3;
	public static final int AI_VERSUS_AI_GAME_MODE = 9;

	public static final int NOVICE_DIFFICULTY = 1;
	public static final int INTERMEDIATE_DIFFICULTY = 2;
	public static final int ADVANCED_DIFFICULTY = 3;
	public static final int ULTIMATE_DIFFICULTY = 4;

	private int mode;
	private static Game instance = null;
	private IPlayer player2;
	private IPlayer player1;

	private static Board board;
	private IPlayer activePlayer;

	private Game() {
		board = new Board();
		// initialize as multiplayer by default
		initMultiplayer();
	}

	/**
	 * Initialize the game to a local multiplayer game with two Players
	 */
	public void initMultiplayer(){
		this.mode = MULTIPLAYER_GAME_MODE;
		board = new Board();
		player1 = new Player();
		player2 = new Player();
		activePlayer = player1;
	}

	/**
	 * Initialize the game to single-player of difficulty <code>difficulty</code>
	 * @param difficulty
	 * 		The difficulty of the game
	 * @param playerFirst
	 */
	public void initSinglePlayer(int difficulty, boolean playerFirst){
		this.mode = SINGLEPLAYER_GAME_MODE;
		board = new Board();
		player1 = playerFirst ? new Player() : new AI(difficulty, board, !playerFirst);
		player2 = playerFirst ? new AI(difficulty, board, !playerFirst) : new Player();
		activePlayer = player1;
	}

	public void initNetwork(IPlayer player1, IPlayer player2) {
		this.mode = NETWORK_GAME_MODE;
		board = new Board();
		this.player1 = player1;
		this.player2 = player2;
		activePlayer = player1;
	}

	public void initAiVAi(int Ai1Difficulty, int Ai2Difficulty) {
		this.mode = AI_VERSUS_AI_GAME_MODE;
		board = new Board();
		player1 = new AI (Ai1Difficulty, board, true);
		player2 = new AI (Ai2Difficulty, board, false);
		activePlayer = player1;
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	public void withdraw() throws WithdrawException, InvalidIndexException{
		board.withdrawMove(getActivePlayer().getLastMove());
		board.withdrawMove(getInactivePlayer().getLastMove());
		getActivePlayer().withdraw();
		getInactivePlayer().forceWithdraw();
	}

	public int getMode() {
		return mode;
	}

	/**
	 * Prompts the player to make their next move.
	 * Toggles the active player at the end of their successful turn.
	 *
	 * @return
	 * 		state of if there's a valid next move to make
	 * @throws InvalidIndexException
	 * 		thrown if the move chosen is invalid
	 */
	public void makeMove() throws InvalidIndexException {
		if (!board.updateBoard(activePlayer.makeMove(), isPlayer1Active()))
			throw new InvalidIndexException("The index you entered is not valid!");
		toggleActivePlayer();

	}

	public Board getBoard(){
		return board;
	}

	public IPlayer getPlayer1() {
		return player1;
	}

	public IPlayer getPlayer2() {
		return player2;
	}

	public IPlayer getActivePlayer() {
		return activePlayer;
	}

	public IPlayer getInactivePlayer(){
		if (isPlayer1Active()){
			return player2;
		} else return player1;
	}

	public boolean isPlayer1Active(){
		return activePlayer.equals(player1);
	}

	public void toggleActivePlayer(){
		if (activePlayer.equals(player1)){
			activePlayer = player2;
		} else {
			activePlayer = player1;
		}
	}

	public static boolean isWinning() {
		return board.checkrow() || board.checkcol() || board.checkdiag();
	}

	public static boolean boardFull(){
		return board.boardFull();
	}
}