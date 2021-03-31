package networking.types;

import de.jserverv2.bussard30.networking.server.protocol.message.Message;

/**
 * @author Bussard30
 */
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
}
