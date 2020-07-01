package de.jserverv2.bussard30.networking.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFileChooser;

public class LoggerThread
{
	private Thread t;
	private HashMap<Object, File> assignments;
	private static String docPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
	private boolean running;

	public LoggerThread()
	{
		assignments = new HashMap<>();
		t = new Thread(() ->
		{
			while(running)
			{
				LogWrapper lw = Logger.fetchMessage();
				System.out.println(lw.getMessage());

				if (!assignments.containsKey(lw.getCaster()))
				{
					assignments.put(lw.getCaster(), new File(docPath + "\\" + lw.getCaster().toString()));
				}

				try
				{
					FileWriter myWriter = new FileWriter(assignments.get(lw.getCaster()), true);
					myWriter.write(lw.getMessage());
					myWriter.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
