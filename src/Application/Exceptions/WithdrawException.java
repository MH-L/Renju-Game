package Exceptions;

public abstract class WithdrawException extends Exception {

	public WithdrawException(){
		super();
	}

	public WithdrawException(String e){
		super(e);
	}

	public WithdrawException(Throwable e){
		super(e);
	}
}
