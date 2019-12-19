package networking.server.protocol.example.message;

import networking.server.protocol.message.Body;
import networking.server.protocol.message.MessageContainer;
import networking.types.Packet;

public class MessageBody extends Body
{

	@Override
	public String getString(Packet p)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageContainer getPacket(String[] s, Packet p)
	{
		return new MessageContainer(s, p);
	}
}
