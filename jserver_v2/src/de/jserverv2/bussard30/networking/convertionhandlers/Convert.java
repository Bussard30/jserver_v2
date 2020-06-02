package de.jserverv2.bussard30.networking.convertionhandlers;

import java.security.InvalidParameterException;
import java.util.HashMap;

import de.jserverv2.bussard30.networking.types.ConvertionHandler;

public class Convert
{
	private static HashMap<Class<?>, ConvertionHandling> assignments;

	static
	{
		assignments = new HashMap<Class<?>, ConvertionHandling>();
	}

	public static void register(ConvertionHandling o) throws InvalidParameterException
	{
		try
		{
			if (!assignments.containsKey(o.getClass().getAnnotation(ConvertionHandler.class).target()))
			{
				assignments.put(o.getClass().getAnnotation(ConvertionHandler.class).target(), o);
			} else
			{
				throw new InvalidParameterException("ConvertionHandler for this type already registered!");
			}
		} catch (Throwable t)
		{
			throw new InvalidParameterException("Could not register convertion handler");
		}
	}

	/**
	 * Returns a string for an object that can be converted back to an object using {@link #getObject(Class<?>, String)}
	 * @param o Object to be formatted.
	 * @return String of formatted object.
	 * @throws InvalidParameterException
	 */
	public static final String getString(Object o) throws InvalidParameterException
	{
		return (o instanceof String) ? (String) o : assignments.get(o.getClass()).getString(o);
	}

	/**
	 * Return an object for a string of an formatted object (see {@link #getString(Object)}) and its class.
	 * @param c Expected class of object
	 * @param s Formatted string
	 * @return an object (which is an instance of c)
	 * @throws InvalidParameterException
	 */
	public static final Object getObject(Class<?> c, String s) throws InvalidParameterException
	{
		return (c.equals(String.class)) ? s : assignments.get(c).getObject(s);
	}
	
	/**
	 * Checks if c has been registered.
	 * @param c Class to be checked
	 * @return a boolean if c has been found.
	 */
	public static final boolean containsClass(Class<?> c)
	{
		return assignments.containsKey(c);
	}
}