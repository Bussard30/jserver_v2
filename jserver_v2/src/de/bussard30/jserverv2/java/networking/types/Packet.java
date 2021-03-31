<<<<<<< HEAD
package networking.types;

public abstract class Packet {
    protected int nr;
    protected Object buffer;
    protected String name;
    protected byte[] convertedBuffer;

    /**
     * sets nr to -1
     *
     * @param name
     * @param buffer
     * @param convertedBuffer
     */
    public Packet(String name, Object buffer, byte[] convertedBuffer) {
        this.buffer = buffer;
        this.name = name;
        this.nr = -1;
        this.convertedBuffer = convertedBuffer;
    }

    public Packet(String name, Object buffer, int nr, byte[] convertedBuffer) {
        this.buffer = buffer;
        this.nr = nr;
        this.name = name;
        this.convertedBuffer = convertedBuffer;
    }

    public Object getBuffer() {
        return buffer;
    }

    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }

    public String getName() {
        return name;
    }

    public byte[] getConvertedBuffer() {
        return convertedBuffer;
    }

    // packet identifier in actual packet sent to other client, for example "res" for response

    /**
     * @return unique packet identifier
     */
    public String getPacketIdentifier() {
        return "";
=======
package de.bussard30.jserverv2.java.networking.types;


import de.bussard30.jserverv2.java.networking.server.protocol.Protocol;
import de.bussard30.jserverv2.java.networking.server.protocol.message.Body;
import de.bussard30.jserverv2.java.networking.server.protocol.message.Message;

public abstract class Packet {
    private Message m;

    public abstract String getPacketIdentifier();

    public abstract Object[] getObjects();

    public abstract void load(Object[] content);

    public abstract Body getBody();

    public abstract Protocol getProtocol();

    /**
     * Builds message.
     */
    public void build() {
        m = getMessage();
    }

    /**
     * Only executable after calling {@link Packet#build()}
     *
     * @return length of message in bytes
     */
    public int length() {
        return m.getLength();
    }

    /**
     * Only executable after calling {@link Packet#build()}
     *
     * @return length of message in bytes
     */
    public Message getMessage() {
        return new Message(this);
>>>>>>> develop
    }
}
