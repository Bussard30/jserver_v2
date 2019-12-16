package networking.server.protocol;

public interface NetworkPhase
{

	public default String getName()
	{
		return this.getClass().getTypeName();
	}
}
