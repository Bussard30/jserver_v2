package de.bussard30.jserverv2.java.networking.server;

import de.bussard30.jserverv2.java.networking.server.protocol.Protocol;

import java.util.HashMap;

public class ProtocolManager {

    private static HashMap<String, Protocol> stringIndex = new HashMap<>();

    public static boolean protocolExists(String s) {
        // TODO
        return false;
    }

    public static Protocol getProtocol(String s) {
        // TODO
        return null;
    }

    public static String buildString(Protocol p) {
        // TODO
        return null;
    }

    public static void addProtocol(Protocol p)
    {
        // TODO
    }
}