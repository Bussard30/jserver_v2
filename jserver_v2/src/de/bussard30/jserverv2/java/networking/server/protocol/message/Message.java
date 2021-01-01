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

    public static char seperator = '|';

    private int length = 0;

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
        return null;
    }
}
