package de.bussard30.jserverv2.networking.convertionhandlers;

import de.bussard30.jserverv2.networking.types.ConvertionHandling;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.Base64;
import java.util.HashMap;

public class Convert {
    private static final HashMap<Class<?>, ConversionHandler> assignments;
    private static final HashMap<String, Class<?>> assignmentIndexing;
    private static final String separatorChar = ";";

    static {
        assignments = new HashMap<>();
        assignmentIndexing = new HashMap<>();
    }

    public static void register(ConversionHandler o) throws InvalidParameterException {
        try {
            if (!assignments.containsKey(o.getClass().getAnnotation(ConvertionHandling.class).target())) {
                assignments.put(o.getClass().getAnnotation(ConvertionHandling.class).target(), o);
                assignmentIndexing.put(o.getClass().getAnnotation(ConvertionHandling.class).target().getName(),
                        o.getClass().getAnnotation(ConvertionHandling.class).target());
            } else {
                throw new InvalidParameterException("ConversionHandler for this type already registered!");
            }
        } catch (Throwable t) {
            throw new InvalidParameterException("Could not register conversion handler");

        }
    }

    /**
     * @param o Object to be converted to an string.
     * @return a string : "type;convertedObject" (not including quotation mark)
     * @throws InvalidParameterException
     */
    public static String getStringWithType(Object o) throws InvalidParameterException {
        return (o instanceof String) ? o.getClass().getName() + separatorChar + toBase64((String) o) :
                o.getClass().getName() + separatorChar + toBase64(assignments.get(o.getClass()).buildString(o));
    }

    /**
     * Returns a string for an object that can be converted back to an object using {@link Convert#getObject(Class, String)}
     * <br> uses base64 charset.
     * @param o Object to be formatted.
     * @return String of formatted object.
     * @throws InvalidParameterException
     */
    public static String getString(Object o) throws InvalidParameterException {
        return (o instanceof String) ? toBase64((String) o) : toBase64(assignments.get(o.getClass()).buildString(o));
    }

    /**
     * Converts given string to bytes and then to base64 charset string
     *
     * @param s string to be converted.
     * @return base64 string.
     */
    public static String toBase64(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    /**
     * Converts given base64 string to an utf-8 string
     * @param s base64 string to be converted.
     * @return utf-8 string
     */
    public static String fromBase64(String s) {
        return new String(Base64.getDecoder().decode(s.getBytes()), StandardCharsets.UTF_8);
    }


    /**
     * Converts given base64 string to an string
     * @param s base64 string to be converted.
     * @param c charset for new string
     * @return string
     */
    @SuppressWarnings("unused")
    public static String fromBase64(String s, Charset c) {
        return new String(Base64.getDecoder().decode(s.getBytes()), c);
    }

    @SuppressWarnings("unused")
    public static Object getObject(String s) throws InvalidParameterException {
        String[] strings = s.split(separatorChar);
        if (strings.length == 2) {
            return getObject(assignmentIndexing.get(strings[0]), fromBase64(strings[1]));
        }
        else
        {
            throw new InvalidParameterException();
        }
    }

    /**
     * Return an object for a string of an formatted object (see {@link #getString(Object)}) and its class.
     *
     * @param c Expected class of object
     * @param s Formatted string
     * @return an object (which is an instance of c)
     * @throws InvalidParameterException on invalid parameters
     */
    public static Object getObject(Class<?> c, String s) throws InvalidParameterException {
        return (c.equals(String.class)) ? s : assignments.get(c).buildObject(s);
    }

    /**
     * Checks if c has been registered.
     *
     * @param c Class to be checked
     * @return a boolean if c has been found.
     */
    public static boolean containsClass(Class<?> c) {
        return assignments.containsKey(c);
    }
}
