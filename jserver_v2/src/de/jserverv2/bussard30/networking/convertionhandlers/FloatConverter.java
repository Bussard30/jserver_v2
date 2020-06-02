package de.jserverv2.bussard30.networking.convertionhandlers;

import java.security.InvalidParameterException;

import de.jserverv2.bussard30.networking.types.ConvertionHandler;

@ConvertionHandler(target = Float.class)
public class FloatConverter extends ConvertionHandling
{

	@Override
	public String getString(Object o) throws InvalidParameterException
	{
		if (o instanceof Float)
		{
			return Float.toString((Float) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object getObject(String s) throws InvalidParameterException
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