package de.bussard30.jserverv2.java.networking.server.protocol.message;

public class Header {
    private int length;
    private String type;

    private String header;

    /**
     * Body needs to have been built. Calls build() otherwise.
     *
     * @param b
     */
    public Header(Body b, String type) {
        if (!b.isBuilt()) {
            b.build();
        }

        this.type = type;
        String s = type + Message.seperator + b.getConverted();
        this.length = s.getBytes().length;
    }

    /**
     * @return header with length at the start
     */
    public String getString() {
        return length + type;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

}
