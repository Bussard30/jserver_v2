package networking.server.protocol.message;

import networking.types.Packet;

public abstract class Header implements MessagePart
{
	public String getHeader(Packet p)
	{
		return "" + p.getConvertedBuffer().length + ";" + p.getNr() + ";" + p.getName();
	}
	
}
