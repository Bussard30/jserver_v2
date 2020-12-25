package de.bussard30.jserverv2.java.eventhandling;

import de.bussard30.jserverv2.java.eventhandling.events.Listener;
import de.bussard30.jserverv2.java.networking.server.protocol.NetworkPhase;

import java.lang.reflect.Method;
import java.util.Vector;

public class Handler {

    private final NetworkPhase[] np;
    private final Method m;
    private final Listener listener;
    private int priority;

    public Handler(NetworkPhase[] np, Method m, Listener listener)
    {
        this.np = np;
        this.m = m;
        this.listener = listener;
        this.priority = EventHandling.standard_priority;
    }

    public Handler(NetworkPhase[] np, Method m, Listener listener, int priority)
    {
        this.np = np;
        this.m = m;
        this.listener = listener;
        this.priority = priority;
    }


    public NetworkPhase[] getNetworkPhases() {
        return np;
    }

    public Method getMethod() {
        return m;
    }
    public Listener getListener() {
        return this.listener;
    }

    public int getPriority()
    {
        return priority;
    }
}