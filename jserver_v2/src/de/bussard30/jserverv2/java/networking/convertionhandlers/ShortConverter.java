package de.bussard30.jserverv2.java.networking.convertionhandlers;

import java.security.InvalidParameterException;
import java.util.Base64;

import de.bussard30.jserverv2.java.networking.types.ConvertionHandling;

@ConvertionHandling(target = Short.class)
public class ShortConverter extends ConversionHandler
{

	@Override
	public String buildString(Object o) throws InvalidParameterException
	{
		if (o instanceof Short)
		{
			return Base64.getEncoder().encodeToString(new byte[]
			{ (byte) ((short) o & 0xff), (byte) (((short) o >> 8) & 0xff) });
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object buildObject(String s) throws InvalidParameterException
	{
		try
		{
			byte[] b = Base64.getDecoder().decode(s);
			return ((b[1] & 0xFF) << 8) | (b[0] & 0xFF);
		} catch (Throwable t)
		{
			throw new InvalidParameterException();
		}
	}

}
