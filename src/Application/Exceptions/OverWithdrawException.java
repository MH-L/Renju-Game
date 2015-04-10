package Exceptions;

public class OverWithdrawException extends WithdrawException{
	public OverWithdrawException(){
		super();
	}

	public OverWithdrawException(String e){
		super(e);
	}

	public OverWithdrawException(Throwable e){
		super(e);
	}
}
