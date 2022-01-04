package de.bussard30.jserverv2.java.networking.conversionhandlers;

import java.security.InvalidParameterException;

import de.bussard30.jserverv2.java.networking.types.ConvertionHandling;
@Deprecated
@ConvertionHandling(target = Integer.class)
public class IntegerConverter extends ConversionHandler
{

	@Override
	public String buildString(Object o) throws InvalidParameterException
	{
		if (o instanceof Integer)
		{
			return Integer.toString((Integer) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object buildObject(String s) throws InvalidParameterException
	{
		try
		{
			return Integer.parseInt(s);
		} catch (Throwable t)
		{
			throw new InvalidParameterException();
		}
	}

}
