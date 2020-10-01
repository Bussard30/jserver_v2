package de.jserverv2.bussard30.threading.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.management.InstanceAlreadyExistsException;

import de.jserverv2.bussard30.threading.types.ThreadPool;
import de.jserverv2.bussard30.threading.types.ThreadPoolIdentifier;
import de.jserverv2.bussard30.threading.types.ThreadPriority;
import de.jserverv2.bussard30.threading.types.ThreadedJob;
import de.jserverv2.bussard30.threading.types.ThreadedJobResult;

public class ThreadManager
{
	private static ThreadManager instance;

	private int[] priorityList;

	/**
	 * TODO HashMap<ThreadedJob, ThreadPool> stores where threadedjobs are being
	 * processed. should probably be seperated more by starting letters of the
	 * job or something idk or you just assign every threadedjob an id, an index
	 * of in what hashmap the threadedjob is !
	 */
	private HashMap<ThreadedJob, ThreadPool>[] assignments;
	private HashMap<ThreadedJob, Integer>[] jobIndexes;

	/**
	 * int[] contains all pool indexes for a certain object so you can identify,
	 * for example, all event threadpools, and get where they are in the
	 * assignments array Example for Object would be
	 */

	private HashMap<ThreadPoolIdentifier, Vector<Integer>> poolAssignments;

	private ThreadPool[] threadpools;

	public static int maxThreadPools = 30;

	/**
	 * Example object for pool seperation
	 */
	public static final Object EventPools = new Object();
	
	/**
	 * Example object for pool seperation
	 */
	public static final Object LoggerPools = new Object();
	
	/**
	 * Example object for pool seperation
	 */
	public static final Object HashingPools = new Object();

	public static final int maxIndexing = 50;

	@SuppressWarnings("unchecked")
	public ThreadManager() throws InstanceAlreadyExistsException
	{
		if (instance == null)
		{
			ThreadManager.instance = this;
		} else
		{
			throw new InstanceAlreadyExistsException();
		}

		ThreadPriority[] values = new ThreadPriority[]
		{ ThreadPriority.LOW, ThreadPriority.NORMAL, ThreadPriority.HIGH };

		priorityList = new int[values.length];

		for (int i = 0; i < priorityList.length; i++)
		{
			priorityList[i] = values[i].getPriority();
		}

		Arrays.sort(priorityList);
		priorityList = reverse(priorityList, priorityList.length);

		assignments = new HashMap[maxThreadPools];
		poolAssignments = new HashMap<ThreadPoolIdentifier, Vector<Integer>>();

		jobIndexes = new HashMap[maxIndexing];

		threadpools = new ThreadPool[maxThreadPools];

		// TODO Init @assignments and @jobindexes
		for (int i = 0; i < assignments.length; i++)
		{
			assignments[i] = new HashMap<ThreadedJob, ThreadPool>();
		}

		for (int i = 0; i < jobIndexes.length; i++)
		{
			jobIndexes[i] = new HashMap<ThreadedJob, Integer>();
		}
	}

