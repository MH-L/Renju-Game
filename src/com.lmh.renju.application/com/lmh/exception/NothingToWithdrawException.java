package com.lmh.exception;

public class NothingToWithdrawException extends WithdrawException {
	public NothingToWithdrawException(){
		super();
	}

	public NothingToWithdrawException(String e){
		super(e);
	}

	public NothingToWithdrawException(Throwable e){
		super(e);
	}
}
