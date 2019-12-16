package networking.convertionhandlers;

import java.security.InvalidParameterException;

@networking.types.ConvertionHandler(target = Double.class)
public class DoubleConverter extends networking.convertionhandlers.ConvertionHandler
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
