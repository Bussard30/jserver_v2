package networking.server.protocol;

import java.util.HashMap;
import java.util.Map;

import networking.types.Packet;

public class Condition
{

	private HashMap<Class<? extends Packet>, Boolean> packages;
	private Class<? extends Packet>[] p;
	private boolean and;
	private boolean conditionMet;
	private int specification;

	/**
	 * 
	 * @param specification
	 *            0 = received, 1 = successful
	 * @param and
	 *            : if true Condition fulfilled when at least one of all package
	 *            types were received<br>
	 *            if not true Condition fulfilled when at least one package of
	 *            all package types were received
	 * @param p
	 */
	@SafeVarargs
	public Condition(int specification, boolean and, Class<? extends Packet>... p)
	{
		this.specification = specification;
		this.p = p;
		this.and = and;
		packages = new HashMap<>();
		for (int i = 0; i < p.length; i++)
		{
			packages.put(p[i], false);
		}
	}

	public Class<? extends Packet>[] getPackets()
	{
		return p;
	}

	/**
	 * this method requires the packet to be one of the in the constructor
	 * specified ones, this method doesnt check anything
	 * 
	 * @param p
	 * @return
	 */
	public boolean check(Packet p)
	{
		packages.put(p.getClass(), true);
		if (!and)
		{
			conditionMet = true;
			return true;
		}
		boolean b = false;
		boolean firstTime = true;
		for (Map.Entry<Class<? extends Packet>, Boolean> m : packages.entrySet())
		{
			if (firstTime)
			{
				firstTime = false;
				if (and && m.getValue())
					b = true;
				else
					return false;
			} else
			{
				if (and && m.getValue())
					b = true;
				else
					return false;
			}
		}
		return (conditionMet = b);
	}

	public boolean isConditionMet()
	{
		return conditionMet;
	}

}
