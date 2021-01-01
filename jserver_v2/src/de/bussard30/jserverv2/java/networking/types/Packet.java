package de.bussard30.jserverv2.java.networking.types;


import de.bussard30.jserverv2.java.networking.server.protocol.message.Body;
import de.bussard30.jserverv2.java.networking.server.protocol.message.Header;
import de.bussard30.jserverv2.java.networking.server.protocol.message.Message;

public abstract class Packet
{
	private Message m;

	public abstract String getPacketIdentifier();
	public abstract Object[] getObjects();
	public abstract void load(Object[] content);
	public abstract Body getBody();

	/**
	 * Builds message.
	 */
	public void build()
	{
		m = getMessage();
	}

	/**
	 * Only executable after calling {@link Packet#build()}
	 * @return length of message in bytes
	 */
	public int length()
	{
		return m.getLength();
	}

	/**
	 * Only executable after calling {@link Packet#build()}
	 * @return length of message in bytes
	 */
	public Message getMessage()
	{
		return new Message(this);
	}
}
