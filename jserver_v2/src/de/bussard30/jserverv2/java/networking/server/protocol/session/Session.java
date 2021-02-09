package de.bussard30.jserverv2.java.networking.server.protocol.session;

import de.bussard30.jserverv2.java.networking.server.protocol.NetworkPhase;
<<<<<<< HEAD
import de.bussard30.jserverv2.java.networking.server.protocol.Protocol;

import java.util.UUID;

public abstract class Session {
    private NetworkPhase phase;
    private UUID id;

    private boolean isSleeping = false;
    private boolean isDead = false;

    public Session(UUID id, NetworkPhase phase) {
=======

public abstract class Session
{
    private NetworkPhase phase;
    private int id;

    public Session(int id, NetworkPhase phase)
    {
>>>>>>> origin/develop
        this.id = id;
        this.phase = phase;
    }

    /**
     * Gets called to reinstantiate session.
     */
<<<<<<< HEAD
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
    public void setIsDead(boolean isDead)
    {
        this.isDead = isDead;
=======
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
>>>>>>> origin/develop
    }
}