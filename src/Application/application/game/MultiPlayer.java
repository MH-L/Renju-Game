package application.game;

import application.Player;

/**
 * Created by kelvin on 4/28/15.
 */
public class MultiPlayer extends Game {

    /**
     * Create a multi-player game with two players
     */
    public MultiPlayer(){
        super();
        player1 = new Player();
        player2 = new Player();
        getState().startNewGame(player1, player2);
    }
}
