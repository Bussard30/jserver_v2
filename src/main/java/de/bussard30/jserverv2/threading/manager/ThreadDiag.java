package de.bussard30.jserverv2.threading.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import de.bussard30.jserverv2.threading.types.ThreadPool;
import de.bussard30.jserverv2.threading.types.ThreadedJob;

/**
 * Calculates averages, median etc. for job processing and queueing time.
 * 
 * @author Bussard30
 *
 */
public class ThreadDiag
{
	/**
	 * in what interval in ms the update method is supposed to be executed
	 */
	public static final long interval = 60000;

	/**
	 * not sorted list for all processing times for
	 */
	public static final HashMap<Class<? extends ThreadedJob>, Vector<Long>> timings = new HashMap<>();

	/**
	 * contains median for processing time for every ThreadedJob
	 */
	public static final HashMap<Class<? extends ThreadedJob>, Double> median = new HashMap<>();

	/**
	 * contains avg for procesing time for every ThreadedJob
	 */
	public static final HashMap<Class<? extends ThreadedJob>, Double> avg = new HashMap<>();

	/**
	 * top 1 percent
	 */
	public static final HashMap<Class<? extends ThreadedJob>, Double> t1p = new HashMap<>();
	/**
	 * top 0.1 percent
	 */
	public static final HashMap<Class<? extends ThreadedJob>, Double> t01p = new HashMap<>();

	/**
	 * not sorted list for all queue timings for threadpools
	 */
	public static final HashMap<ThreadPool, Vector<Long>> poolQueue = new HashMap<>();

	/**
	 * contains median for queue time for every pool
	 */
	public static final HashMap<ThreadPool, Double> poolMedian = new HashMap<>();

	/**
	 * contains avg for queue time for every pool
	 */
	public static final HashMap<ThreadPool, Double> poolAvg = new HashMap<>();

	/**
	 * top 1 percent
	 */
	public static final HashMap<ThreadPool, Double> poolT1p = new HashMap<>();
	/**
	 * top 0.1 percent
	 */
	public static final HashMap<ThreadPool, Double> poolT01p = new HashMap<>();

	/**
	 * maximum average queue time for ThreadedJob required for threaddiag; value
	 * in ms
	 */
	public static final double maxAverageQueueTime = 5;

	/**
	 * maximum median queue time for ThreadedJob required for threaddiag; value
	 * in ms
	 */
	public static final double maxMedianQueueTime = 5;

	/**
	 * maximum top 1 percent queue time for ThreadedJob required for threaddiag;
	 * value in ms
	 */
	public static final double maxT1PQueueTime = 10;

	/**
	 * maximum top 0.1 percent queue time for ThreadedJob required for
	 * threaddiag; value in ms
	 */
	public static final double maxT01PQueueTime = 50;

	public static void register(Class<? extends ThreadedJob> c)
	{
		synchronized (timings)
		{
			timings.put(c, new Vector<>());
		}
	}

	public static void addEntry(ThreadedJob t, ThreadPool tp)
	{
		Vector<Long> v;
		synchronized (timings)
		{
			v = timings.get(t.getClass());
		}
		v.add(t.getJobProcessingTime());
		synchronized (poolQueue)
		{
			poolQueue.get(tp).add(t.getDelay());
		}

	}

	/**
	 * Recalculates median, average, top 1 percent and top 0.1 percent of
	 * processing times of ThreadedJob class.<br>
	 * Operation might take a while.
	 *
	 *
	 * Local synchronization to save time locking the hashmap.
	 * 
	 * @param c
	 *            Class of ThreadedJob
	 */
	public static void recalculate(Class<? extends ThreadedJob> c,
			HashMap<Class<? extends ThreadedJob>, Vector<Long>> hm)
	{
		double median;
		double avg;
		double t1p;
		double t01p;
		Vector<Long> v;

		synchronized (hm)
		{
			v = hm.get(c);
		}
		if (v.size() % 2 == 0)
		{
			median = v.get((v.size() + 1) / 2);
		} else
		{
			median = (double)(v.get(v.size() / 2) + v.get(v.size() / 2 + 1)) / 2d;
		}
		long l = 0;
		for (long ll : v)
		{
			l += ll;
		}
		avg = l / (double) v.size();

		Collections.sort(v);
		int t1 = 1;
		int t01 = 1;
		if (v.size() > 1000)
		{
			t01 = (int) Math.round(v.size() * 0.001d);
			t1 = (int) Math.round(v.size() * 0.01d);

		} else if (v.size() > 100)
		{
			t1 = (int) Math.round(v.size() * 0.01d);
		}

		long l1 = 0;
		long l01 = 0;

		for (int i = v.size() - 1; i > v.size() - t1 - 1; i--)
		{
			l1 += v.get(i);
		}

		for (int i = v.size() - 1; i > v.size() - t01 - 1; i--)
		{
			l01 += v.get(i);
		}
		t1p = l1 / (double) t1;
		t01p = l1 / (double) t01;

		synchronized (ThreadDiag.median)
		{
			ThreadDiag.median.put(c, median);
		}
		synchronized (ThreadDiag.avg)
		{
			ThreadDiag.avg.put(c, avg);
		}
		synchronized (ThreadDiag.t1p)
		{
			ThreadDiag.t1p.put(c, t1p);
		}
		synchronized (ThreadDiag.t01p)
		{
			ThreadDiag.t01p.put(c, t01p);
		}

	}

