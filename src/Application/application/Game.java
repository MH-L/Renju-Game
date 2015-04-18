package application;

import model.Board;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;

public class Game {
	public static final int MULTIPLAYER_GAME_MODE = 1;
	public static final int SINGLEPLAYER_GAME_MODE = 2;

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
		player1 = playerFirst ? new Player() : application.AI.getInstance();
		player2 = playerFirst ? application.AI.getInstance() : new Player();
		AI.initAI(difficulty, board, !playerFirst);
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
	 * Prompts the player to make their next move
	 *
	 * @throws InvalidIndexException
	 * 		Thrown if the chosen move is invalid
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