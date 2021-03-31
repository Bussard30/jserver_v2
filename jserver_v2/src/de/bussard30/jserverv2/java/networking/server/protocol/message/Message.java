<<<<<<< HEAD
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
=======
package de.bussard30.jserverv2.java.networking.server.protocol.message;

import de.bussard30.jserverv2.java.networking.types.Packet;

/**
 * Message is more or less a container for several objects inside of a request/ a response
 * TODO needs some restructuring though, is packet even necessary anymore????
 *
 * @author Jonas
 */

public class Message {
    private Body body;
    private Header header;

    private Packet packet;

    public static final char seperator = '|';

    private final int length = 0;

    public Message(Packet p)
    {
        this.packet = p;
        this.body = p.getBody();
        this.header = this.body.getHeader(this.packet);
    }

    /**
     * Converts incoming string to a Message.
     * @param msg incoming string from client (not including length)
     */
    public Message(String msg)
    {
        // convert message
    }

    public int getLength()
    {
        return length;
    }

    public Packet getPacket()
    {
        return packet;
>>>>>>> develop
    }
}
