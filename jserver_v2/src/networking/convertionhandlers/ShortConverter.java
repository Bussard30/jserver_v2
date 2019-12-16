package networking.convertionhandlers;

import java.security.InvalidParameterException;

@networking.types.ConvertionHandler(target = Short.class)
public class ShortConverter extends networking.convertionhandlers.ConvertionHandler
{

	@Override
	public String getString(Object o) throws InvalidParameterException
	{
		if (o instanceof Short)
		{
			return Short.toString((Short) o);
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object getObject(String s) throws InvalidParameterException
	{
		try
		{
			return Short.parseShort(s);
		} catch (Throwable t)
		{
			throw new InvalidParameterException();
		}
	}

}
