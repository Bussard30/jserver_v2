package de.bussard30.jserverv2.eventhandling.events;

public class MessageReceivedEvent extends Event{

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isAsync() {
        return false;
    }
}