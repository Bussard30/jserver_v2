package de.jserverv2.bussard30.threading.manager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

import de.jserverv2.bussard30.threading.types.ThreadPool;
import de.jserverv2.bussard30.threading.types.ThreadedJob;

public class ThreadDiag
{
	public static HashMap<Class<? extends ThreadedJob>, Vector<Long>> timings = new HashMap<>();
	public static HashMap<Class<? extends ThreadedJob>, Double> median = new HashMap<>();
	public static HashMap<Class<? extends ThreadedJob>, Double> avg = new HashMap<>();
	/**
	 * top 1 percent
	 */
	public static HashMap<Class<? extends ThreadedJob>, Double> t1p = new HashMap<>();
	/**
	 * top 0.1 percent
	 */
	public static HashMap<Class<? extends ThreadedJob>, Double> t01p = new HashMap<>();

	public static HashMap<ThreadPool, Vector<Long>> poolQueue = new HashMap<>();
	public static HashMap<ThreadPool, Double> poolMedian = new HashMap<>();
	public static HashMap<ThreadPool, Double> poolAvg = new HashMap<>();
	/**
	 * top 1 percent
	 */
	public static HashMap<ThreadPool, Double> poolT1p = new HashMap<>();
	/**
	 * top 0.1 percent
	 */
	public static HashMap<ThreadPool, Double> poolT01p = new HashMap<>();

	public static void register(Class<? extends ThreadedJob> c)
	{
		synchronized (timings)
		{
			timings.put(c, new Vector<Long>());
		}
	}

	public static void addEntry(ThreadedJob t, ThreadPool tp)
	{
		Vector<Long> v = null;
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
	 * @param c
	 *            Class of ThreadedJob
	 */
	public static void recalculate(Class<? extends ThreadedJob> c)
	{
		double median = 0;
		double avg = 0;
		double t1p = 0;
		double t01p = 0;
		Vector<Long> v = null;

		synchronized (timings)
		{
			v = timings.get(c);
		}
		if (v.size() % 2 == 0)
		{
			median = v.get((v.size() + 1) / 2);
		} else
		{
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
	 * @param tp
	 */
	public static void recalculate(ThreadPool tp)
	{
		double median = 0;
		double avg = 0;
		double t1p = 0;
		double t01p = 0;
		Vector<Long> v = null;

		synchronized (timings)
		{
			v = poolQueue.get(tp);
		}
		if (v.size() % 2 == 0)
		{
			median = v.get((v.size() + 1) / 2);
		} else
		{
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
}
