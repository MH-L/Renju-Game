package application.game;

import application.IPlayer;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;
import model.Board;
import model.BoardLocation;

public abstract class Game {
	public static final int MULTIPLAYER_GAME_MODE = 1;
	public static final int SINGLEPLAYER_GAME_MODE = 2;
	public static final int NETWORK_GAME_MODE = 3;
	public static final int AI_VERSUS_AI_GAME_MODE = 9;

	public static final int NOVICE_DIFFICULTY = 1;
	public static final int INTERMEDIATE_DIFFICULTY = 2;
	public static final int ADVANCED_DIFFICULTY = 3;
	public static final int ULTIMATE_DIFFICULTY = 4;

	protected IPlayer player2;
	protected IPlayer player1;
	protected IPlayer activePlayer;

	private Board board;
	private boolean gameInProgress;
	private boolean isGameTie;
	private IPlayer winner;

	public Game() {
		startGame();
	}

	private void startGame() {
		board = new Board();
		gameInProgress = true;
		isGameTie = false;
		winner = null;
	}

	public void withdraw() throws WithdrawException, InvalidIndexException{
		board.withdrawMove(getActivePlayer().getLastMove());
		board.withdrawMove(getInactivePlayer().getLastMove());
		getActivePlayer().withdraw();
		getInactivePlayer().forceWithdraw();
	}

	/**
	 * Makes the move on the board at location <code>loc</code> if the <code>sender</code>
	 * is the current active player. On successful move, update the board and toggle the
	 * player at the end of the turn.
	 *
	 * @param sender
	 * 		The player making the move
	 * @param loc
	 * 		The board location the sender wishes to place
	 * @throws InvalidIndexException
	* 		thrown if the move chosen is invalid
	 *
	 */
	public void makeMove(IPlayer sender, BoardLocation loc) throws InvalidIndexException {
		if (gameInProgress && sender.equals(activePlayer)) {
			if (!board.updateBoard(loc, isPlayer1Active()))
				throw new InvalidIndexException("The index you entered is not valid!");
			if (checkWinning()) {
				winner = activePlayer;
				gameInProgress = false;
				isGameTie = false;
			}
			else if (boardFull()) {
				gameInProgress = false;
				isGameTie = true;
			}
			else toggleActivePlayer();
		}

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
		activePlayer = (activePlayer.equals(player1)) ? player2 : player1;
	}

	public boolean isWinning() {
		return winner != null;
	}

	private boolean checkWinning(){
		return board.checkrow() || board.checkcol() || board.checkdiag();
	}

	public boolean isGameInProgress() {
		return gameInProgress;
	}

	public boolean isGameTie() {
		return isGameTie;
	}

	public IPlayer getWinner() {
		return winner;
	}

	private boolean boardFull(){
		return board.boardFull();
	}
}