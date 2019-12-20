package networking.convertionhandlers;

import java.security.InvalidParameterException;
import java.util.Base64;

@networking.types.ConvertionHandler(target = Short.class)
public class ShortConverter extends networking.convertionhandlers.ConvertionHandler
{

	@Override
	public String getString(Object o) throws InvalidParameterException
	{
		if (o instanceof Short)
		{
			return Base64.getEncoder().encodeToString(new byte[]
			{ (byte) ((short) o & 0xff), (byte) (((short) o >> 8) & 0xff) });
		}
		throw new InvalidParameterException();
	}

	@Override
	public Object getObject(String s) throws InvalidParameterException
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
