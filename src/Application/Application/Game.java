package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.Board;

public class Game {
	public static final int MULTIPLAYER_GAME_MODE = 1;
	public static final int SINGLEPLAYER_GAME_MODE = 2;
	public static final int NETWORK_GAME_MODE = 3;

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
	 */
	public void initSinglePlayer(int difficulty){
		this.mode = SINGLEPLAYER_GAME_MODE;
		board = new Board();
		player1 = new Player();
		player2 = Application.AI.getInstance();
		AI.initAI(difficulty, board);
		activePlayer = player1;
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	public void withdraw() throws WithdrawException{
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
		board.updateBoard(activePlayer.makeMove(), isPlayer1Active());
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
			return player1;
		} else return player2;
	}

	public boolean isPlayer1Active(){
		return activePlayer.equals(player1);
	}

	public IPlayer toggleActivePlayer(){
		if (activePlayer.equals(player1)){
			activePlayer = player2;
		} else {
			activePlayer = player1;
		}
		return activePlayer;
	}

	public static boolean isWinning() {
		return board.checkrow() || board.checkcol() || board.checkdiag();
	}

	public static boolean boardFull(){
		return board.boardFull();
	}
}