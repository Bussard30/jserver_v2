package networking.server.protocol.example;

import networking.types.Packet;

public class VersionPacket extends Packet
{

	public VersionPacket(String name, Object buffer, byte[] convertedBuffer)
	{
		super(name, buffer, convertedBuffer);
	}

	public VersionPacket(String name, Object buffer, int nr, byte[] convertedBuffer)
	{
		super(name, buffer, nr, convertedBuffer);
	}

}
