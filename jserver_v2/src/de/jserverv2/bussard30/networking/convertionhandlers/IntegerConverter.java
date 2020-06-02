package de.jserverv2.bussard30.networking.convertionhandlers;

import java.security.InvalidParameterException;

import de.jserverv2.bussard30.networking.types.ConvertionHandler;

@ConvertionHandler(target = Integer.class)
public class IntegerConverter extends ConvertionHandling
{

	@Override
	public String getString(Object o) throws InvalidParameterException
	{
		if (o instanceof Integer)
		{
			return Integer.toString((Integer) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object getObject(String s) throws InvalidParameterException
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
