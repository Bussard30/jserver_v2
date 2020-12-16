package de.bussard30.jserverv2.java.networking.server.protocol.message;

import de.bussard30.jserverv2.java.networking.types.Packet;

/**
 * Message is more or less a container for several objects inside of a request/ a response
 * TODO needs some restructuring though, is packet even necessary anymore????
 * @author Jonas
 *
 */
public class Message
{
	private MessagePart[] messageParts;

	private char seperator = ';';

	/**
	 * Order in array matters.
	 * 
	 * @param s
	 */
	public Message(MessagePart[] m)
	{
		this.messageParts = m;
	}
	
	public Message(Object[] o)
	{
		
	}

	public Message(MessagePart[] m, char seperator)
	{
		this(m);
		this.seperator = seperator;
	}

	public String constructMessage(Packet p)
	{
		String temp = new String();
		for (int i = 0; i < messageParts.length; i++)
		{
			temp += messageParts[i].getString(p);
		}
		return temp;
	}

	public Packet getPacket(String s)
	{
		String[] parts = s.split(String.valueOf(seperator));
		MessageContainer mc = new MessageContainer(parts, null);
		for (int i = 0; i < messageParts.length; i++)
		{
			mc = messageParts[i].getPacket(mc.getStrings(), mc.getPacket());
		}
		return mc.getPacket();
	}
}
