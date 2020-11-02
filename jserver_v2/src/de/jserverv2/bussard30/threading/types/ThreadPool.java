package de.jserverv2.bussard30.threading.types;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Vector;
import java.util.function.Function;

import de.jserverv2.bussard30.threading.exceptions.ThreadJobException;
import de.jserverv2.bussard30.threading.exceptions.ThreadJobNotDoneException;
import de.jserverv2.bussard30.threading.manager.ThreadDiag;

/**
 * Thread pool that maintains several worker threads to handle ThreadedJob(s)
 * @author Bussard30
 *
 */
public class ThreadPool
{

	/**
	 * Sorted after descending priority e.g. index 0 = ThreadPriority.HIGH, index 1 = ThreadPriority.NORMAL
	 */
	private Vector<ThreadPoolWorker>[] threadpool;

	private int[] priorityList;

	private Vector<ThreadedJob>[] jobs;

	private int queuedJobs = 0;
	private int workingOnJobs = 0;

	/**
	 * This hashmap is used to only store results.
	 */
	private HashMap<ThreadedJob, ThreadedJobResult> jobResults;
	private Object jobResultsLock;
	
	private int workers;

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

		for (int i = 0; i < jobs.length; i++)
		{
			jobs[i] = new Vector<ThreadedJob>();
		}

		for (int i = 0; i < threadpool.length; i++)
		{
			threadpool[i] = new Vector<ThreadPoolWorker>();
		}
		addWorker(ThreadPriority.LOW);
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
		System.out.println("[ThreadPool]Adding job in ThreadPool.");
		int index = 0;
		for (int i = 0; i < priorityList.length; i++)
		{
			if (priorityList[i] == tp.getPriority())
			{
				index = i;
			}
		}
		t.setQueueTime(System.currentTimeMillis());
		synchronized (jobs[index])
		{
			jobs[index].add(t);
			System.out.println("[ThreadPool]Added job at " + index);
		}
		queuedJobs++;
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
			o = jobResults.remove(t).getResult();
		}
		return o;
	}

	/**
	 * Returns function that fetches and returns a job on execution.
	 * Deprecated due to new worker system.
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
			if (jobs[i].size() == 0)
			{
				continue;
			}
			synchronized (jobs[i])
			{
				t = jobs[i].remove(0);
			}
			queuedJobs--;
			workingOnJobs++;
		}
		if (t == null)
		{
			throw new NoSuchElementException();
		} else
		{
			return t;
		}
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
		System.out.println("[ThreadPool] Executing finish job method...");
		// Diagnostics
		// t.getDelay()
		// t.getJobProcessingTime()
		if (t.keepNotification())
		{
			System.out.println(
					"[ThreadPool] result[type:\"" + result.getClass().getName() + "\";sysout:\"" + result + "\"]");
			ThreadedJobResult temp = new ThreadedJobResult(result);
			synchronized (jobResults)
			{
				System.out.println("[ThreadPool] Adding job result to map...");
				jobResults.put(t, temp);
			}
			ThreadDiag.addEntry(t, this);
		} else
		{
			ThreadDiag.addEntry(t, this);
		}
		workingOnJobs--;
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
		}
		workingOnJobs--;
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
		synchronized (threadpool[index])
		{
			threadpool[index].add(new ThreadPoolWorker(this, assignment));
		}
		workers++;
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
		synchronized (threadpool[index])
		{
			ThreadPoolWorker tpw = threadpool[index].remove(0);
			tpw.join();
			threadpool[index].remove(tpw);
		}
		workers--;
	}

	public int getQueuedTasks()
	{
		return queuedJobs + workingOnJobs;
	}

	public int getWorkers()
	{
		return workers;
	}
	
	protected class ThreadPoolWorker
	{
		private ThreadPool tp;
		private Thread t;
		private boolean running;
		private ThreadPriority min;
		private boolean suspended;
		private Object sync = new Object();


		private ThreadPoolWorker(ThreadPool tp, ThreadPriority minimum)
		{
			this.tp = tp;
			this.min = minimum;

			t = new Thread(() ->
			{
				while (running)
				{
					if(suspended)
					{
						synchronized (sync)
						{
							try
							{
								sync.wait();
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}
					ThreadedJob t = null;
					try
					{
						t = tp.getThreadedJob(min);
					} catch (NoSuchElementException e)
					{
						try
						{
							Thread.sleep(1);
						} catch (InterruptedException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						continue;
					}
					t.setDelay(System.currentTimeMillis() - t.getQueueTime());
					// DEBUG
					System.out.println("[Worker]working on job with index:" + t.getIndex());
					try
					{
						finishJob(t, t.run());
					} catch (Throwable e)
					{
						// DEBUG
						System.out.println("Exception...");
						e.printStackTrace();
						finishJob(t, null, e);
					}
				}
			});
			start();
		}

		protected void start()
		{
			running = true;
			t.start();
		}

		protected void join()
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

		protected void interrupt()
		{
			running = false;
			t.interrupt();
		}
		
		protected void suspend()
		{
			suspended = true;
		}
		
		protected void resume()
		{
			sync.notify();
			suspended = false;
		}

	}
}
