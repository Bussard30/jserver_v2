package networking.server.protocol.example;

import networking.server.protocol.message.Message;
import networking.types.Packet;
import networking.types.Request;
import networking.types.Response;

public class TerminationPacket extends Packet implements Request, Response
{
	public TerminationPacket(String name, Object buffer, byte[] convertedBuffer)
	{
		super(name, buffer, convertedBuffer);
	}

	public TerminationPacket(String name, Object buffer, int nr, byte[] convertedBuffer)
	{
		super(name, buffer, nr, convertedBuffer);
	}

	@Override
	public void onRequest(Packet p)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(Packet response, Packet request)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Message getResponseMessage()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message getRequestMessage()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
