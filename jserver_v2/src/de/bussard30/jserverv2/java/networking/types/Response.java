package de.bussard30.jserverv2.java.networking.types;

import de.bussard30.jserverv2.java.networking.server.protocol.message.Message;

/**
 * @author Bussard30
 *
 */
public interface Response extends Packet
{
	
	String identifier = "res";


	default String getPacketIdentifier()
	{
		return identifier;
	}
	
	public Message getResponseMessage();
}
