package networking.server.protocol.message;

import networking.types.Packet;

public class Message {
    private final MessagePart[] messageParts;

    private char seperator = ';';

    /**
     * Order in array matters.
     *
     * @param m
     */
    public Message(MessagePart[] m) {
        this.messageParts = m;
    }

    public Message(MessagePart[] m, char seperator) {
        this(m);
        this.seperator = seperator;
    }

    public String constructMessage(Packet p) {
        String temp = "";
        for (int i = 0; i < messageParts.length; i++) {
            temp += messageParts[i].getString(p);
        }
        return temp;
    }

    public Packet getPacket(String s) {
        String[] parts = s.split(String.valueOf(seperator));
        MessageContainer mc = new MessageContainer(parts, null);
        for (int i = 0; i < messageParts.length; i++) {
            mc = messageParts[i].getPacket(mc.getStrings(), mc.getPacket());
        }
        return mc.getPacket();
    }
}
