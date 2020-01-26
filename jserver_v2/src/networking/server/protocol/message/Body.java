package networking.server.protocol.message;

import java.util.Base64;

import networking.types.Packet;

public abstract class Body implements MessagePart
{
	/**
	 * Example function that can be used to transmit messages.
	 * @param p
	 * @return
	 */
	public String getBody(Packet p)
	{
		return "" + Base64.getEncoder().encodeToString(p.getConvertedBuffer());
	}
}
