package application.game;

import application.IPlayer;

/**
 * Created by kelvin on 4/29/15.
 */
public class GameState {
    private IPlayer activePlayer;

    private boolean gameInProgress;
    private boolean isGameTie;
    private IPlayer winner;

    public GameState(){
        gameInProgress = false;
        isGameTie = false;
        winner = null;
    }

    public void startGame(IPlayer startingPlayer) {
        activePlayer = startingPlayer;
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
    public IPlayer getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(IPlayer player) {
        activePlayer = player;
    }
}
