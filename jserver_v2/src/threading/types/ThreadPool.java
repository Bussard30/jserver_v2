package threading.types;

import java.util.Vector;
import java.util.function.Function;

public class ThreadPool
{
	private Vector<Thread> threadpool;
	
	private ThreadedJob[][] jobs;
	
	public ThreadPool()
	{
		threadpool = new Vector<Thread>();
	}
	
	public ThreadPool(Thread... threads)
	{
		this();
		
		for(Thread t : threads)
		{
			threadpool.add(t);
		}
		
		
	}
	
	/**
	 * 
	 * @return function that fetches a threadedjob
	 */
	public Function<ThreadPriority, ThreadedJob> getJobFetcher()
	{
		return tp -> getThreadedJob(tp);
	}
	
	public ThreadedJob getThreadedJob(ThreadPriority minimum)
	{
		return null;
	}
}
