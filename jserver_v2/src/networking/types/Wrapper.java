package networking.types;

import networking.convertionhandlers.Convert;

public class Wrapper
{
	private Object[] objects;
	public Wrapper(Object[] objects)
	{
		this.objects = objects;
	}
	
	public String getString()
	{
		String[] tmp = new String[objects.length];
		int i = 0;
		for(Object o : objects)
		{
			if(!(o instanceof String))
			{
				tmp[i] = Convert.getString(o);
				i++;
			}
			else
			{
				tmp[i] = (String) o;
			}
		}
		throw new RuntimeException();
	}
	
	public static Object[] getObjects(String[] s)
	{
		throw new RuntimeException();
	}
}