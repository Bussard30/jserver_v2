package networking.convertionhandlers;

import java.security.InvalidParameterException;

@networking.types.ConvertionHandler(target = Integer.class)
public class IntegerConverter extends networking.convertionhandlers.ConvertionHandler
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
