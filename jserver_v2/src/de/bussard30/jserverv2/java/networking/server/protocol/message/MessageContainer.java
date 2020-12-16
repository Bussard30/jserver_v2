package networking.server.protocol.message;


import networking.types.Packet;

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
