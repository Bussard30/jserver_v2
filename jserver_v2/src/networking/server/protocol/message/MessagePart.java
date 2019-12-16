package networking.server.protocol.message;

import networking.types.Packet;

public interface MessagePart
{
	public String getString(Packet p);

	/**
	 * The default method just returns the same MessageContainer<br>
	 * This method interprets incoming messages.
	 * 
	 * @param s
	 *            from previous MessagePart
	 * @param p
	 *            from previous MessagePart
	 * @return MessageContainer for, if there is one, next MessagePart
	 */
	public default MessageContainer getPacket(String[] s, Packet p)
	{
		return new MessageContainer(s, p);
	}
}
