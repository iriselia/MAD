package com.mygdx.hanto.common;

/**
 * The HantoException is the Exception that is thrown for any error that occurs during the
 * 
 */
public class HantoException extends Exception
{
	/**
	 * Every instance of this exception must have a message describing the exception.
	 * 
	 * @param message
	 *            the string describing the error causing the exception
	 */
	public HantoException(String message)
	{
		super(message);
	}

	/**
	 * An exception that was caused by some other exception.
	 * 
	 * @param message
	 *            the string describing the error causing the exception
	 * @param cause
	 *            the error that caused this exception
	 */
	public HantoException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
