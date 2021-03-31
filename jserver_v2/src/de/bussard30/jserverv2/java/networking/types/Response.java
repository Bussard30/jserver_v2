package de.bussard30.jserverv2.java.networking.types;

import de.bussard30.jserverv2.java.networking.server.protocol.message.Message;

/**
 * @author Bussard30
 */
public abstract class Response extends Packet {

    final String identifier = "res";

    public String getPacketIdentifier() {
        return identifier;
    }

    public abstract Message getResponseMessage();
}
