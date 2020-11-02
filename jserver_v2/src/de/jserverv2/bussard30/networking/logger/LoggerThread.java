package de.jserverv2.bussard30.networking.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JFileChooser;

public class LoggerThread
{
	private Thread t;
	private HashMap<Object, File> assignments0;
	private HashMap<String, File> assignments1;
	private static String docPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\jserver_data";
	private boolean running;

	public static SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy-");

	public LoggerThread()
	{
		assignments0 = new HashMap<Object, File>();
		t = new Thread(() ->
		{
			while (running)
			{
				LogWrapper lw = null;
				try
				{
					lw = Logger.fetchMessage();
				} catch (Throwable t)
				{
					try
					{
						Thread.sleep(10);
					} catch (InterruptedException e)
					{
						Logger.error(this, e);
					}
					continue;
				}
				System.out.println(lw.getMessage());

				if (!assignments0.containsKey(lw.getCaster()))
				{
					File f = new File(docPath + "\\" + getDate() + lw.getCaster().toString() + ".log");
					try
					{
						f.createNewFile();
					} catch (IOException e)
					{
						synchronized (Logger.queue)
						{
							Logger.queue.add(lw);
						}
						e.printStackTrace();
					}
					assignments0.put(lw.getCaster(), f);
				}
				if (lw.getIP() != null)
				{
					if (!assignments1.containsKey(lw.getIP()))
					{
						assignments1.put(lw.getIP(),
								new File(docPath + "\\" + "ips" + "\\" + getDate() + lw.getIP() + ".log"));
					}

					try
					{
						FileWriter myWriter = new FileWriter(assignments1.get(lw.getIP()), true);
						myWriter.write(getFinalMessage(lw));
						myWriter.close();
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}

				try
				{
					FileWriter myWriter = new FileWriter(assignments0.get(lw.getCaster()), true);
					myWriter.write(getFinalMessage(lw));
					myWriter.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public void start()
	{
		t.start();
	}

	private static String getFinalMessage(LogWrapper lw)
	{
		return "[" + lw.getType() + "]" + "[" + lw.getTime() + "]" + "[" + lw.getCaster().toString() + "]"
				+ ((lw.getIP() == null) ? "" : ("[" + lw.getCaster().toString() + "]"))
				+ ((lw.getMessage().length() > 100) ? lw.getMessage().substring(0, 99) : lw.getMessage());
	}

	public String getDate()
	{
		return date.format(Calendar.getInstance().getTime());
	}

}
