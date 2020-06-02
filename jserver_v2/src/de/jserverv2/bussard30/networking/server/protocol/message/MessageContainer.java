package de.jserverv2.bussard30.networking.server.protocol.message;

import de.jserverv2.bussard30.networking.types.Packet;

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
