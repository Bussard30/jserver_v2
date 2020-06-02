package de.jserverv2.bussard30.networking.server.protocol.message;

import de.jserverv2.bussard30.networking.types.Packet;

public abstract class Header implements MessagePart
{
	public String getHeader(Packet p)
	{
		return "" + p.getConvertedBuffer().length + ";" + p.getNr() + ";" + p.getName();
	}
	
}
