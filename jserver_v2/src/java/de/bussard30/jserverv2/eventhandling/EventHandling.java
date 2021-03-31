package de.bussard30.jserverv2.eventhandling;

import de.bussard30.jserverv2.eventhandling.events.Event;
import de.bussard30.jserverv2.eventhandling.events.Listener;
import de.bussard30.jserverv2.networking.logger.Logger;
import de.bussard30.jserverv2.networking.server.protocol.NetworkPhase;
import de.bussard30.jserverv2.networking.types.Packet;
import de.bussard30.jserverv2.networking.types.Request;
import de.bussard30.jserverv2.networking.types.Response;
import de.bussard30.jserverv2.threading.manager.ThreadManager;
import de.bussard30.jserverv2.threading.types.EventThreadedJob;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class EventHandling {

    private static final HashMap<Class<?>, Vector<Handler>> hm = new HashMap<>();

    public static final int standard_priority = 1;

    /**
     * defines if handlers may be registered (events can always be thrown)
     */
    public static volatile boolean launched;

    /**
     * Scans methods of object for following annotations: "@interface<br>
     * EventHandler, "@interface RequestHandler, "@interface ResponseHandler<br>
     * Does not throw an error if no method has been found.Does throw an error<br>
     * if a method that is handling the exact same condition is found.<br>
     * Automatically registers parameter as Event<br>
     * <br>
     * Handlers can't be added when the server has been launched.
     *
     * @param listener Listener to be scanned for methods containing eventhandler annotation
     */
    public static void addHandler(Listener listener) {
        if (!launched) {
            for (Method m : listener.getClass().getMethods()) {
                EventHandler a = m.getAnnotation(EventHandler.class);
                if (a != null) {
                    if (m.getParameterTypes().length <= 1) {
                        if (hm.containsKey(m.getParameterTypes()[0])) {
                            hm.get(m.getParameterTypes()[0]).add(new Handler(NetworkPhase.stringsToNetworkPhases(a.networkPhase()), m, listener));
                        } else {
                            Vector<Handler> v = new Vector<>();
                            v.add(new Handler(NetworkPhase.stringsToNetworkPhases(a.networkPhase()), m, listener));
                            hm.put(m.getParameterTypes()[0], v);
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("Can't register events when the server has launched!");
        }
    }

    /**
     * Scans methods of object for following annotations: "@interface<br>
     * EventHandler, "@interface RequestHandler, "@interface ResponseHandler<br>
     * Does not throw an error if no method has been found.Does throw an
     * error<br>
     * if a method that is handling the exact same condition is found.<br>
     * Automatically registers parameter as Event<br>
     * <br>
     * Handlers can't be added when the server has been launched.
     *
     * @param listener Listener to be scanned for methods containing eventhandler annotation
     */
    public static void addHandler(Listener listener, int priority) {
        if (!launched) {
            for (Method m : listener.getClass().getMethods()) {
                EventHandler a = m.getAnnotation(EventHandler.class);
                if (a != null) {
                    if (m.getParameterTypes().length <= 1) {
                        if (hm.containsKey(m.getParameterTypes()[0])) {
                            hm.get(m.getParameterTypes()[0]).add(new Handler(NetworkPhase.stringsToNetworkPhases(a.networkPhase()), m, listener, priority));
                        } else {
                            Vector<Handler> v = new Vector<>();
                            v.add(new Handler(NetworkPhase.stringsToNetworkPhases(a.networkPhase()), m, listener, priority));
                            hm.put(m.getParameterTypes()[0], v);
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException("Can't register events when the server has launched!");
        }
    }

    /**
     * sorts internal hashmap and allows events to be thrown
     */
    public static void launch() {
        //sorts vector inside hashmap with given priority
        for (Map.Entry<Class<?>, Vector<Handler>> m : hm.entrySet()) {
            Collections.sort(m.getValue(), (o1, o2) -> (o1.getPriority() > o2.getPriority()) ? 1 : (o1.getPriority() == o2.getPriority()) ? 0 : -1);
        }
        launched = true;
    }

    /**
     * denies events to be thrown until the launch() method has been executed
     */
    public static void stop() {
        launched = false;
    }

    /**
     * Calls all methods for the certain event.
     *
     * @param event Event to be thrown.
     * @param n     Current NetworkPhase.
     * @return Event that has been "given through" handlers. If event is async, the event remains unchanged.
     */
    public static Event throwEvent(Event event, NetworkPhase n) {
        for (Map.Entry<Class<?>, Vector<Handler>> e : hm.entrySet()) {
            if (e.getKey().equals(event.getClass())) {
                @SuppressWarnings("unchecked") Vector<Handler> shallow_copy = (Vector<Handler>) e.getValue().clone();
                for (Handler h : shallow_copy) {
                    for (NetworkPhase np : h.getNetworkPhases()) {
                        if (np.equals(n)) {
                            if (event.isAsync()) {
                                ThreadManager.getInstance().handleEvent(ThreadManager.EventPools, new EventThreadedJob(h.getMethod(), event));
                            } else {
                                try {
                                    h.getMethod().invoke(h.getListener(), event);
                                } catch (IllegalAccessException | InvocationTargetException | IllegalArgumentException e1) {
                                    Logger.error("EventHandling", e1);
                                } catch (Throwable t) {
                                    Logger.warning("EventHandling", "Throwable thrown in event handling stack invoker. Ignoring.", t);
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

}
