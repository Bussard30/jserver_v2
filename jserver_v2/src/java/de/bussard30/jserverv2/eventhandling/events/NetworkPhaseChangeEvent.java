package de.bussard30.jserverv2.eventhandling.events;

public class NetworkPhaseChangeEvent extends Event{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}