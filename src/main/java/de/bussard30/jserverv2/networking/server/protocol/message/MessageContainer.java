package de.bussard30.jserverv2.networking.server.protocol.message;


import de.bussard30.jserverv2.networking.types.Packet;

public class MessageContainer {
    private final String[] s;
    private final Packet p;

    public MessageContainer(String[] s, Packet p) {
        this.s = s;
        this.p = p;
    }

    public String[] getStrings() {
        return s;
    }

    public Packet getPacket() {
        return p;
    }

}
