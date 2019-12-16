package networking.server.protocol.example;

import networking.types.Packet;

public class TerminationPacket extends Packet
{
	public TerminationPacket(String name, Object buffer, byte[] convertedBuffer)
	{
		super(name, buffer, convertedBuffer);
	}

	public TerminationPacket(String name, Object buffer, int nr, byte[] convertedBuffer)
	{
		super(name, buffer, nr, convertedBuffer);
	}
}
