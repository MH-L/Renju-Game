package application.command;

import application.IPlayer;
import model.BoardLocation;

/**
 * Created by kelvin on 4/29/15.
 */
public class Command {
    public enum Type {
        MOVE(),
        WITHDRAW(),
        QUIT()

    }
    Type type;
    BoardLocation value;
    IPlayer sender;

    // TODO make some type of signing so one player can't issue a command in the other player's name
    public Command(Type type, IPlayer sender) {
        this.type = type;
        this.sender = sender;
    }

    public Command(Type type, BoardLocation value, IPlayer sender) {
        this.type = type;
        this.value = value;
        this.sender = sender;
    }

    public BoardLocation getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public IPlayer getSender() {
        return sender;
    }

    public void setValue(BoardLocation value) {
        this.value = value;
    }
}
