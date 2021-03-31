package de.bussard30.jserverv2.networking.server.protocol.message;

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
        byte[] bytes = s.getBytes();
        if (bytes.length >= Short.MAX_VALUE * 2) ;
        this.length = length;
    }

    /**
     * Converts incoming message to header. (Takes only header)<br>
     * e.g. : "byte0 byte1 byte2 ... byte(n)" -> byte0 byte1 = (short)s; byte2 ... byte(n) = (String) type
     *
     * @param msg
     */
    public Header(String msg) {
        byte[] bytes = msg.getBytes();
        short s = (short) (bytes[0] << 8 | bytes[1] & 0xFF);
        int length = s + Short.MAX_VALUE;
    }

    /**
     * Converts an integer below {@link Short#MAX_VALUE}*2 to a short value.<br>
     * Length needs to be above 0 and below {@link Short#MAX_VALUE}*2, otherwise returns {@link Short#MAX_VALUE}.
     * @return Returns a value between {@link Short#MIN_VALUE} and {@link Short#MAX_VALUE}
     */
    public short getConvertedLength() {
        return (length <= Short.MAX_VALUE * 2 && length >= 0) ? (short) (length - Short.MAX_VALUE) : Short.MAX_VALUE;
    }

    /**
     * @return header with length at the start
     */
    public String getString() {
        return getConvertedLength() + type;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

}
