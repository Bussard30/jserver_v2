package de.jserverv2.bussard30.threading.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.jserverv2.bussard30.threading.types.ThreadPool;
import de.jserverv2.bussard30.threading.types.ThreadedJob;

public class GarbageCollector
{
	/**
	 * in what interval in ms the update method is supposed to be executed
	 */
	public static final long interval = 300000;

	public static void gc(HashMap<ThreadedJob, ThreadPool>[] hm)
	{
		ArrayList<ThreadedJob> tbr = new ArrayList<>();
		for (HashMap<ThreadedJob, ThreadPool> e : hm)
		{
			synchronized (e)
			{
				for (Map.Entry<ThreadedJob, ThreadPool> m : e.entrySet())
				{
					if (System.currentTimeMillis() - m.getKey().getFinishedTime() > m.getKey().getTimeOut())
					{
						tbr.add(m.getKey());
					}
				}
			}
		}
		for (ThreadedJob tj : tbr)
		{
			ThreadManager.getInstance().removeMapping(tj);
		}
	}

	public static void gc()
	{
		gc(ThreadManager.getInstance().getResultMap());
	}
}
