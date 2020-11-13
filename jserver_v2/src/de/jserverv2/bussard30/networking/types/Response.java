package de.jserverv2.bussard30.networking.types;

import de.jserverv2.bussard30.networking.server.protocol.message.Message;

/**
 * @author Bussard30
 *
 */
public interface Response 
{
	
	public static String identifier = "res";


	public default String getPacketIdentifier()
	{
		return identifier;
	}
	
	public Message getResponseMessage();
}
