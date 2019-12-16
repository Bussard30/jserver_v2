package networking.server;

public class ActiveTime
{
	private AdvancedArray<Long> markTimes;
	private AdvancedArray<Boolean> values;
	private long creationTime;

	private int index = 0;

	public ActiveTime()
	{
		markTimes = new AdvancedArray<>(Long.class, 100);
		values = new AdvancedArray<>(Boolean.class, 100);
		creationTime = System.currentTimeMillis();
	}

	public void setMark(boolean b)
	{
		markTimes.put(System.currentTimeMillis(), index);
		values.put(b, index);
		index++;
	}

	public void setMark(long time, boolean b)
	{
		markTimes.put(time, index);
		values.put(b, index);
		index++;
	}

	public void update(long threshhold)
	{
		while ((System.currentTimeMillis() - markTimes.get(0)) >= threshhold)
		{
			markTimes.removeFirst();
			values.removeFirst();
		}
	}

	public long getActiveTime(long threshhold)
	{
		long l = 0;
		l += markTimes.get(0) - threshhold;
		for (int i = 1; i < markTimes.length; i++)
		{
			if (!values.get(i))
				l += markTimes.get(i) - markTimes.get(i - 1);
		}
		return l;
	}
	
	public long getAliveTime()
	{
		return System.currentTimeMillis() - creationTime;
	}
}
