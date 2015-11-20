package renju.com.lmh.exception;

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
