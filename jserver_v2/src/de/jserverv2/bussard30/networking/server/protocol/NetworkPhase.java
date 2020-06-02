package de.jserverv2.bussard30.networking.server.protocol;

public interface NetworkPhase
{

	public default String getName()
	{
		return this.getClass().getTypeName();
	}
}
