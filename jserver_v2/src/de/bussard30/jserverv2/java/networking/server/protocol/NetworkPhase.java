<<<<<<< HEAD
package networking.server.protocol;

public interface NetworkPhase {

    default String getName() {
        return this.getClass().getTypeName();
    }
=======
package de.bussard30.jserverv2.java.networking.server.protocol;

import java.util.HashMap;
import java.util.Vector;

public interface NetworkPhase
{

	default String getName()
	{

		return this.getClass().getTypeName();
	}

	Vector<NetworkPhase> registeredNetworkPhases = new Vector<>();
	HashMap<String, NetworkPhase> convertionHash = new HashMap<>();

	static void registerNetworkPhase(NetworkPhase np)
	{
		registeredNetworkPhases.add(np);
		synchronized (convertionHash)
		{
			convertionHash.put(np.getName(), np);
		}
	}

	/**
	 * strings have to be case sensitive!
	 * 
	 * @param s
	 * @return
	 */
	static NetworkPhase[] stringsToNetworkPhases(String[] s)
	{
		NetworkPhase[] temp = new NetworkPhase[s.length];
		for (int i = 0; i < s.length; i++)
		{
			NetworkPhase np = null;
			synchronized (convertionHash)
			{
				np = convertionHash.get(s[i]);
			}
			if (np != null)
			{
				temp[i] = np;
			}
		}
		return temp;
	}
>>>>>>> develop
}
