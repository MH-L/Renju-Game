package application.game;

import application.IPlayer;
import model.BoardLocation;

import java.util.ArrayList;

/**
 * Created by kelvin on 4/29/15.
 */
public class PlayerState {
    private IPlayer player;
    private ArrayList<BoardLocation> moveList;

    private int regrets;

    public PlayerState(IPlayer player) {
        this.player = player;
        moveList = new ArrayList<>();
        regrets = Game.NUM_REGRETS_LIMIT;
    }

    public void setLastMove(BoardLocation loc) {
        moveList.add(loc);
    }

    public BoardLocation peekLastMove() {
        if (moveList.size() > 0) {
           return moveList.get(moveList.size()-1);
        }
        return null;
    }

    public BoardLocation popLastMove() {
        if (moveList.size() > 0) {
            return moveList.remove(moveList.size() - 1);
        }
        return null;
    }

    public int getRegrets() {
        return regrets;
    }

    public IPlayer getPlayer() {
        return player;
    }

    public int decrementRegrets() {
        if (regrets > 0){
            regrets--;
        }
        return regrets;
    }
}
