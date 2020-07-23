package de.jserverv2.bussard30.networking.exceptions;

public class BadRequestException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1329899281949335125L;


	public BadRequestException()
	{
		super();
	}

	public BadRequestException(String message)
	{
		super(message);
	}

	public BadRequestException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BadRequestException(Throwable cause)
	{
		super(cause);
	}

	public BadRequestException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
