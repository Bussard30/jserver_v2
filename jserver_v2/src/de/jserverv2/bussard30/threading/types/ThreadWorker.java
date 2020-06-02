package de.jserverv2.bussard30.threading.types;

import java.util.function.Predicate;

public class ThreadWorker
{
	private Predicate<ThreadedJob> fetchJob;
	
	public ThreadWorker()
	{
		
	}
	
	public void setPredicate(Predicate<ThreadedJob> fetchJob)
	{
		this.fetchJob = fetchJob;
	}
}
