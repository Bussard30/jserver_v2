package de.bussard30.jserverv2.networking.convertionhandlers;

import java.security.InvalidParameterException;

public abstract class Serializer
{
	/**
	 * Converts an object to a string
	 * @param o the object to be converted
	 * @return return a string that can be converted back to an object using {@link Serializer#buildObject(String)}.
	 */
	public abstract String buildString(Object o) throws InvalidParameterException;
	
	/**
	 * Converts a string to an object
	 * @param s the string to be converted
	 * @return returns an object from a string built by {@link Serializer#buildString(Object)}.
	 */
	public abstract Object buildObject(String s) throws InvalidParameterException;
}
