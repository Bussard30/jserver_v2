package de.bussard30.jserverv2.java.threading.manager;

import java.util.Map.Entry;

import de.bussard30.jserverv2.java.networking.logger.Logger;
import de.bussard30.jserverv2.java.threading.types.ThreadPool;
import de.bussard30.jserverv2.java.threading.types.ThreadPriority;

/**
 * Class for thread that runs garbage collector and threaddiag
 * 
 * @author Bussard30
 *
 */
public class UtilThread
{
	private final Thread t;

	private long lastGc;
	private long lastDiag;

	private boolean running;

	public UtilThread()
	{
		t = new Thread(() ->
		{
			while (running)
			{
				if (System.currentTimeMillis() - lastDiag > ThreadDiag.interval)
				{
					ThreadDiag.recalculateAll();
					for (Entry<ThreadPool, Double> m : ThreadDiag.poolMedian.entrySet())
					{
						if (m.getValue() > ThreadDiag.maxMedianQueueTime)
						{
							m.getKey().addWorker(ThreadPriority.NORMAL);
						}
					}

					for (Entry<ThreadPool, Double> m : ThreadDiag.poolAvg.entrySet())
					{
						if (m.getValue() > ThreadDiag.maxAverageQueueTime)
						{
							m.getKey().addWorker(ThreadPriority.NORMAL);
						}
						/**
						 * 1.05 : 5 % tolerance calculates if one worker can be
						 * removed
						 */
						else if (((m.getKey().getWorkers() - 1) / m.getKey().getWorkers()) * 1.05d
								* m.getValue() < ThreadDiag.maxAverageQueueTime && m.getKey().getWorkers() > 1)
						{
							m.getKey().removeWorker(ThreadPriority.NORMAL);
						}
					}

					for (Entry<ThreadPool, Double> m : ThreadDiag.poolT1p.entrySet())
					{
						if (m.getValue() > ThreadDiag.maxT1PQueueTime)
						{
							m.getKey().addWorker(ThreadPriority.NORMAL);
						}
					}

					for (Entry<ThreadPool, Double> m : ThreadDiag.poolT01p.entrySet())
					{
						if (m.getValue() > ThreadDiag.maxT01PQueueTime)
						{
							m.getKey().addWorker(ThreadPriority.NORMAL);
						}
					}
				}

				if (System.currentTimeMillis() - lastGc > GarbageCollector.interval)
				{
					GarbageCollector.gc();
				}
			}
		});
	}

	public void start()
	{
		running = true;
		t.start();
	}

	public void interrupt()
	{
		t.interrupt();
	}

	/**
	 * Returns once finished with cycling (might be doing gc or diag so might
	 * take a while)
	 */
	public void join()
	{
		running = false;
		try
		{
			t.join();
		} catch (InterruptedException e)
		{
			Logger.error(this, e);
		}
	}

}
