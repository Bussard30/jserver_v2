package de.bussard30.jserverv2.java.threading.types;

import de.bussard30.jserverv2.java.eventhandling.events.Event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class EventThreadedJob extends ThreadedJob {
    /**
     * Server structure is already asynchronized.<br>
     * Use async events only when you're doing longer calculations like e.g. calculating hashes
     *
     * @param m Method m to be executed
     * @param e Event e as data for event handler
     */
    public EventThreadedJob(Method m, Event e) {
        super(() -> {
            try {
                m.invoke(e);
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            } catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
            }
        }, true);
    }

    @Override
    public long getTimeOut() {
        return Integer.MAX_VALUE;
    }
}