package application;

/**
 * Created by kelvin on 4/28/15.
 */
public class AiVersusAi extends Game {

    /**
     * Create a new game between two AI's
     */
    public AiVersusAi(int Ai1Difficulty, int Ai2Difficulty){
        super();
        player1 = new AI (Ai1Difficulty, getBoard(), true);
        player2 = new AI (Ai2Difficulty, getBoard(), false);
        activePlayer = player1;
    }
}
