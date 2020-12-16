package de.bussard30.jserverv2.java.networking.types;

// TODO requires rewrite
@Deprecated
public abstract class Packet
{
	protected int nr;
	protected Object buffer;
	protected String name;
	protected byte[] convertedBuffer;

	/**
	 * sets nr to -1
	 * 
	 * @param name
	 * @param buffer
	 * @param convertedBuffer
	 */
	public Packet(String name, Object buffer, byte[] convertedBuffer)
	{
		this.buffer = buffer;
		this.name = name;
		this.nr = -1;
		this.convertedBuffer = convertedBuffer;
	}

	public Packet(String name, Object buffer, int nr, byte[] convertedBuffer)
	{
		this.buffer = buffer;
		this.nr = nr;
		this.name = name;
		this.convertedBuffer = convertedBuffer;
	}

	public void setNr(int nr)
	{
		this.nr = nr;
	}

	public Object getBuffer()
	{
		return buffer;
	}

	public int getNr()
	{
		return nr;
	}

	public String getName()
	{
		return name;
	}

	public byte[] getConvertedBuffer()
	{
		return convertedBuffer;
	}

	// packet identifier in actual packet sent to other client, for example
	// "res" for response
	/**
	 * 
	 * @return unique packet identifier
	 */
	public String getPacketIdentifier()
	{
		return "";
	}
}
