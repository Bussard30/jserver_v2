package de.jserverv2.bussard30.threading.types;

import java.util.function.Function;

public abstract class ThreadedJob
{
	public Function<Object, Object> job;

	/**
	 * After executing the job, the ThreadedManager is going to discard it
	 * instantly.
	 */
	public boolean noNotification = false;
	
	private Object input;

	public ThreadedJob(Runnable r)
	{
		this.job = (a) ->
		{
			r.run();
			return null;
		};
	}

	public ThreadedJob(Function<Object, Object> r, Object input, boolean noReturn)
	{
		this.input = input;
	}

	public ThreadedJob(Runnable r, boolean noReturn, boolean noNotification)
	{
		this(r);
		this.noNotification = noNotification;
	}

	public boolean keepNotification()
	{
		return !noNotification;
	}

	public Object run()
	{
		return job.apply(input);
	}
}
