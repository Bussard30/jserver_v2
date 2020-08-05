package de.jserverv2.bussard30.threading.types;

import java.util.NoSuchElementException;
import java.util.Vector;

public interface ThreadPriority
{
	public static ThreadPriority LOW = new ThreadPriority()
	{
		
		@Override
		public ThreadProcessingBehaviour getProcessingBehaviour()
		{
			return ThreadProcessingBehaviour.SLOWDOWN;
		}
		
		@Override
		public int getPriority()
		{
			return 0;
		}
	};
	
	public static ThreadPriority NORMAL = new ThreadPriority()
	{
		@Override
		public ThreadProcessingBehaviour getProcessingBehaviour()
		{
			return ThreadProcessingBehaviour.SLOWDOWN;
		}
		
		@Override
		public int getPriority()
		{
			return 1;
		}
	};
	
	public static ThreadPriority HIGH = new ThreadPriority()
	{
		
		@Override
		public ThreadProcessingBehaviour getProcessingBehaviour()
		{
			return ThreadProcessingBehaviour.SLOWDOWN;
		}
		
		@Override
		public int getPriority()
		{
			return 2;
		}
	};
	public int getPriority();
	public ThreadProcessingBehaviour getProcessingBehaviour();
	
	
	static Vector<ThreadPriority> priorities = new Vector<>();
	
	public static void register(ThreadPriority tp)
	{
		priorities.add(tp);
	}
	
	/**
	 * Fetches 
	 * @param i priority indicator
	 * @return
	 * @throws NoSuchElementException
	 */
	public static ThreadPriority getPriority(int i) throws NoSuchElementException
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