	/**
	 * Recalculates median, average, top 1 percent and top 0.1 percent of queue
	 * times of certain threadpool.<br>
	 * Operation might take a while.
	 *
	 * Local synchronization to save time locking hashmap.
	 * 
	 * @param tp todo
	 * @param hm todo
	 */
	public static void recalculate(ThreadPool tp, HashMap<ThreadPool, Vector<Long>> hm)
	{
		double median;
		double avg;
		double t1p;
		double t01p;
		Vector<Long> v;

		synchronized (hm)
		{
			v = hm.get(tp);
		}
		if (v.size() % 2 == 0)
		{
			median = v.get((v.size() + 1) / 2);
		} else
		{
			//noinspection IntegerDivisionInFloatingPointContext
			median = (v.get(v.size() / 2) + v.get(v.size() / 2 + 1)) / 2;
		}
		long l = 0;
		for (long ll : v)
		{
			l += ll;
		}
		avg = l / (double) v.size();

		Collections.sort(v);
		int t1 = 1;
		int t01 = 1;
		if (v.size() > 1000)
		{
			t01 = (int) Math.round(v.size() * 0.001d);
			t1 = (int) Math.round(v.size() * 0.01d);

		} else if (v.size() > 100)
		{
			t1 = (int) Math.round(v.size() * 0.01d);
		}

		long l1 = 0;
		long l01 = 0;

		for (int i = v.size() - 1; i > v.size() - t1 - 1; i--)
		{
			l1 += v.get(i);
		}

		for (int i = v.size() - 1; i > v.size() - t01 - 1; i--)
		{
			l01 += v.get(i);
		}
		t1p = l1 / (double) t1;
		t01p = l1 / (double) t01;

		synchronized (ThreadDiag.poolMedian)
		{
			ThreadDiag.poolMedian.put(tp, median);
		}
		synchronized (ThreadDiag.poolAvg)
		{
			ThreadDiag.poolAvg.put(tp, avg);
		}
		synchronized (ThreadDiag.poolT1p)
		{
			ThreadDiag.poolT1p.put(tp, t1p);
		}
		synchronized (ThreadDiag.poolT01p)
		{
			ThreadDiag.poolT01p.put(tp, t01p);
		}
	}

	/**
	 * Executes {@link #recalculateJobs()} and {@link #recalculatePools()}.
	 */
	public static void recalculateAll()
	{
		recalculateJobs();
		recalculatePools();
	}

	/**
	 * Clones current timing hashmap and then recalculates all timings for jobs
	 */
	@SuppressWarnings("unchecked")
	public static void recalculateJobs()
	{
		ArrayList<Class<? extends ThreadedJob>> al = new ArrayList<>();
		HashMap<Class<? extends ThreadedJob>, Vector<Long>> copy;
		synchronized (timings)
		{
			copy = (HashMap<Class<? extends ThreadedJob>, Vector<Long>>) timings.clone();
		}
		for (Map.Entry<Class<? extends ThreadedJob>, Vector<Long>> me : copy.entrySet())
		{
			al.add(me.getKey());
		}

		for (Class<? extends ThreadedJob> c : al)
		{
			recalculate(c, copy);
		}
	}

	/**
	 * Clones current timing hashmap and then recalculates all timings for threadpools
	 */
	@SuppressWarnings("unchecked")
	public static void recalculatePools()
	{
		ArrayList<ThreadPool> all = new ArrayList<>();
		HashMap<ThreadPool, Vector<Long>> copy;
		synchronized (poolQueue)
		{
			copy = (HashMap<ThreadPool, Vector<Long>>) poolQueue.clone();
		}
		for (Map.Entry<ThreadPool, Vector<Long>> me : copy.entrySet())
		{
			all.add(me.getKey());
		}

		for (ThreadPool t : all)
		{
			recalculate(t, copy);
		}
	}
}
