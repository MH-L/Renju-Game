package Application;

import Exceptions.InvalidIndexException;
import Exceptions.WithdrawException;
import Model.Board;
import Model.BoardLocation;

public class Game {
	public static final int MULTIPLAYER_GAME_MODE = 1;
	public static final int SINGLEPLAYER_GAME_MODE = 2;

	private int mode;
	private static Game instance = null;
	private IPlayer player2;
	private IPlayer player1;
	private int difficulty;
	private Board board;
	private boolean activePlayer;

	private Game(int mode, int difficulty) {
		this.mode = mode;
		if (mode == 1) {
			player2 = Application.AI.getInstance(difficulty, board);
			player1 = new Player();
			this.difficulty = difficulty;
			this.board = new Board();
		} else {
			player1 = new Player();
			player2 = new Player();
			this.difficulty = difficulty;
			this.board = new Board();
		}
	}

	public static Game getInstance(int mode, int difficulty) {
		if (instance == null) {
			instance = new Game(mode, difficulty);
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

	public void makeMove(){
		BoardLocation loc;
		if (activePlayer) {
			loc = player1.makeMove();
		} else {
			loc = player2.makeMove();
		}

		try {
			board.updateBoard(loc, activePlayer);
		} catch (InvalidIndexException e) {
			e.printStackTrace();
		}
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

	public boolean getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(boolean active) {
		this.activePlayer = active;
	}


}
