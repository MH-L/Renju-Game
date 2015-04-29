package application.command;

import application.IPlayer;
import model.BoardLocation;

/**
 * Created by kelvin on 4/29/15.
 */
public class Move extends Command {
    private BoardLocation location;

    public Move(IPlayer sender, BoardLocation location) {
        super(sender);
        this.location = location;
    }

    public BoardLocation getLocation() {
        return location;
    }
}
