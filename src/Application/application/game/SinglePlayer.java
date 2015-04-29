package application.game;

import application.AI;
import application.Player;

/**
 * Created by kelvin on 4/28/15.
 */
public class SinglePlayer extends Game {

    private int difficulty;
    private boolean isPlayerFirst;

    /**
     * Create a single-player game of difficulty <code>difficulty</code>
     * @param difficulty
     *      The difficulty of the game
     * @param isPlayerFirst
     *      true if the player is player 1
     *      false if the player is player 2
     */
    public SinglePlayer(int difficulty, boolean isPlayerFirst){
        super();
        this.difficulty = difficulty;
        this.isPlayerFirst = isPlayerFirst;
        player1 = isPlayerFirst ? new Player() : new AI(difficulty, getBoard(), true);
        player2 = isPlayerFirst ? new AI(difficulty, getBoard(), false) : new Player();
        activePlayer = player1;
    }
}
