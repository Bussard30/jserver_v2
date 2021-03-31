<<<<<<< HEAD
package threading.manager;


import threading.types.ThreadPool;
import threading.types.ThreadPriority;
import threading.types.ThreadedJob;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashMap;

public class ThreadManager {
    public static int maxThreadPools = 30;
    private static ThreadManager instance;
    private final int[] priorityList;
    /**
     * TODO HashMap<ThreadedJob, ThreadPool> stores where threadedjobs are being
     * processed. should probably be seperated more by starting letters of the
     * job or something idk or you just assign every threadedjob an id, an index
     * of in what hashmap the threadedjob is !
     */
    private final HashMap<ThreadedJob, ThreadPool>[] assignments;
    private Object[] locks;
    /**
     * int[] contains all pool indexes for a certain object so you can identify,
     * for example, all event threadpools, and get where they are in the
     * assignments array Example for Object would be "{static Object EventPools
     * = new Object()}"
     */
    private HashMap<Object, int[]> poolAssignments;

    @SuppressWarnings("unchecked")
    public ThreadManager() throws InstanceAlreadyExistsException {
        if (instance == null) {
            ThreadManager.instance = this;
        } else {
            throw new InstanceAlreadyExistsException();
        }

        ThreadPriority[] values = ThreadPriority.values();
        priorityList = new int[values.length];

        for (int i = 0; i < priorityList.length; i++) {
            priorityList[i] = values[i].getPriority();
        }
        quickSort(priorityList, 0, priorityList.length);

        assignments = new HashMap[maxThreadPools];

    }

    public void handleEvent(ThreadedJob e) {

    }

    /**
     * just for testing
     *
     * @param index
     * @return
     */
    public HashMap<ThreadedJob, ThreadPool> getAssignment(int index) {
        return assignments[index];
    }

    /**
     * Quicksort algorithm.
     *
     * @param arr
     * @param begin
     * @param end
     */
    private void quickSort(int[] arr, int begin, int end) {
        if (begin < end) {
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
    private int partition(int[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
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

=======
package de.bussard30.jserverv2.java.threading.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.management.InstanceAlreadyExistsException;

import de.bussard30.jserverv2.java.networking.logger.Logger;
import de.bussard30.jserverv2.java.networking.logger.LoggerThread;
import de.bussard30.jserverv2.java.threading.types.*;

/**
 * Main class of asnyc threading that can be used to queue jobs.
 * 
 * @author Bussard30
 *
 */
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
	private final HashMap<ThreadedJob, ThreadPool>[] assignments;
	private final HashMap<ThreadedJob, Integer>[] jobIndexes;

	/**
	 * int[] contains all pool indexes for a certain object so you can identify,
	 * for example, all event threadpools, and get where they are in the
	 * assignments array Example for Object would be
	 */

	private final HashMap<ThreadPoolIdentifier, Vector<Integer>> poolAssignments;

	private final ThreadPool[] threadpools;

	public static final int maxThreadPools = 30;

	/**
	 * Example object for pool separation
	 */
	public static final EventHandlerIdent EventPools = new EventHandlerIdent();

	/**
	 * Example object for pool separation
	 */
	public static final EventHandlerIdent LoggerPools = new EventHandlerIdent();

	/**
	 * Example object for pool separation
	 */
	public static final EventHandlerIdent HashingPools = new EventHandlerIdent();

	public static final int maxIndexing = 50;

	public static LoggerThread lt;

	@SuppressWarnings("unchecked")
	/**
	 * Instantiates ThreadManager and automatically launches LoggerThread
	 * 
	 * @throws InstanceAlreadyExistsException
	 */
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
		poolAssignments = new HashMap<>();

		jobIndexes = new HashMap[maxIndexing];

		threadpools = new ThreadPool[maxThreadPools];

		// TODO Init @assignments and @jobindexes
		for (int i = 0; i < assignments.length; i++)
		{
			assignments[i] = new HashMap<>();
		}

		for (int i = 0; i < jobIndexes.length; i++)
		{
			jobIndexes[i] = new HashMap<>();
		}
		lt = new LoggerThread();
		lt.start();
		Logger.info(this, "Successfully instantiated ThreadManager!");
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
			Logger.info(this, "Working on: index<" + temp.get(i) + "> at:" + i);
			Logger.info(this, "Current queued tasks : " + threadpools[temp.get(i)].getQueuedTasks());
			if (threadpools[temp.get(i)].getQueuedTasks() == 0)
			{
				// queues job
				synchronized (assignments[i])
				{
					Logger.info(this, "Put job in threadpool" + i);
					assignments[i].put(e, threadpools[i]);
				}

				synchronized (jobIndexes[e.getIndex()])
				{
					Logger.info(this, "Put job index in map" + e.getIndex());
					jobIndexes[e.getIndex()].put(e, i);
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
			Logger.info(this, "Put job in threadpool" + index);
			assignments[index].put(e, threadpools[index]);
		}
		synchronized (jobIndexes[e.getIndex()])
		{
			Logger.info(this, "Put job index in map" + e.getIndex());
			jobIndexes[e.getIndex()].put(e, index);
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
					Logger.error(this, "Could not find result index: " + e.getIndex());
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
			Logger.error(this, "Could not get result.", t);
			return false;
		}

	}

	public boolean removeMapping(ThreadedJob tj)
	{
		try
		{
			Integer i = null;
			synchronized (jobIndexes[tj.getIndex()])
			{
				i = jobIndexes[tj.getIndex()].remove(tj);
			}
			synchronized (assignments[i])
			{
				assignments[i].remove(tj);
			}
			return true;
		} catch (Throwable t)
		{
			Logger.error(this, "Could not remove mapping.", t);
			return false;
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
		Logger.info(this, "Adding threadpool...");
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
					Logger.info(this, "Adding threadpool to list...");
					threadpools[i] = tp;
					Vector<Integer> temp = new Vector<>();
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

	/**
	 * Returns the result hashmap (not a copy!)
	 * 
	 * @return
	 */
	public HashMap<ThreadedJob, ThreadPool>[] getResultMap()
	{
		return assignments;

	}

	public static int[] reverse(int[] a, int n)
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

	@Override
	public String toString()
	{
		return "ThreadManager";
	}
>>>>>>> develop
}
