package de.bussard30.jserverv2.java.eventhandling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import de.bussard30.jserverv2.java.networking.logger.Logger;
import de.bussard30.jserverv2.java.networking.server.protocol.NetworkPhase;
import de.bussard30.jserverv2.java.networking.types.Packet;

public class EventHandling
{

	private static HashMap<Class<?>, DataContainer> hm = new HashMap<>();

	/**
	 * Scans methods of object for following annotations: "@interface<br>
	 * EventHandler, "@interface RequestHandler, "@interface ResponseHandler<br>
	 * Does not throw an error if no method has been found.Does throw an
	 * error<br>
	 * if a method that is handling the exact same condition is found.<br>
	 * Automatically registers parameter as Event<br>
	 * 
	 * @param o
	 */
	public static void addHandler(Object o)
	{
		for (Method m : o.getClass().getMethods())
		{
			EventHandler a = m.getAnnotation(EventHandler.class);
			if (a != null)
			{
				synchronized (hm)
				{
					for (Map.Entry<Class<?>, DataContainer> e : hm.entrySet())
					{
						if (e.getKey().equals(o.getClass()))
						{
							for (NetworkPhase np : e.getValue().getNetworkPhases())
							{
								if (np.equals(NetworkPhase.stringsToNetworkPhases(a.networkPhase())))
								{
									throw new RuntimeException("Method already present!");
								}
							}
						}
					}
				}
				if (m.getParameterTypes().length <= 1)
				{
					hm.put(m.getParameterTypes()[0],
							new DataContainer(NetworkPhase.stringsToNetworkPhases(a.networkPhase()), m));
				}
			}
		}
	}

	/**
	 * Due to different event handling methods being available for different
	 * network phases,<br>
	 * you have to give the current network phase for differentiation
	 * purposes.<br>
	 * Cannot call multiple event handle methods
	 * 
	 * @param o
	 * @param n
	 */
	public static void throwEvent(Object o, NetworkPhase n)
	{
		synchronized (hm)
		{
			for (Map.Entry<Class<?>, DataContainer> e : hm.entrySet())
			{
				if (e.getKey().equals(o.getClass()))
				{
					for (NetworkPhase np : e.getValue().getNetworkPhases())
					{
						if (np.equals(n))
						{
							try
							{
								e.getValue().getMethod().invoke(null, o);
								return;
							} catch (IllegalAccessException e1)
							{
								Logger.error("EventHandling", e1);
							} catch (IllegalArgumentException e1)
							{
								Logger.error("EventHandling", e1);
							} catch (InvocationTargetException e1)
							{
								Logger.error("EventHandling", e1);
							}
						}
					}
				}
			}
		}
	}

	public static void handleRequest(Packet p)
	{

	}

	public static void handleResponse(Packet p)
	{

	}

	private static class DataContainer
	{
		private NetworkPhase[] np;
		private Method m;

		public DataContainer(NetworkPhase[] np, Method m)
		{
			this.np = np;
			this.m = m;
		}

		public NetworkPhase[] getNetworkPhases()
		{
			return np;
		}

		public Method getMethod()
		{
			return m;
		}
	}
}
