package de.bussard30.jserverv2.java.networking.server;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Vector;

/**
 * Does currently not work.
 * 
 * @author Bussard30
 *
 */
public class Diagnostics
{
	private static Diagnostics instance;

	private final boolean noThread;
	private final HashMap<ServerThread, Vector<ServerHandler>> handlers;
	private final HashMap<ServerHandler, ActiveTime> uptime;
	private final HashMap<ServerThread, Double> ranking;

	int i = 0;

	private final long threshhold = 30000;

	public Diagnostics()
	{
		if (instance == null)
		{
			instance = this;
		} else
		{
			throw new RuntimeException();
		}
		handlers = new HashMap<>();
		uptime = new HashMap<>();
		ranking = new HashMap<>();
		noThread = true;
		i = 0;
	}

	public void assign(ServerThread t, ServerHandler h)
	{
		if (handlers.containsKey(t))
		{
			handlers.get(t).add(h);
		} else
		{
			Vector<ServerHandler> hh = new Vector<>();
			hh.add(h);
			handlers.put(t, hh);
		}
	}

	public void process(ServerHandler h, boolean state)
	{
		if (state)
		{

			if (!uptime.containsKey(h)) {
				uptime.put(h, new ActiveTime());
			}
			uptime.get(h).setMark(true);

		} else
		{
			if (!uptime.containsKey(h)) {
				uptime.put(h, new ActiveTime());
			}
			uptime.get(h).setMark(false);
		}
	}

	public void check()
	{
		for (Map.Entry<ServerThread, Vector<ServerHandler>> m0 : handlers.entrySet())
		{
			long l = 0;
			boolean b = true;
			for (ServerHandler h : m0.getValue())
			{
				if (uptime.containsKey(h))
				{
					// alive time
					if (uptime.get(h).getAliveTime() > threshhold)
					{
						l += uptime.get(h).getActiveTime(threshhold);
					} else
					{
						b = false;
					}
				}
			}
			if ((double)l / (double)threshhold > 0.15d && b)
			{
				// reallocate half of the handlers to a new serverthread when
				// approximate active time is 15%
				// "overload"
				Server.getInstance().splitThread(m0.getKey());
			} else if ((double)l / (double)threshhold > 0.02 && b)
			{
				// close thread and move threads to other handlers when
				// approximate active time is 2%
				// unnecessary serverthread
				Server.getInstance().closeThread(m0.getKey());
			}
		}
	}

	/**
	 * 
	 * @return thread with lowest usage
	 */
	public ServerThread getThread() throws NoSuchElementException
	{
		double d = Integer.MAX_VALUE;
		for (Map.Entry<ServerThread, Double> m : ranking.entrySet())
		{
			if (m.getValue() < d)
			{
				d = m.getValue();
			}
		}

		for (Map.Entry<ServerThread, Double> m : ranking.entrySet())
		{
			if (m.getValue() == d)
				return m.getKey();
		}

		// return getThread(1);
		throw new NoSuchElementException();
	}

	/**
	 * just a loop protection
	 * 
	 * @param i+
	 * @return
	 */
	private ServerThread getThread(int i)
	{
		if (i > 3)
			throw new RuntimeException();
		double d = Integer.MAX_VALUE;
		for (Map.Entry<ServerThread, Double> m : ranking.entrySet())
		{
			if (m.getValue() < d)
				d = m.getValue();
		}

		for (Map.Entry<ServerThread, Double> m : ranking.entrySet())
		{
			if (m.getValue() == d)
				return m.getKey();
		}
		return getThread(i++);
	}

	private static class InfoContainer
	{
		private final boolean b;
		private final long l;

		public InfoContainer(boolean b, long l)
		{
			this.b = b;
			this.l = l;
		}

		public boolean getState()
		{
			return b;
		}

		public long getTime()
		{
			return l;
		}
	}

	public void printStuff()
	{
		// Logger.info("Printing stuff");
		// for(Map.Entry<ServerHandler, UptimeContainer> m : uptime.entrySet())
		// {
		// System.out.println(m.getValue().getActiveTime());
		// System.out.println(m.getValue().getnActiveTime());
		// }
	}

	public static Diagnostics getInstance()
	{
		return instance;
	}
}
