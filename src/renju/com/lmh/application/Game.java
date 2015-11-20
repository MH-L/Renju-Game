package renju.com.lmh.application;

import renju.com.lmh.application.IPlayer;
import renju.com.lmh.exception.InvalidIndexException;
import renju.com.lmh.exception.WithdrawException;
import renju.com.lmh.model.Board;

public abstract class Game {
	public enum Mode {
		SINGLEPLAYER,
		MULTIPLAYER,
		NETWORK,
		AIVERSUSAI
	}
	public enum Difficulty {
		NOVICE,
		INTERMEDIATE,
		ADVANCED,
		ULTIMATE
	}

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
	 * @return
	 * 		state of if there's a valid next move to make
	 * @throws InvalidIndexException
	 * 		thrown if the move chosen is invalid
	 */
	public void makeMove() throws InvalidIndexException {
		if (!board.updateBoard(activePlayer.makeMove(), isPlayer1Active())) {
			if (getActivePlayer().getClass() == AI.class) {
				board.printPlayerStoneList(true);
				board.printPlayerStoneList(false);
				System.exit(1);
			}
			throw new InvalidIndexException("The index you entered is not valid!");
		}
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
		return board.isBoardFull();
	}
}