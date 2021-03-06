package de.bussard30.jserverv2.java.networking.server.protocol;

import java.util.HashMap;
import java.util.Map;

import de.bussard30.jserverv2.java.networking.server.protocol.message.Header;
import de.bussard30.jserverv2.java.networking.types.Packet;

public class Protocol
{
	// problem with current conditionwrapper system: logical operator in last
	// condition is redundant
	// prob fine though
	private HashMap<NetworkPhase, ProtocolCondition> conditions;

	// TODO
	private NetworkPhase currentNetworkPhase;

	// indexing maps
	private HashMap<Class<? extends Packet>, ConditionWrapper> map0;
	private HashMap<ConditionWrapper, NetworkPhase> map1;

	private Header header;

	public Protocol()
	{
		// to be used to subclasses for filling conditions hashmap
	}

	public HashMap<NetworkPhase, ProtocolCondition> getConditionsHashMap()
	{
		if (conditions != null)
		{
			return conditions;
		} else
		{
			return (conditions = new HashMap<>());
		}
	}

	/**
	 * Creates new mappings of conditions for faster future processing speed
	 */
	public void sort()
	{
		map0 = new HashMap<>();
		map1 = new HashMap<>();
		for (Map.Entry<NetworkPhase, ProtocolCondition> m : conditions.entrySet())
		{
			for (ConditionWrapper c : m.getValue().getConditions())
			{
				map1.put(c, m.getKey());
				for (Class<? extends Packet> p : c.getCondition().getPackets())
				{
					map0.put(p, c);
				}
			}
		}
	}

	/**
	 * Checks if the packet triggers progresses to another network phase
	 * 
	 * @param p
	 * @return
	 */
	public boolean pass(Packet p, PacketState ps)
	{
		if (map0.get(p.getClass()).getCondition().isConditionMet() != map0.get(p).getCondition().check(p, ps))
		{
			ConditionWrapper[] c = conditions.get(currentNetworkPhase).getConditions();
			boolean b = false;
			b = c[0].getLogicalOperator().compare(c[0].getCondition().isConditionMet(),
					c[1].getCondition().isConditionMet());
			for (int i = 1; i < c.length - 1; i++)
			{
				b = c[i].getLogicalOperator().compare(b, c[i + 1].getCondition().isConditionMet());
			}
			return b;
		}
		return false;
	}

	public void setHeader(Header h)
	{
		this.header = h;
	}

	public ProtocolHeader getProtocolHeader() {
		return null;
	}
}
