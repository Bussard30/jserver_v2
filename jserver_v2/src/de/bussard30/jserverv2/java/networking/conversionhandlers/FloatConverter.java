package de.bussard30.jserverv2.java.networking.conversionhandlers;

import java.security.InvalidParameterException;

import de.bussard30.jserverv2.java.networking.types.ConvertionHandling;
@Deprecated
@ConvertionHandling(target = Float.class)
public class FloatConverter extends ConversionHandler
{

	@Override
	public String buildString(Object o) throws InvalidParameterException
	{
		if (o instanceof Float)
		{
			return Float.toString((Float) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object buildObject(String s) throws InvalidParameterException
	{
		try
		{
			return Float.parseFloat(s);
		} catch (Throwable t)
		{
			throw new InvalidParameterException();
		}
	}

}
