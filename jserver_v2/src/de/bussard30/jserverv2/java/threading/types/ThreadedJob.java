<<<<<<< HEAD
package threading.types;

import java.util.function.Function;

public abstract class ThreadedJob {
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

    public ThreadedJob(Runnable r) {
        this.job = (a) ->
        {
            r.run();
            return null;
        };
    }

    public ThreadedJob(Function<Object, Object> r, Object input, boolean noReturn) {
        this.input = input;
    }

    public ThreadedJob(Runnable r, boolean noReturn, boolean noNotification) {
        this(r);
        this.noNotification = noNotification;
    }

    public boolean keepNotification() {
        return !noNotification;
    }

    public Object run() {
        return job.apply(input);
    }

    public long getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(long queueTime) {
        this.queueTime = queueTime;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getJobProcessingTime() {
        return jobProcessingTime;
    }

    public void setJobProcessingTime(long jobProcessingTime) {
        this.jobProcessingTime = jobProcessingTime;
    }
=======
package de.bussard30.jserverv2.java.threading.types;

import java.util.function.Function;

import de.bussard30.jserverv2.java.threading.manager.ThreadManager;

/**
 * Abstract class for creating jobs to be performed by the async threading system.
 * @author Bussard30
 *
 */
public abstract class ThreadedJob
{
	public final Function<Object, Object> job;

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

	public ThreadedJob(Runnable r, boolean noReturn)
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
	
	public long getFinishedTime()
	{
		return jobProcessingTime + delay + queueTime;
	}

	/**
	 * timeout for results
	 * @return duration for timeout
	 */
	public abstract long getTimeOut();
>>>>>>> develop
}
