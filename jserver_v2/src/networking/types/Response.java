package networking.types;

import java.io.Serializable;

/**
 * This class contains the number of the response,<br>
 * which is actually not in use right now,<br>
 * the Object which is being buffered,<br>
 * and the name of the response (see {@link Responses})
 * 
 * @author Bussard30
 *
 */
public class Response extends Packet implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -608768784249987736L;

	public Response(String name, Object buffer, byte[] convertedBuffer)
	{
		super(name, buffer, -1, convertedBuffer);
	}

	public Response(String name, Object buffer, int nr, byte[] convertedBuffer)
	{
		super(name, buffer, nr, convertedBuffer);
	}

}
