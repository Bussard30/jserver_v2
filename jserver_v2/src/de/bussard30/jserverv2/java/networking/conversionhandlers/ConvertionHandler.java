package de.bussard30.jserverv2.java.networking.conversionhandlers;

import java.security.InvalidParameterException;
@Deprecated
public abstract class ConvertionHandler {
    /**
     * Converts an object to a string
     *
     * @param o the object to be converted
     * @return
     */
    public abstract String getString(Object o) throws InvalidParameterException;

    /**
     * Converts a string to an object
     *
     * @param s the string to be converted
     * @return
     */
    public abstract Object getObject(String s) throws InvalidParameterException;
}
