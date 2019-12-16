package networking.server;

import java.util.HashMap;

public class ThreadManager
{
	/**
	 * Stores how thread is currently performing(idle time)
	 */
	private HashMap<ServerThread, Integer> threadscore;
	
	/**
	 * Assigns Runnable to serverthread for execution
	 */
	private HashMap<ServerThread, Runnable> assignment;
}
