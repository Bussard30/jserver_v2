package de.bussard30.jserverv2.networking.exceptions;

public class BadPacketException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3353601339684869221L;

	public BadPacketException()
	{
		super();
	}

	public BadPacketException(String message)
	{
		super(message);
	}

	public BadPacketException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BadPacketException(Throwable cause)
	{
		super(cause);
	}

	public BadPacketException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
