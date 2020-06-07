package de.jserverv2.bussard30.threading.types;

import java.util.function.Function;

import de.jserverv2.bussard30.threading.manager.ThreadManager;

public abstract class ThreadedJob
{
	public Function<Object, Object> job;

	/**
	 * After executing the job, the ThreadedManager is going to discard the
	 * results instantly.
	 */
	public boolean noNotification = false;

	public Object input;

	/**
	 * system time in which the job gets put into queue
	 */
	private long queueTime;

	private long delay;
	private long jobProcessingTime;
	
	private int index;

	public ThreadedJob(Runnable r)
	{
		index = ThreadManager.getInstance().generateRandomId();
		this.job = (a) ->
		{
			r.run();
			return null;
		};
	}

	public ThreadedJob(Function<Object, Object> r, Object input, boolean noReturn)
	{
		index = ThreadManager.getInstance().generateRandomId();
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

	public long getQueueTime()
	{
		return queueTime;
	}

	public void setQueueTime(long queueTime)
	{
		this.queueTime = queueTime;
	}

	public long getDelay()
	{
		return delay;
	}

	public void setDelay(long delay)
	{
		this.delay = delay;
	}

	public long getJobProcessingTime()
	{
		return jobProcessingTime;
	}

	public void setJobProcessingTime(long jobProcessingTime)
	{
		this.jobProcessingTime = jobProcessingTime;
	}

	public int getIndex()
	{
		return index;
	}
}
