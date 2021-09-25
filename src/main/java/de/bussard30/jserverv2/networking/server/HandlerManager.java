package de.bussard30.jserverv2.networking.server;

import de.bussard30.jserverv2.networking.server.protocol.NetworkPhase;
import de.bussard30.jserverv2.networking.types.Packet;

import java.util.HashMap;
import java.util.Vector;
import java.util.function.Consumer;

/**
 * object(!) to be used inside serverHandler to manage runnables for each packet that could be received
 * should shallow copy from eventhandling to have everything
 */
public class HandlerManager {
    /**
     * Assigns certain request/response classes to methods depending on the
     * network phase
     */
    private HashMap<Class<? extends Packet>, HandlerWrapper<Object>> assignments;
    private final Vector<Class<? extends Packet>> temp = new Vector<>();

    /**
     * @param c         Class of packet on which the event handling method is<br>
     *                  supposed to be performed on
     * @param parameter Parameter of event handling method<br>
     *                  e.g. Event object
     * @param n         Network phase in which the event occurs
     * @return
     */
    public Runnable getRunnable(Class<? extends Packet> c, Object parameter, NetworkPhase n) {
        return () -> assignments.get(c).getConsumer(n).accept(parameter);
    }

    public void addConsumer(Class<? extends Packet> c, NetworkPhase n, Consumer<Object> cc) {
        assignments.put(c, new HandlerWrapper<>(n, cc));
    }

    /**
     * Filters all Handlers which are in this network phase.<br>
     *
     * @param n
     */
    public void filter(NetworkPhase... n) {
        assignments.forEach((c, h) ->
        {
            for (NetworkPhase nn : n) {
                if (h.getNetworkPhases() != nn) {
                    temp.add(c);
                }
            }
        });
        temp.forEach(c -> assignments.remove(c));
        temp.clear();
    }

    public void invertedFilter(NetworkPhase... n) {
        assignments.forEach((c, h) ->
        {
            for (NetworkPhase nn : n) {
                if (h.getNetworkPhases() == nn) {
                    temp.add(c);
                }
            }
        });
        temp.forEach(c -> assignments.remove(c));
        temp.clear();
    }

    public void register(NetworkPhase n, Class<? extends Packet> c, Consumer<Object> cc) {
        assignments.put(c, new HandlerWrapper<>(n, cc));
    }
}

class HandlerWrapper<T> {
    private NetworkPhase n;
    private Consumer<? super T> c;

    protected HandlerWrapper(NetworkPhase n, Consumer<? super T> c) {
        this.c = c;
        this.n = n;
    }

    protected void setConsumer(NetworkPhase n, Consumer<? super T> c) {
        this.c = c;
    }

    protected void setNetworkPhase(NetworkPhase n) {
        this.n = n;
    }

    protected Consumer<? super T> getConsumer(NetworkPhase n) {
        return c;
    }

    protected NetworkPhase getNetworkPhases() {
        return n;
    }
}
