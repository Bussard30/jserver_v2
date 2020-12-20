package de.bussard30.jserverv2.java.eventhandling;

import de.bussard30.jserverv2.java.eventhandling.events.Event;
import de.bussard30.jserverv2.java.eventhandling.events.Listener;
import de.bussard30.jserverv2.java.eventhandling.events.Result;
import de.bussard30.jserverv2.java.networking.logger.Logger;
import de.bussard30.jserverv2.java.networking.server.protocol.NetworkPhase;
import de.bussard30.jserverv2.java.networking.types.Packet;
import de.bussard30.jserverv2.java.networking.types.Request;
import de.bussard30.jserverv2.java.networking.types.Response;
import de.bussard30.jserverv2.java.threading.manager.ThreadManager;
import de.bussard30.jserverv2.java.threading.types.EventThreadedJob;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class EventHandling {

    private static final HashMap<Class<?>, DataContainer> hm = new HashMap<>();

    /**
     * Scans methods of object for following annotations: "@interface<br>
     * EventHandler, "@interface RequestHandler, "@interface ResponseHandler<br>
     * Does not throw an error if no method has been found.Does throw an
     * error<br>
     * if a method that is handling the exact same condition is found.<br>
     * Automatically registers parameter as Event<br>
     *
     * @param listener Listener to be scanned for methods containing eventhandler annotation
     */
    public static void addHandler(Listener listener) {
        for (Method m : listener.getClass().getMethods()) {
            EventHandler a = m.getAnnotation(EventHandler.class);
            if (a != null) {
                if (m.getParameterTypes().length <= 1) {
                    if (hm.containsKey(m.getParameterTypes()[0])) {
                        hm.get(m.getParameterTypes()[0]).addMethod(m);
                    } else {
                        hm.put(m.getParameterTypes()[0],
                                new DataContainer(NetworkPhase.stringsToNetworkPhases(a.networkPhase()), m, listener));
                    }
                }
            }
        }
    }

    /**
     * Calls all methods for the certain event.
     *
     * @param event Event to be thrown.
     * @param n     Current NetworkPhase.
     *
     * @return Event that has been "given through" handlers. If event is async, the event remains unchanged.
     */
    public static Event throwEvent(Event event, NetworkPhase n) {
        synchronized (hm) {
            for (Map.Entry<Class<?>, DataContainer> e : hm.entrySet()) {
                if (e.getKey().equals(event.getClass())) {
                    for (NetworkPhase np : e.getValue().getNetworkPhases()) {
                        if (np.equals(n)) {
                            synchronized (e.getValue().getMethods()) {
                                for (Method m : e.getValue().getMethods()) {
                                    if (event.isAsync()) {
                                        ThreadManager.getInstance().handleEvent(ThreadManager.EventPools, new EventThreadedJob(m, event));
                                    } else {
                                        try {
                                            m.invoke(e.getValue().getHandler(), event);
                                        } catch (IllegalAccessException e1) {
                                            Logger.error("EventHandling", e1);
                                        } catch (IllegalArgumentException e1) {
                                            Logger.error("EventHandling", e1);
                                        } catch (InvocationTargetException e1) {
                                            Logger.error("EventHandling", e1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return event;
    }

    public static void handle(Packet p) {
        if (p instanceof Request) {
            handleRequest((Request) p);
        } else if (p instanceof Response) {
            handleResponse((Response) p);
        }
    }

    public static void handleRequest(Request request) {
        // TODO
    }

    public static void handleResponse(Response response) {
        // TODO
    }

    private static class DataContainer {
        private final NetworkPhase[] np;
        private final Vector<Method> m;
        private final Listener handler;

        public DataContainer(NetworkPhase[] np, Method m, Listener handler) {
            this.np = np;
            this.m = new Vector<>();
            this.m.add(m);
            this.handler = handler;
        }

        public NetworkPhase[] getNetworkPhases() {
            return np;
        }

        public Vector<Method> getMethods() {
            return m;
        }

        public void addMethod(Method m) {
            this.m.add(m);
        }

        public Listener getHandler() {
            return this.handler;
        }
    }
}
