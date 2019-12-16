package networking.server.protocol.example;

import java.util.HashMap;

import networking.server.protocol.Condition;
import networking.server.protocol.ConditionWrapper;
import networking.server.protocol.LogicalOperator;
import networking.server.protocol.NetworkPhase;
import networking.server.protocol.Protocol;
import networking.server.protocol.example.networkphases.Com;
import networking.server.protocol.example.networkphases.Pre0;
import networking.server.protocol.message.Header;
import networking.types.Packet;

public class BasicProtocol extends Protocol
{
	HashMap<NetworkPhase, ConditionWrapper[]> hm;

	public BasicProtocol()
	{
		hm = this.getConditionsHashMap();
		hm.put((NetworkPhase) new Pre0(), new ConditionWrapper[]
		{ new ConditionWrapper(new Condition(1, false, VersionPacket.class), LogicalOperator.none) });
		hm.put((NetworkPhase) new Com(), new ConditionWrapper[]
		{ new ConditionWrapper(new Condition(1, false, TerminationPacket.class), LogicalOperator.none) });
	}

	@Override
	public String getHeader(Header h, Packet p)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
