package de.jserverv2.bussard30.threading.types;

public enum ThreadPriority
{
	LOW(0),MID(1),HIGH(2);
	
	private int priority;
	
	ThreadPriority(int i)
	{
		this.priority = i;
	}
	
	public int getPriority()
	{
		return priority;
	}
}
