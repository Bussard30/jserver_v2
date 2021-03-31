package de.bussard30.jserverv2.eventhandling.events;

public class ProtocolConditionFulfilledEvent extends Event{
    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}