package de.bussard30.jserverv2.networking.server.protocol.session;

import de.bussard30.jserverv2.networking.server.protocol.NetworkPhase;
import de.bussard30.jserverv2.networking.server.protocol.Protocol;

import java.util.UUID;
public abstract class Session
{
    private NetworkPhase phase;
    private final UUID id;
    private boolean isDead;
    private boolean isSleeping;

    public Session(UUID id, NetworkPhase phase)
    {
        this.id = id;
        this.phase = phase;
    }

    /**
     * Gets called to reinstantiate session.
     */
    public NetworkPhase getNetworkPhase() {
        return phase;
    }

    public void setNetworkPhase(NetworkPhase networkPhase) {
        this.phase = networkPhase;
    }

    public abstract Protocol getProtocol();

    public UUID getId() {
        return id;
    }

    public boolean isSleeping() {
        return isSleeping;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setIsSleeping(boolean isSleeping) {
        this.isSleeping = isSleeping;
    }
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
    public void rebuild()
    {
        SessionInjector.inject(this);
    }
}