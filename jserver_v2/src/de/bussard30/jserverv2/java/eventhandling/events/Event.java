package de.bussard30.jserverv2.java.eventhandling.events;

public abstract class Event {
    protected Result r;

    /**
     * Returns name of event.
     * @return name of event
     */
    public abstract String getName();

    /**
     * Returns if event is being handled asynchronous.<br>
     * Should only be used e.g. calculating hashes
     * @return
     */
    public abstract boolean isAsync();

    /**
     * Sets the result of the event.
     * @param r Result.Allow or Result.Deny (allows or denies event)
     */
    public void setResult(Result r) {
        this.r = r;
    }

    public Result getResult() {
        return r;
    }
}