package networking.convertionhandlers;

import java.security.InvalidParameterException;

@networking.types.ConvertionHandler(target = Float.class)
public class FloatConverter extends networking.convertionhandlers.ConvertionHandler
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
