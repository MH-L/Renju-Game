package renju.com.lmh.application;

import renju.com.lmh.application.Game;
import renju.com.lmh.application.Player;

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
        activePlayer = player1;
    }
}
