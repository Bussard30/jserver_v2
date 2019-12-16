package networking.server.protocol.message;

import networking.types.Packet;

public class MessageContainer
{
	private String[] s;
	private Packet p;

	public MessageContainer(String[] s, Packet p)
	{
		this.s = s;
		this.p = p;
	}

	public String[] getStrings()
	{
		return s;
	}

	public Packet getPacket()
	{
		return p;
	}

}
