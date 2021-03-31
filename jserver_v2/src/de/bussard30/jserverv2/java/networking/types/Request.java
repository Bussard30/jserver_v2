package de.bussard30.jserverv2.java.networking.types;

import de.bussard30.jserverv2.java.networking.server.protocol.message.Message;

/**
 * @author Bussard30
 */
public abstract class Request extends Packet {

    public static final String identifier = "req";

    public String getPacketIdentifier() {
        return identifier;
    }

    public abstract Message getRequestMessage();
}
