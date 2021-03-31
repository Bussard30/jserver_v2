package networking.server.protocol.message;


import networking.types.Packet;

import java.util.Base64;

public abstract class Body implements MessagePart {

    public String getBody(Packet p) {
        return "" + Base64.getEncoder().encodeToString(p.getConvertedBuffer());
    }
}
