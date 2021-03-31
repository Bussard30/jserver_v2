<<<<<<< HEAD
package networking.convertionhandlers;

import networking.types.ConvertionHandling;

import java.security.InvalidParameterException;

@ConvertionHandling(target = Long.class)
public class LongConverter extends ConvertionHandler {

    @Override
    public String getString(Object o) throws InvalidParameterException {
        if (o instanceof Long) {
            return Long.toString((Integer) o);
        }
        throw new InvalidParameterException();
    }

    @Override
    public Object getObject(String s) throws InvalidParameterException {
        try {
            return Long.parseLong(s);
        } catch (Throwable t) {
            throw new InvalidParameterException();
        }
    }
=======
package de.bussard30.jserverv2.java.networking.convertionhandlers;

import java.security.InvalidParameterException;

import de.bussard30.jserverv2.java.networking.types.ConvertionHandling;

@ConvertionHandling(target = Long.class)
public class LongConverter extends ConversionHandler
{

	@Override
	public String buildString(Object o) throws InvalidParameterException
	{
		if (o instanceof Long)
		{
			return Long.toString((Long) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object buildObject(String s) throws InvalidParameterException
	{
		try
		{
			return Long.parseLong(s);
		} catch (Throwable t)
		{
			throw new InvalidParameterException();
		}
	}
>>>>>>> develop

}
