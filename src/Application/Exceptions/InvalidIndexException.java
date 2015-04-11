package Exceptions;

public class InvalidIndexException extends Exception {
	public InvalidIndexException(){
		super();
	}

	public InvalidIndexException(String e){
		super(e);
	}

	public InvalidIndexException(Throwable e){
		super(e);
	}
}
