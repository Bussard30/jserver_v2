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
	public boolean noNotification = true;

	public Object input;

	/**
	 * system time in which the job gets put into queue
	 */
	private long queueTime;

	private long delay;
	private long jobProcessingTime;

	private int index;

	private ThreadProcessingBehaviour tpb;

	/**
	 * Since the threading system uses runtime analysis on ThreadedJobs to
	 * calculate appx. queue time,<br>
	 * extend this class to create new jobs.
	 * 
	 * @param r
	 *            Runnable to be executed
	 */
	public ThreadedJob(Runnable r)
	{
		index = ThreadManager.getInstance().generateId();
		this.job = (a) ->
		{
			r.run();
			return null;
		};
	}

	/**
	 * Since the threading system uses runtime analysis on ThreadedJobs to
	 * calculate appx. queue time,<br>
	 * extend this class to create new jobs.
	 * 
	 * @param r
	 *            Function to be executed
	 * @param input
	 *            Input object for function
	 * @param noReturn
	 *            bool to determine if this job has a result which needs to be
	 *            stored
	 */
	public ThreadedJob(Function<Object, Object> r, Object input, boolean noReturn)
	{
		job = r;
		index = ThreadManager.getInstance().generateId();
		this.input = input;
		noNotification = noReturn;
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

	/**
	 * point of time where job has been queued, not delta of how long the queue
	 * time was
	 * 
	 * @return
	 */
	public long getQueueTime()
	{
		return queueTime;
	}

	public void setQueueTime(long queueTime)
	{
		this.queueTime = queueTime;
	}

	/**
	 * delta of when job has been queued and when it was started
	 * 
	 * @return
	 */
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

	public void setIndex(int index)
	{
		this.index = index;
	}

	public abstract ThreadProcessingBehaviour getProcessingBehaviour();

	/**
	 * unused?? might have to be removed
	 * @param tpb
	 */
	public void setProcessingBehaviour(ThreadProcessingBehaviour tpb)
	{
		this.tpb = tpb;
	}
	
	public long getFinishedTime()
	{
		return jobProcessingTime + delay + queueTime;
	}

	public abstract long getTimeOut();
}
