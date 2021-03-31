<<<<<<< HEAD
package networking.types;

import de.jserverv2.bussard30.networking.server.protocol.message.Message;
=======
package de.bussard30.jserverv2.java.networking.types;

import de.bussard30.jserverv2.java.networking.server.protocol.message.Message;
>>>>>>> develop

/**
 * @author Bussard30
 */
<<<<<<< HEAD
public interface Request {

    String identifier = "req";

    default String getPacketIdentifier() {
        return identifier;
    }

    /**
     * @param request Request that can be processed.
     */
    void onRequest(Packet request);

    Message getRequestMessage();
=======
public abstract class Request extends Packet {

    public static final String identifier = "req";

    public String getPacketIdentifier() {
        return identifier;
    }

    public abstract Message getRequestMessage();
>>>>>>> develop
}
