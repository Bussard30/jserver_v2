package networking.types;

import de.jserverv2.bussard30.networking.server.protocol.message.Message;

/**
 * @author Bussard30
 */
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
}
