package de.jserverv2.bussard30.networking.convertionhandlers;

import java.security.InvalidParameterException;

import de.jserverv2.bussard30.networking.types.ConvertionHandler;

@ConvertionHandler(target = Double.class)
public class DoubleConverter extends ConvertionHandling
{

	@Override
	public String getString(Object o) throws InvalidParameterException
	{
		if (o instanceof Double)
		{
			return Double.toString((Double) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object getObject(String s) throws InvalidParameterException
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
