package application.command;

import application.IPlayer;

/**
 * Created by kelvin on 4/29/15.
 */
public abstract class Command {
    private IPlayer sender;

    public Command(IPlayer sender) {
        this.sender = sender;
    }

    public IPlayer getSender() {
        return sender;
    }
}
