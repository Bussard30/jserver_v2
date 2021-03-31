package de.bussard30.jserverv2.networking.server.protocol;

import java.util.HashMap;
import java.util.Map;

import de.bussard30.jserverv2.networking.types.Packet;

public class Condition
{

	private final HashMap<Class<? extends Packet>, Boolean> packages;
	private final Class<? extends Packet>[] p;
	private final boolean and;
	private boolean conditionMet;
	private final PacketState packetState;

	/**
	 * 
	 * @param packetState
	 *            0 = received, 1 = successful
	 * @param and
	 *            : if true Condition fulfilled when at least one of all package
	 *            types were received<br>
	 *            if not true Condition fulfilled when at least one package of
	 *            all package types were received
	 * @param p
	 */
	@SafeVarargs
	public Condition(PacketState packetState, boolean and, Class<? extends Packet>... p)
	{
		this.packetState = packetState;
		this.p = p;
		this.and = and;
		packages = new HashMap<>();
		for (Class<? extends Packet> aClass : p) {
			packages.put(aClass, false);
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
	public boolean check(Packet p, PacketState packetState)
	{
		if (packetState != this.packetState)
			return conditionMet;
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
			}
			if (and && m.getValue())
				b = true;
			else
				return false;
		}
		return (conditionMet = b);
	}

	public boolean isConditionMet()
	{
		return conditionMet;
	}

	public PacketState getPacketState()
	{
		return packetState;
	}

}
