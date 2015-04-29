package exceptions;

public class GameException extends Exception {
	public GameException(){
		super();
	}

	public GameException(String e){
		super(e);
	}

	public GameException(Throwable e){
		super(e);
	}
}
