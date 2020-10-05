package de.jserverv2.bussard30.threading.manager;

/**
 * Class for thread that runs garbage collector and threaddiag
 * 
 * @author Jonas
 *
 */
public class UtilThread
{
	private Thread t;

	private long lastGc;
	private long lastDiag;
	
	public UtilThread()
	{
		t = new Thread(() ->
		{
			if(System.currentTimeMillis() - lastDiag > ThreadDiag.interval)
			{
				ThreadDiag.recalculateAll();
			}
		});
	}

}
