package de.jserverv2.bussard30.networking.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

/**
 * TODO new logger system required; make logger create files for every single ip
 * and process
 * 
 * @author Unknown
 *
 */
public class Logger
{
	private static SimpleDateFormat sdf;
	public static Vector<LogWrapper> queue;

	public static LogWrapper fetchMessage()
	{
		synchronized (queue)
		{
			return queue.remove(0);
		}
	}

	static
	{
		sdf = new SimpleDateFormat("HH:mm:ss:SSS");
	}

	public static void info(String s)
	{
		System.out.println("[" + getTime() + "]" + "[INFO]" + ((s.length() > 100) ? s.substring(0, 99) : s));
	}

	public static void info(String source, String info)
	{
		print("INFO", source, info);
	}

	protected static String getTime()
	{
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static void error(Throwable t)
	{
		print("ERROR", t.getMessage());
	}

	public static void error(String source, Throwable error)
	{
		print("ERROR", source, error.getMessage());
	}

	private static void print(String prefix, String s)
	{
		System.out.println("[" + getTime() + "]" + "[" + prefix + "]" + ((s.length() > 100) ? s.substring(0, 99) : s));
	}

	private static void print(String prefix, String source, String s)
	{
		System.out.println("[" + getTime() + "]" + "[" + prefix + "]" + "[" + source + "]"
				+ ((s.length() > 100) ? s.substring(0, 99) : s));
	}
}
