package com.epam.testapp.util;

/**
 * NoSuchElementException --- class exception which is thrown when xml document
 * doesn't contain specified element
 * 
 * @author Marharyta_Amelyanchuk
 */
public class NoSuchElementException extends Exception {

	private static final long serialVersionUID = 4992223960436174956L;

	public NoSuchElementException() {
		super();
	}

	public NoSuchElementException(String message) {
		super(message);
	}

	public NoSuchElementException(String message, Exception e) {
		super(message, e);
	}

	public NoSuchElementException(Throwable cause) {
		super(cause);
	}

}