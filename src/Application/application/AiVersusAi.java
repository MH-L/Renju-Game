package application;

import application.game.Game;

/**
 * Created by kelvin on 4/28/15.
 */
public class AiVersusAi extends Game {

    /**
     * Create a new game between two AI's
     */
    public AiVersusAi(int ai1Difficulty, int ai2Difficulty){
        super();
        player1 = new AI(ai1Difficulty, getBoard(), true);
        player2 = new AI (ai2Difficulty, getBoard(), false);
        activePlayer = player1;
    }
}
