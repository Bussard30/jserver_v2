<<<<<<< HEAD
package networking.types;


import networking.convertionhandlers.Convert;

public class Wrapper {
    private final Object[] objects;

    public Wrapper(Object[] objects) {
        this.objects = objects;
    }

    public static Object[] getObjects(String[] s) {
        throw new RuntimeException();
    }

    public String getString() {
        // ????
        String[] tmp = new String[objects.length];
        int i = 0;
        for (Object o : objects) {
            if (!(o instanceof String)) {
                tmp[i] = Convert.getString(o);
                i++;
            } else {
                tmp[i] = (String) o;
            }
        }
        throw new RuntimeException();
    }
=======
package de.bussard30.jserverv2.java.networking.types;

import de.bussard30.jserverv2.java.networking.convertionhandlers.Convert;

public class Wrapper
{
	private final Object[] objects;
	public Wrapper(Object[] objects)
	{
		this.objects = objects;
	}

	/**
	 * Converts specified objecs in constructor to one string.
	 * @return string that be converted to an object array
	 */
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

	/**
	 *
	 * @param objects
	 * @return
	 */
	public static String getString(Object[] objects)
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

	/**
	 * Converts string array to object array.
	 * @param s string array
	 * @return object array
	 */
	public static Object[] getObjects(String[] s)
	{
		throw new RuntimeException();
	}
>>>>>>> develop
}