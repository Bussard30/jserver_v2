package de.bussard30.jserverv2.java.networking.convertionhandlers;

import java.security.InvalidParameterException;

import de.bussard30.jserverv2.java.networking.types.ConvertionHandling;

@ConvertionHandling(target = Double.class)
public class DoubleConverter extends ConversionHandler
{

	@Override
	public String buildString(Object o) throws InvalidParameterException
	{
		if (o instanceof Double)
		{
			return Double.toString((Double) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object buildObject(String s) throws InvalidParameterException
	{
		try
		{
			return Double.parseDouble(s);
		} catch (Throwable t)
		{
			throw new InvalidParameterException();
		}
	}

}
