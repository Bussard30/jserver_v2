package de.jserverv2.bussard30.threading.types;

import java.util.HashMap;
import java.util.Vector;
import java.util.function.Function;

import de.jserverv2.bussard30.threading.exceptions.ThreadJobException;
import de.jserverv2.bussard30.threading.exceptions.ThreadJobNotDoneException;

public class ThreadPool
{
	private Vector<Thread> threadpool;

	private int[] priorityList;

	private Vector<ThreadedJob>[] jobs;

	/**
	 * This hashmap is used to only store results.
	 */
	private HashMap<ThreadedJob, ThreadedJobResult> jobResults;
	private Object jobResultsLock;

	public ThreadPool()
	{
		threadpool = new Vector<Thread>();
		
		jobResults = new HashMap<ThreadedJob, ThreadedJobResult>();
		jobResultsLock = new Object();
	}

	public ThreadPool(Thread... threads)
	{
		this();

		for (Thread t : threads)
		{
			threadpool.add(t);
		}

	}

	public ThreadPool(int[] priorityList)
	{
		this();
		this.priorityList = priorityList;
	}

	public ThreadPool(int[] priorityList, Thread... threads)
	{
		this(priorityList);

		for (Thread t : threads)
		{
			threadpool.add(t);
		}
	}

	public void addJob(ThreadedJob t)
	{
	}

	public Object getResult(ThreadedJob t) throws ThreadJobException, ThreadJobNotDoneException
	{
		Object o = null;
		synchronized (jobResultsLock)
		{
			if(!jobResults.containsKey(t))
			{
				throw new ThreadJobNotDoneException();
			}
			o = jobResults.get(t).getResult();
			if(o == null)
			{
				
			}
			jobResults.remove(t);
		}
		return o;
	}

	/**
	 * Works, should not be used though.
	 * 
	 * @return function that fetches a threadedjob
	 */
	@Deprecated
	public Function<ThreadPriority, ThreadedJob> getJobFetcher()
	{
		return tp -> getThreadedJob(tp);
	}

	public ThreadedJob getThreadedJob(ThreadPriority minimum)
	{
		return null;
	}

	public void finishTask(ThreadedJob t, Object result)
	{
		if (t.keepNotification())
		{
			ThreadedJobResult temp;
			synchronized (jobResultsLock)
			{
				temp = jobResults.get(t);
			}
			temp.setResult(result);
		} else
		{
			synchronized (jobResultsLock)
			{
				jobResults.remove(t);
			}
		}
	}

	public void finishTask(ThreadedJob t, Object result, Throwable e)
	{
		if (t.keepNotification())
		{
			ThreadedJobResult temp;
			synchronized (jobResultsLock)
			{
				temp = jobResults.get(t);
			}
			temp.throwException(e);
		} else
		{
			synchronized (jobResultsLock)
			{
				jobResults.remove(t);
			}
		}
	}

	private class ThreadPoolWorker
	{
		private ThreadPool tp;
		private Thread t;
		private boolean running;
		private ThreadPriority min;

		public ThreadPoolWorker(ThreadPool tp, ThreadPriority minimum)
		{
			this.tp = tp;
			this.min = minimum;
			
			t = new Thread(() ->
			{
				while (running)
				{
					ThreadedJob t = tp.getThreadedJob(min);
					try
					{
						finishTask(t, t.run());
					}
					catch(Throwable e)
					{
						finishTask(t, null, e);
					}
				}
			});
		}

		public void start()
		{
			running = true;
			t.start();
		}

	}
}
