package de.jserverv2.bussard30.threading.manager;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import javax.management.InstanceAlreadyExistsException;

import de.jserverv2.bussard30.threading.types.ThreadPool;
import de.jserverv2.bussard30.threading.types.ThreadPoolIdentifier;
import de.jserverv2.bussard30.threading.types.ThreadPriority;
import de.jserverv2.bussard30.threading.types.ThreadedJob;

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

	/**
	 * locks for poolAssignments
	 */
	private Object[] locks0;

	/**
	 * locks for threads in threadpools
	 */
	private Object[] locks1;
	private Object[] locks2;

	private ThreadPool[] threadpools;

	public static int maxThreadPools = 30;

	public static final Object EventPools = new Object();
	public static final Object LoggerPools = new Object();
	public static final Object HashingPools = new Object();

	public static final int maxIndexing = 50;

	private Random r;

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

		ThreadPriority[] values = ThreadPriority.values();
		priorityList = new int[values.length];

		for (int i = 0; i < priorityList.length; i++)
		{
			priorityList[i] = values[i].getPriority();
		}
		quickSort(priorityList, 0, priorityList.length);

		assignments = new HashMap[maxThreadPools];
		poolAssignments = new HashMap<ThreadPoolIdentifier, Vector<Integer>>();

		jobIndexes = new HashMap[maxIndexing];

		// TODO Init @assignments and @jobindexes
		r = new Random();
	}

	public void handleEvent(Object o, ThreadedJob e)
	{
		// diagnostics still necessary but this should be fine for now
		Vector<Integer> temp = poolAssignments.get(o);

		if (temp.size() == 0)
		{
			// TODO create a new threadpool
		}

		int tasks = 0;
		int index = 0;
		boolean firstrun = true;
		for (int i = temp.get(0); i < temp.size(); i++)
		{
			if (threadpools[temp.get(i)].getQueuedTasks() == 0)
			{
				// queues job
				synchronized (locks0[i])
				{
					assignments[i].put(e, threadpools[i]);
				}
				synchronized (locks1[i])
				{
					jobIndexes[e.getIndex()].put(e, new Integer(i));
				}
				threadpools[i].addJob(e);
				return;
			}
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
		synchronized (locks0[index])
		{
			assignments[index].put(e, threadpools[index]);
		}
		threadpools[index].addJob(e);
		return;

	}

	public boolean getResult(ThreadedJob e, Object output)
	{
		try
		{
			Integer i = jobIndexes[e.getIndex()].get(e);
			if (i == null)
			{
				return false;
			} else
			{
				ThreadPool tp = null;
				synchronized (locks0[i])
				{
					tp = assignments[i].get(e);
				}
				output = tp.getResult(e);
				return true;
			}
		} catch (Throwable t)
		{
			t.printStackTrace();
			return false;
		}

	}
	
	private int iterator = 0;
	/**
	 * assign every threadedjob a random id
	 * reduces search time since hashmaps become smaller
	 * @return
	 */
	public int generateId()
	{
		if(iterator == maxIndexing - 1)
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
		if (poolAssignments.containsKey(identifier))
		{
			for (int i = 0; i < maxThreadPools; i++)
			{
				if (threadpools[i] == null)
				{
					threadpools[i] = tp;
					poolAssignments.get(identifier).add(i);
				}
			}
		} else
		{
			for (int i = 0; i < maxThreadPools; i++)
			{
				if (threadpools[i] == null)
				{
					threadpools[i] = tp;
					poolAssignments.put(identifier, new Vector<Integer>());
				}
			}
		}
	}

	/**
	 * Quicksort algorithm.
	 * 
	 * @param arr
	 * @param begin
	 * @param end
	 */
	private void quickSort(int arr[], int begin, int end)
	{
		if (begin < end)
		{
			int partitionIndex = partition(arr, begin, end);

			quickSort(arr, begin, partitionIndex - 1);
			quickSort(arr, partitionIndex + 1, end);
		}
	}

	/**
	 * Quicksort algorithm.
	 * 
	 * @param arr
	 * @param begin
	 * @param end
	 * @return
	 */
	private int partition(int arr[], int begin, int end)
	{
		int pivot = arr[end];
		int i = (begin - 1);

		for (int j = begin; j < end; j++)
		{
			if (arr[j] <= pivot)
			{
				i++;

				int swapTemp = arr[i];
				arr[i] = arr[j];
				arr[j] = swapTemp;
			}
		}

		int swapTemp = arr[i + 1];
		arr[i + 1] = arr[end];
		arr[end] = swapTemp;

		return i + 1;
	}

	public static ThreadManager getInstance()
	{
		return instance;
	}
}
