package de.bussard30.jserverv2.java.threading.types;

import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * ThreadPriority determines in which order ThreadedJob(s) inside ThreadPool(s) are being processed.
 * @author Bussard30
 *
 */
public interface ThreadPriority
{
	ThreadPriority LOW = () -> 0;
	
	ThreadPriority NORMAL = () -> 1;
	
	ThreadPriority HIGH = () -> 2;
	int getPriority();
	
	
	Vector<ThreadPriority> priorities = new Vector<>();
	
	static void register(ThreadPriority tp)
	{
		priorities.add(tp);
	}
	
	/**
	 * Fetches 
	 * @param i priority indicator
	 * @return
	 * @throws NoSuchElementException
	 */
	static ThreadPriority getPriority(int i) throws NoSuchElementException
	{
		for(ThreadPriority tp : priorities)
		{
			if(tp.getPriority() == i)
			{
				return tp;
			}
		}
		throw new NoSuchElementException("Could not find element");
	}
}
