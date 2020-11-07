package de.jserverv2.bussard30.eventhandling;

import java.lang.reflect.Method;
import java.util.HashMap;

import de.jserverv2.bussard30.networking.server.protocol.NetworkPhase;
import de.jserverv2.bussard30.networking.types.Packet;

public class EventHandling
{

	private static HashMap<Class<?>, DataContainer> hm = new HashMap<>();

	/**
	 * Scans methods of object for following annotations: "@interface
	 * EventHandler, "@interface RequestHandler, "@interface ResponseHandler
	 * Does not throw an error if no method has been found. Automatically
	 * registers parameter as Event
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
				if (m.getParameterTypes().length <= 1)
				{
					hm.put(m.getParameterTypes()[0],
							new DataContainer(NetworkPhase.stringsToNetworkPhases(a.networkPhase()), m));
				}
			}
		}
	}
	
	public static void throwEvent(Object o)
	{
		
	}
	
	public static void handleRequest(Packet p)
	{
		
	}
	
	public static void handleResponse(Packet p)
	{
		
	}

	private static class DataContainer
	{
		public DataContainer(NetworkPhase[] np, Method m)
		{

		}
	}
}
