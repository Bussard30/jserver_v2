package de.bussard30.jserverv2.eventhandling.events.exceptions;

public class NotRunningException extends Exception {
    public NotRunningException(String s) {
        super(s);
    }

    public NotRunningException() {
        super();
    }
}