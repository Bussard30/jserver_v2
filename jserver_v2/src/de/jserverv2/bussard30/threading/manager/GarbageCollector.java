package de.jserverv2.bussard30.threading.manager;

import java.util.ArrayList;
import java.util.Vector;

import de.jserverv2.bussard30.threading.types.ThreadedJob;

public class GarbageCollector
{
	public void gc(Vector<ThreadedJob>[] v)
	{
		ArrayList<ThreadedJob> tbr = new ArrayList<>();
		for(Vector<ThreadedJob> e : v)
		{
			for(ThreadedJob t : e)
			{
				if(System.currentTimeMillis() - t.getQueueTime() >= t.getTimeOut())
				{
					tbr.add(t);
				}
			}
			e.removeAll(tbr);
		}
	}
}
