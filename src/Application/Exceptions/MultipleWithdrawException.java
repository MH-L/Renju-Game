package Exceptions;

public class MultipleWithdrawException extends WithdrawException {
	public MultipleWithdrawException(){
		super();
	}

	public MultipleWithdrawException(String e){
		super(e);
	}

	public MultipleWithdrawException(Throwable e){
		super(e);
	}
}
