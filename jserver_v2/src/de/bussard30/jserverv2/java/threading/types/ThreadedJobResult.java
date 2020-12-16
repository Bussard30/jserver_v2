package de.bussard30.jserverv2.java.threading.types;

import de.bussard30.jserverv2.java.threading.exceptions.ThreadJobException;
import de.bussard30.jserverv2.java.threading.exceptions.ThreadJobNotDoneException;

/**
 * Contains result for ThreadedJob
 * @author Bussard30
 *
 */
public class ThreadedJobResult
{
	private Object result;
	private boolean hasFailed;

	public ThreadedJobResult(Object result)
	{
		this.result = result;
	}
	
	/**
	 * 
	 * @param result
	 * @param hasFailed
	 */
	public ThreadedJobResult(Exception e, boolean hasFailed)
	{
		result = e;
		hasFailed = true;
	}

	public void throwException(Throwable t)
	{
		hasFailed = true;
		result = t;
	}

	/**
	 * automatically calls finish after setting result
	 * 
	 * @param o
	 */
	public void setResult(Object o)
	{
		if (!hasFailed)
		{
			result = o;
		}
	}

	/**
	 * 
	 * @return
	 * @throws ThreadJobException
	 * @throws ThreadJobNotDoneException
	 */
	public Object getResult() throws ThreadJobException
	{
		if (hasFailed)
		{
			throw new ThreadJobException((Throwable) result);
		}
		return result;

	}

	public boolean hasFailed()
	{
		return hasFailed;
	}
}
