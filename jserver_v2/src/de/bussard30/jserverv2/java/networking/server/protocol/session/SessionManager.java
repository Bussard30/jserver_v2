package de.bussard30.jserverv2.java.networking.server.protocol.session;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Consumer;

public class SessionManager {
    public static HashMap<UUID, Session> uuidSessionHashMap;
    public static HashSet<Session> sessionHashSet;

    public static HashMap<Long, Session> deadSessions;

    static {
        uuidSessionHashMap = new HashMap<>();
        sessionHashSet = new HashSet<>();
    }

    public static void register(Session s) {
        uuidSessionHashMap.put(s.getId(), s);
        sessionHashSet.add(s);
    }

    public static Session getSession(UUID uuid) {
        return uuidSessionHashMap.get(uuid);
    }

    public static void forEach(Consumer<? super Session> action) {
        sessionHashSet.forEach(action);
    }

    public static void sleepSession(Session s) {
        // TODO
    }

    public static void killSession(Session s) {
        // TODO
    }

    public static void checkTimeouts() {
        // TODO
    }
}