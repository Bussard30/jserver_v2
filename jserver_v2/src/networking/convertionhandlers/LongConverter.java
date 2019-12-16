package networking.convertionhandlers;

import java.security.InvalidParameterException;

@networking.types.ConvertionHandler(target = Long.class)
public class LongConverter extends networking.convertionhandlers.ConvertionHandler
{

	@Override
	public String getString(Object o) throws InvalidParameterException
	{
		if (o instanceof Long)
		{
			return Long.toString((Integer) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object getObject(String s) throws InvalidParameterException
	{
		try
		{
			return Long.parseLong(s);
		} catch (Throwable t)
		{
			throw new InvalidParameterException();
		}
	}

}
