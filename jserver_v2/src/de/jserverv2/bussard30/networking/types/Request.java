package de.jserverv2.bussard30.networking.types;

import de.jserverv2.bussard30.networking.server.protocol.message.Message;

/**
 * 
 * @author Bussard30
 *
 */
public interface Request 
{

	public static String identifier = "req";
	
	public default String getPacketIdentifier()
	{
		return identifier;
	}
	
	/**
	 * 
	 * @param request Request that can be processed.
	 */
	public void onRequest(Packet request);

	public Message getRequestMessage();
}