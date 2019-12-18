package networking.server.protocol;

import java.util.HashMap;
import java.util.Map;

import networking.server.protocol.message.Header;
import networking.types.Packet;

public class Protocol
{
	// problem with current conditionwrapper system: logical operator in last
	// condition is redundant
	private HashMap<NetworkPhase, ConditionWrapper[]> conditions;

	// TODO
	private NetworkPhase currentNetworkPhase;

	// this were only implemented for faster execution speed
	private HashMap<Class<? extends Packet>, ConditionWrapper> map0;
	private HashMap<ConditionWrapper, NetworkPhase> map1;

	private Header header;

	public Protocol()
	{
		// to be used to subclasses for filling conditions hashmap
	}

	public HashMap<NetworkPhase, ConditionWrapper[]> getConditionsHashMap()
	{
		if (conditions != null)
		{
			return conditions;
		} else
		{
			return (conditions = new HashMap<>());
		}
	}

	public void sort()
	{
		map0 = new HashMap<>();
		map1 = new HashMap<>();
		for (Map.Entry<NetworkPhase, ConditionWrapper[]> m : conditions.entrySet())
		{
			for (ConditionWrapper c : m.getValue())
			{
				map1.put(c, m.getKey());
				for (Class<? extends Packet> p : c.getCondition().getPackets())
				{
					map0.put(p, c);
				}
			}
		}
	}

	public boolean pass(Packet p, PacketState ps)
	{
		if (map0.get(p.getClass()).getCondition().isConditionMet() != map0.get(p).getCondition().check(p, ps))
		{
			ConditionWrapper[] c = conditions.get(currentNetworkPhase);
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

	public String getHeader(Header h, Packet p)
	{
		return String.valueOf(p.getConvertedBuffer().length);
	}

}
