package de.jserverv2.bussard30.networking.convertionhandlers;

import java.security.InvalidParameterException;

public abstract class ConvertionHandling
{
	/**
	 * Converts an object to a string
	 * @param o the object to be converted
	 * @return
	 */
	public abstract String getString(Object o) throws InvalidParameterException;
	
	/**
	 * Converts a string to an object
	 * @param s the string to be converted
	 * @return
	 */
	public abstract Object getObject(String s) throws InvalidParameterException;
}