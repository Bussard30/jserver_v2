package de.jserverv2.bussard30.networking.server.protocol.message;

import java.util.Base64;

import de.jserverv2.bussard30.networking.types.Packet;

public abstract class Body implements MessagePart
{

	public String getBody(Packet p)
	{
		return "" + Base64.getEncoder().encodeToString(p.getConvertedBuffer());
	}
}
