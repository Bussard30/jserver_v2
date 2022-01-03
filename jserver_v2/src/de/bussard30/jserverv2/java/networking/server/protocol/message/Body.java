package de.bussard30.jserverv2.java.networking.server.protocol.message;

import de.bussard30.jserverv2.java.networking.conversionhandlers.Convert;
import de.bussard30.jserverv2.java.networking.server.protocol.Protocol;
import de.bussard30.jserverv2.java.networking.types.Packet;

public class Body {
    private Object[] objects;
    private String converted;
    private int length;

    /**
     * might be unnecessary
     */
    private Protocol protocol;

    private boolean isBuilt;

    public Body(Packet p) {

    }

    public String getString() {
        return null;
    }

    /**
     * Calls constructor {@link Body#Body(Packet)} and returns object.
     */
    public static Body getBody(Packet p) {
        return new Body(p);
    }

    public Header getHeader(String type) {
        return new Header(this, type);
    }

    public Header getHeader(Packet p) {
        return new Header(this, p.getPacketIdentifier());
    }

    public void build() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < objects.length; i++)
        {
            if(i == objects.length - 1)
            {
                sb.append(Convert.getStringWithType(objects[i]));
            }
            else
            {
                sb.append(Convert.getStringWithType(objects[i]) + Message.seperator);
            }
        }
        converted = sb.toString();
        isBuilt = true;
    }

    public Object[] getObjects() {
        return objects;
    }

    public String getConverted() {
        return converted;
    }

    public int getLength() {
        return length;
    }

    public boolean isBuilt() {
        return isBuilt;
    }
}
