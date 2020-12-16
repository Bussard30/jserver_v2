package de.bussard30.jserverv2.java.eventhandling.events;

public abstract class Event {
    protected Result r;

    public abstract String getName();

    public abstract boolean isAsync();

    public void setResult(Result r) {
        this.r = r;
    }

    public Result getResult() {
        return r;
    }
}