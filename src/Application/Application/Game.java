package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.Board;

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
	// possibly also include win status

	public int getMode() {
		return mode;
	}

	private Board board;
	private IPlayer activePlayer;

	private Game() {
		this.board = new Board();

		player1 = new Player();
		if (mode == SINGLEPLAYER_GAME_MODE) {
			player2 = Application.AI.getInstance();
		} else {
			player2 = new Player();
		}
		activePlayer = player1;
	}

	public void init(int mode){
		this.mode = mode;
	}

	/**
	 * If the game is single-player, initialize player 2 to be a computer
	 * with difficulty <code>difficulty</code>
	 * If the game is multi-player,  this method does nothing.
	 *
	 * @param difficulty
	 * 		The difficulty level
	 * @param board
	 * 		The board in play
	 */
	public void initAI(int difficulty, Board board){
		if (mode == SINGLEPLAYER_GAME_MODE) {
			player2 = Application.AI.getInstance();
			((AI) player2).initAI(difficulty, board);
		}
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	public void withdraw(boolean first) {
		if (mode == 1)
			try {
				if (first) {
					player1.withdraw();
					player2.forceWithdraw();
				} else {
					player2.withdraw();
					player1.withdraw();
				}
			} catch (WithdrawException e){
				e.printStackTrace(); // TODO
			}
	}

	public void makeMove() throws InvalidIndexException {
		board.updateBoard(activePlayer.makeMove(), isPlayer1Active());
	}

	public Board getBoard(){
		return board;
	}

	public IPlayer getPlayer2() {
		return player2;
	}

	public IPlayer getPlayer1() {
		return player1;
	}

	public IPlayer getActivePlayer() {
		return activePlayer;
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

	/**
	 * Returns the active player as a string of either "one" if player 1 is active
	 * and "two" if player two is active
	 * @return
	 * 		"one" if player 1 is active.
	 * 		"two if player 2 is active.
	 */
	public String getActivePlayerAsString(){
		if (isPlayer1Active()){
			return "one";
		} else return "two";
	}

}
