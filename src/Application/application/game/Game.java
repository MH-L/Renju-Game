package application.game;

import application.IPlayer;
import application.command.Command;
import application.command.Move;
import application.command.Quit;
import application.command.Withdraw;
import exceptions.InvalidIndexException;
import model.Board;
import model.BoardLocation;

/**
 * Created by kelvin on 4/29/15.
 */
public abstract class Game {
	public static final int NUM_HINTS_LIMIT = 3;
	public static final int NUM_REGRETS_LIMIT = 3;

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


	protected IPlayer player1;
	protected IPlayer player2;

	protected GameState state;

	private Board board;

	public Game() {
		board = new Board();
		state = new GameState();
	}

	public GameState getState(){
		return state;
	}

	private void withdraw(IPlayer sender) {
		if (state.isGameInProgress() && sender.equals(state.getActivePlayer())) {
			if (state.getPlayerRegrets(state.getActivePlayer()) > 0) {
				try {
					board.withdrawMove(state.removeLastMove(state.getActivePlayer()));
					state.decreasePlayerRegrets(state.getActivePlayer());
					System.out.println("You only have " + state.getPlayerRegrets(state.getActivePlayer()) + " withdrawals remaining.");
					// Force withdraw the inactive player
					if (state.getLastMove(state.getInactivePlayer()) != null) {
						board.withdrawMove(state.removeLastMove(state.getInactivePlayer()));
					}
				} catch (InvalidIndexException e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("You have no more withdrawals");
			}
		}
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
	private void makeMove(IPlayer sender, BoardLocation loc) throws InvalidIndexException {
		if (state.isGameInProgress() && sender.equals(state.getActivePlayer())) {
			board.updateBoard(loc, state.isPlayerOne(sender));
			state.setLastMove(sender, loc);
			if (checkWinning()) {
				state.setWinner(sender);
			}
			else if (boardFull()) {
				state.setTieGame();
			}
			else state.toggleActivePlayer();
		}
	}

	private void quit(IPlayer sender) {
		if (state.isGameInProgress() && sender.equals(state.getActivePlayer())) {
			state.setWinner(state.getInactivePlayer());
		}
	}

	public void doCommand(Command c) {
		if (c == null) {
			return;
		}
		if (state.isGameInProgress() && c.getSender().equals(state.getActivePlayer())) {
			if (c instanceof Move) {
				try {
					makeMove(c.getSender(), ((Move) c).getLocation());
				} catch (InvalidIndexException e) {
					System.out.println(e.getMessage());
				}
			} else if (c instanceof Quit) {
				quit(c.getSender());
			} else if (c instanceof Withdraw) {
				withdraw(c.getSender());
			}
		}
	}

	public Board getBoard(){
		return board;
	}

	public IPlayer getPlayer1() {
		return player1;
	}

	public IPlayer getPlayer2(){
		return player2;
	}

	private boolean checkWinning(){
		return board.checkrow() || board.checkcol() || board.checkdiag();
	}

	private boolean boardFull(){
		return board.boardFull();
	}
}