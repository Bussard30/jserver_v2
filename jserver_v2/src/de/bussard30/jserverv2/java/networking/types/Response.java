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
public interface Response {

    String identifier = "res";


    default String getPacketIdentifier() {
        return identifier;
    }

    /**
     * @param response Response that can processed
     * @param request  Request on which the Response responded
     */
    void onResponse(Packet response, Packet request);

    Message getResponseMessage();
=======
public abstract class Response extends Packet {

    final String identifier = "res";

    public String getPacketIdentifier() {
        return identifier;
    }

    public abstract Message getResponseMessage();
>>>>>>> develop
}
