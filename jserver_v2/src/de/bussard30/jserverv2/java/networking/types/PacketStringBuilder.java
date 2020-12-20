package de.bussard30.jserverv2.java.networking.types;

import java.util.Base64;

public class PacketStringBuilder {

    public static final String seperatorChar = ";";
    public static final String charSet = "base64";

    public static String getString(String[] strings) {
        return getString(strings, seperatorChar, charSet);
    }

    public static String getString(String[] strings, String seperatorChar, String charSet) {
        StringBuilder s = new StringBuilder();
        switch (charSet) {
            case "base64":
                for (int i = 0; i < strings.length; i++) {
                    if (i == strings.length - 1) {
                        s.append(Base64.getEncoder().encodeToString(strings[i].getBytes()));
                        break;
                    }
                    s.append(Base64.getEncoder().encodeToString(strings[i].getBytes()) + seperatorChar);
                }
                break;
            default:
                break;
        }
        return s.toString();
    }
}