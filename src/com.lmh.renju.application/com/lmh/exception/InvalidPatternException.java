package com.lmh.exception;
/**
 * An exception thrown when the pattern has
 * incorrect number of locations(i.e. other than 3 or 4).
 * @author Minghao
 *
 */
public class InvalidPatternException extends Exception {
	public InvalidPatternException(){
		super();
	}

	public InvalidPatternException(String e){
		super(e);
	}

	public InvalidPatternException(Throwable e){
		super(e);
	}
}
