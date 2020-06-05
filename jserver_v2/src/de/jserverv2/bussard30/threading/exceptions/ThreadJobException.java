package de.jserverv2.bussard30.threading.exceptions;

public class ThreadJobException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7322110731828281906L;
	private Throwable e;
	public ThreadJobException(Throwable e)
	{
		this.e = e;
	}
	
	public Throwable getThrowable()
	{
		return e;
	}
}