	public void handleEvent(ThreadPoolIdentifier o, ThreadedJob e)
	{
		// diagnostics still necessary but this should be fine for now
		Vector<Integer> temp = poolAssignments.get(o);

		if (temp.size() == 0)
		{
			addThreadPool(o, new ThreadPool(priorityList));
		}
		int tasks = 0;
		int index = 0;
		boolean firstrun = true;
		for (int i = temp.get(0); i < temp.size(); i++)
		{
			// if one threadpool has 0 queued jobs it instantly queues the job
			// and returns
			System.out.println("[ThreadManager]Working on: index<" + temp.get(i) + "> at:" + i);
			System.out.println("[ThreadManager]Current queued tasks : " + threadpools[temp.get(i)].getQueuedTasks());
			if (threadpools[temp.get(i)].getQueuedTasks() == 0)
			{
				// queues job
				synchronized (assignments[i])
				{
					System.out.println("[ThreadManager]Put job in threadpool" + i);
					assignments[i].put(e, threadpools[i]);
				}
				
				synchronized (jobIndexes[e.getIndex()])
				{
					System.out.println("[ThreadManager]Put job index in map" + e.getIndex());
					jobIndexes[e.getIndex()].put(e, new Integer(i));
				}
				threadpools[i].addJob(e);
				return;
			}

			// looks for the threadpool with the least amount of queued tasks
			if (firstrun)
			{
				tasks = threadpools[i].getQueuedTasks();
				index = temp.get(i);
				firstrun = false;
			} else
			{
				if (threadpools[i].getQueuedTasks() < tasks)
				{
					tasks = threadpools[i].getQueuedTasks();
					index = temp.get(i);
				}
			}
		}

		synchronized (assignments[index])
		{
			System.out.println("[ThreadManager]Put job in threadpool" + index);
			assignments[index].put(e, threadpools[index]);
		}
		synchronized (jobIndexes[e.getIndex()])
		{
			System.out.println("[ThreadManager]Put job index in map" + e.getIndex());
			jobIndexes[e.getIndex()].put(e, new Integer(index));
		}
		threadpools[index].addJob(e);
		return;

	}

	boolean b = false;

	/**
	 * 
	 * @param e
	 * @param output
	 *            : empty threadedJobResult; method sets result in this object
	 * @return
	 */
	public boolean getResult(ThreadedJob e, ThreadedJobResult output)
	{
		try
		{
			if (!jobIndexes[e.getIndex()].containsKey(e))
			{
				if (!b)
				{
					System.out.println("[SEVERE][ThreadManager]Could not find index " + e.getIndex());
					b = true;
				}
				return false;
			} else
			{
				Integer i = jobIndexes[e.getIndex()].remove(e);
				ThreadPool tp = null;
				synchronized (assignments[i])
				{
					tp = assignments[i].remove(e);
				}
				output.setResult(tp.getResult(e));
				return true;
			}
		} catch (Throwable t)
		{
			t.printStackTrace();
			return false;
		}

	}
	
	public void removeMapping(ThreadedJob tj)
	{
		try
		{
			Integer i = jobIndexes[tj.getIndex()].remove(tj);
			synchronized (assignments[i])
			{
				assignments[i].remove(tj);
			}
		}
		catch(Throwable t)
		{
			t.printStackTrace();
		}
	}

	private int iterator = 0;

	/**
	 * assign every threadedjob a random id reduces search time since hashmaps
	 * become smaller
	 * 
	 * @return
	 */
	public int generateId()
	{
		if (iterator == maxIndexing - 1)
		{
			iterator = -1;
		}
		return iterator++;
	}

	/**
	 * 
	 * @param identifier
	 *            : should be some kind of public static object
	 * @param tp
	 *            the new Threadpool
	 */
	public void addThreadPool(ThreadPoolIdentifier identifier, ThreadPool tp)
	{
		System.out.println("[ThreadManager]Adding threadpool...");
		if (poolAssignments.containsKey(identifier))
		{
			for (int i = 0; i < maxThreadPools; i++)
			{
				if (threadpools[i] == null)
				{
					System.out.println("Adding index...");
					threadpools[i] = tp;
					poolAssignments.get(identifier).add(i);
					break;
				}
			}
		} else
		{
			for (int i = 0; i < maxThreadPools; i++)
			{
				if (threadpools[i] == null)
				{
					System.out.println("[ThreadManager]Adding threadpool to list...");
					threadpools[i] = tp;
					Vector<Integer> temp = new Vector<Integer>();
					temp.add(i);
					poolAssignments.put(identifier, temp);
					break;
				}
			}
		}
	}

	public int[] getPriorityList()
	{
		return priorityList;
	}

	public static ThreadManager getInstance()
	{
		return instance;
	}

	public static int[] reverse(int a[], int n)
	{
		int[] b = new int[n];
		int j = n;
		for (int i = 0; i < n; i++)
		{
			b[j - 1] = a[i];
			j = j - 1;
		}
		return b;
	}
}
