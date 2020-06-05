package de.jserverv2.bussard30.threading.types;

import java.util.function.Predicate;

@Deprecated
public class ThreadWorker
{
	private Predicate<ThreadedJob> fetchJob;
	
	@Deprecated
	public ThreadWorker()
	{
		
	}
	
	@Deprecated
	public void setPredicate(Predicate<ThreadedJob> fetchJob)
	{
		this.fetchJob = fetchJob;
	}
}
