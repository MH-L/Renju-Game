package application;

import model.Board;
import exceptions.InvalidIndexException;
import exceptions.WithdrawException;

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

	private static Board board;

	public Game() {
		board = new Board();
	}

	public void withdraw() throws WithdrawException, InvalidIndexException{
		board.withdrawMove(getActivePlayer().getLastMove());
		board.withdrawMove(getInactivePlayer().getLastMove());
		getActivePlayer().withdraw();
		getInactivePlayer().forceWithdraw();
	}

	/**
	 * Prompts the player to make their next move.
	 * Toggles the active player at the end of their successful turn.
	 *
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