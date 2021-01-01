package de.bussard30.jserverv2.java.networking.server.protocol.session;

import de.bussard30.jserverv2.java.networking.server.protocol.NetworkPhase;

public abstract class Session
{
    private NetworkPhase phase;
    private int id;

    public Session(int id, NetworkPhase phase)
    {
        this.id = id;
        this.phase = phase;
    }

    /**
     * Gets called to reinstantiate session.
     */
    public void rebuild()
    {
        SessionInjector.inject(this);
    }

    public NetworkPhase getNetworkPhase()
    {
        return phase;
    }

    public void setNetworkPhase(NetworkPhase networkPhase)
    {
        this.phase = networkPhase;
    }
}