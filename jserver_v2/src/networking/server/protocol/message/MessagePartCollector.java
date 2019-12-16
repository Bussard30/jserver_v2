package networking.server.protocol.message;

import java.util.Vector;

public class MessagePartCollector
{
	private static Vector<MessagePart> messageParts = new Vector<>();

	public static void register(MessagePart m)
	{
		messageParts.add(m);
	}

	public Object[] getMessagePartsArray()
	{
		return messageParts.toArray();
	}

	public Vector<MessagePart> getMessagePartsVector()
	{
		return messageParts;
	}
}
