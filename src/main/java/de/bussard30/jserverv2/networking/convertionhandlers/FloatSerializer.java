package de.bussard30.jserverv2.networking.convertionhandlers;

import java.security.InvalidParameterException;

import de.bussard30.jserverv2.networking.types.ConvertionHandling;

@ConvertionHandling(target = Float.class)
public class FloatSerializer extends Serializer
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
