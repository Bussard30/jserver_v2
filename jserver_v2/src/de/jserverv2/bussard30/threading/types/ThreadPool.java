package de.jserverv2.bussard30.threading.types;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.function.Function;

import de.jserverv2.bussard30.threading.exceptions.ThreadJobException;
import de.jserverv2.bussard30.threading.exceptions.ThreadJobNotDoneException;

public class ThreadPool
{
	private Vector<ThreadPoolWorker>[] threadpool;
	private Object threadpoollock;

	private int[] priorityList;

	private Vector<ThreadedJob>[] jobs;
	private Object[] jobLocks;
	/**
	 * This hashmap is used to only store results.
	 */
	private HashMap<ThreadedJob, ThreadedJobResult> jobResults;
	private Object jobResultsLock;

	@SuppressWarnings("unchecked")
	private ThreadPool()
	{
		jobResults = new HashMap<ThreadedJob, ThreadedJobResult>();
		jobResultsLock = new Object();

	}

	@SuppressWarnings("unchecked")
	public ThreadPool(int[] priorityList)
	{
		this();
		this.priorityList = priorityList;

		jobs = new Vector[priorityList.length];
		threadpool = new Vector[priorityList.length];
		jobLocks = new Object[priorityList.length];

		for (int i = 0; i < priorityList.length; i++)
		{
			jobLocks[i] = new Object();
		}
	}

	/**
	 * Automatically selects normal ThreadPriority
	 * 
	 * @param t
	 */
	public void addJob(ThreadedJob t)
	{
		addJob(t, ThreadPriority.NORMAL);
	}

	public void addJob(ThreadedJob t, ThreadPriority tp)
	{
		int index = 0;
		for (int i = 0; i < priorityList.length; i++)
		{
			if (priorityList[i] == tp.getPriority())
			{
				index = i;
			}
		}
		synchronized (jobLocks[index])
		{
			t.setQueueTime(System.currentTimeMillis());
			jobs[index].add(t);
		}
	}

	public Object getResult(ThreadedJob t) throws ThreadJobException, ThreadJobNotDoneException
	{
		Object o = null;
		synchronized (jobResultsLock)
		{
			if (!jobResults.containsKey(t))
			{
				throw new ThreadJobNotDoneException();
			}
			o = jobResults.get(t).getResult();
			if (o == null)
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

	/**
	 * fetches a job, removes it from the jobs list
	 * 
	 * @param minimum
	 * @return
	 * @throws NoSuchElementException
	 */
	public ThreadedJob getThreadedJob(ThreadPriority minimum) throws NoSuchElementException
	{
		int index = 0;
		ThreadedJob t = null;
		for (int i = 0; i < priorityList.length; i++)
		{
			if (priorityList[i] == minimum.getPriority())
			{
				index = i;
			}
		}
		for (int i = 0; i <= index; i++)
		{
			synchronized (jobLocks[i])
			{
				t = jobs[i].firstElement();
				jobs[i].remove(t);
			}
		}
		return t;
	}

	/**
	 * 
	 * @param t
	 *            job that was finished
	 * @param result
	 *            result of that job
	 */
	public void finishJob(ThreadedJob t, Object result)
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

	/**
	 * 
	 * @param t
	 *            job that was finished
	 * @param result
	 *            not used
	 * @param e
	 *            exception thrown while executing task
	 */
	public void finishJob(ThreadedJob t, Object result, Throwable e)
	{
		t.setJobProcessingTime(System.currentTimeMillis() - t.getQueueTime() - t.getDelay());
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

	public int getIndex(ThreadPriority tp)
	{
		int index = 0;
		for (int i = 0; i < priorityList.length; i++)
		{
			if (priorityList[i] == tp.getPriority())
			{
				index = i;
			}
		}
		return index;
	}

	/**
	 * Creates another threadpoolworker which handles jobs with a priority at
	 * least of @see <"assignment">
	 * 
	 * @param assignment
	 */
	public void addWorker(ThreadPriority assignment)
	{
		int index = getIndex(assignment);
		synchronized (threadpoollock)
		{
			threadpool[index].add(new ThreadPoolWorker(this, assignment));
		}
	}

	/**
	 * Removes a random threadpoolworker which handles jobs with a priority at
	 * least of @see <"assignment">
	 * 
	 * @see {@link #addWorker(ThreadPriority)}
	 * @param assignment
	 */
	public void removeWorker(ThreadPriority assignment)
	{
		int index = getIndex(assignment);
		synchronized (threadpoollock)
		{
			ThreadPoolWorker tpw = threadpool[index].firstElement();
			tpw.stop();
			threadpool[index].remove(tpw);
		}
	}

	private class ThreadPoolWorker
	{
		private ThreadPool tp;
		private Thread t;
		private boolean running;
		private ThreadPriority min;

		private ThreadPoolWorker(ThreadPool tp, ThreadPriority minimum)
		{
			this.tp = tp;
			this.min = minimum;

			t = new Thread(() ->
			{
				while (running)
				{
					ThreadedJob t = tp.getThreadedJob(min);
					t.setDelay(System.currentTimeMillis() - t.getQueueTime());
					try
					{
						finishJob(t, t.run());
					} catch (Throwable e)
					{
						finishJob(t, null, e);
					}
				}
			});
			start();
		}

		private void start()
		{
			running = true;
			t.start();
		}

		private void stop()
		{
			running = false;
			try
			{
				t.join(10000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

	}
}
