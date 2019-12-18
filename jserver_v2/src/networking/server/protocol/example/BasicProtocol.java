package networking.server.protocol.example;

import java.util.HashMap;

import networking.server.protocol.Condition;
import networking.server.protocol.ConditionWrapper;
import networking.server.protocol.LogicalOperator;
import networking.server.protocol.NetworkPhase;
import networking.server.protocol.Post;
import networking.server.protocol.Protocol;
import networking.server.protocol.ProtocolCondition;
import networking.server.protocol.example.networkphases.Com;
import networking.server.protocol.example.networkphases.Pre0;
import networking.server.protocol.example.packetstates.PacketSuccess;
import networking.server.protocol.message.Header;
import networking.types.Packet;

public class BasicProtocol extends Protocol
{
	HashMap<NetworkPhase, ProtocolCondition> hm;

	public BasicProtocol()
	{
		hm = this.getConditionsHashMap();

		hm.put((NetworkPhase) new Pre0(), new ProtocolCondition(Com.com, new ConditionWrapper[]
		{ new ConditionWrapper(new Condition(PacketSuccess.packetSuccess, false, VersionPacket.class),
				LogicalOperator.none) }));
		hm.put((NetworkPhase) new Com(), new ProtocolCondition(Post.postPhase, new ConditionWrapper[]
		{ new ConditionWrapper(new Condition(PacketSuccess.packetSuccess, false, TerminationPacket.class),
				LogicalOperator.none) }));
	}

	@Override
	public String getHeader(Header h, Packet p)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
