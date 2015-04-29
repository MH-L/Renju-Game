package application.game;

import application.IPlayer;
import model.BoardLocation;

/**
 * Created by kelvin on 4/29/15.
 */
public class GameState {
    private IPlayer activePlayer;
    private PlayerState p1State;
    private PlayerState p2State;

    private IPlayer player1;
    private IPlayer player2;

    private boolean gameInProgress;
    private boolean isGameTie;
    private IPlayer winner;

    public GameState(){
        gameInProgress = false;
        isGameTie = false;
        winner = null;
    }

    public void startNewGame(IPlayer player1, IPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        p1State = new PlayerState(player1);
        p2State = new PlayerState(player2);
        activePlayer = player1;
        gameInProgress = true;
        isGameTie = false;
        winner = null;
    }

    public boolean isGameInProgress(){
        return gameInProgress;
    }

    public boolean isGameEndInTie() {
       return isGameTie;
    }

    public boolean isGameEndInWin() {
        return winner != null;
    }

    public IPlayer getWinner() {
        return winner;
    }

    public boolean isActivePlayer(IPlayer player) {
        return activePlayer.equals(player);
    }

    protected void setWinner(IPlayer player) {
        gameInProgress = false;
        isGameTie = false;
        winner = player;
    }

    protected void setTieGame() {
        gameInProgress = false;
        isGameTie = true;
        winner = null;
    }

    protected void setLastMove(IPlayer sender, BoardLocation loc) {
        PlayerState ps = getPlayerState(sender);
        if (ps != null) {
            ps.setLastMove(loc);
        }
    }

    public PlayerState getPlayerState(IPlayer player) {
        if (player.equals(player1)) {
            return p1State;
        } else if (player.equals(player2)) {
            return p2State;
        } else return null;
    }

    public BoardLocation getLastMove(IPlayer player) {
        PlayerState ps = getPlayerState(player);
        if (ps != null) {
            return ps.peekLastMove();
        }
        return null;
    }

    protected BoardLocation removeLastMove(IPlayer player) {
        PlayerState ps = getPlayerState(player);
        if (ps != null) {
            return ps.popLastMove();
        }
        return null;
    }

    public int getPlayerRegrets(IPlayer player) {
        PlayerState ps = getPlayerState(player);
        if (ps == null) {
            return 0;
        }
        return ps.getRegrets();
    }

    protected void decreasePlayerRegrets(IPlayer player) {
        PlayerState ps = getPlayerState(player);
        if (ps == null) {
            return;
        }
        ps.decrementRegrets();
    }

    public void toggleActivePlayer(){
        activePlayer = (activePlayer.equals(player1)) ? player2 : player1;
    }

    public IPlayer getActivePlayer() {
        return activePlayer;
    }

    public IPlayer getInactivePlayer(){
        if (activePlayer.equals(player1)){
            return player2;
        } else return player1;
    }

    public boolean isPlayerOne(IPlayer player){
        return player1.equals(player);
    }

}
