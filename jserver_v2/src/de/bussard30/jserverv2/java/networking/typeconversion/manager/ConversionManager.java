package de.bussard30.jserverv2.java.networking.typeconversion.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.bussard30.jserverv2.java.networking.typeconversion.exceptions.ConflictingMappingsException;
import de.bussard30.jserverv2.java.networking.typeconversion.exceptions.InitializationException;
import de.bussard30.jserverv2.java.networking.typeconversion.general.Conversion;
import de.bussard30.jserverv2.java.networking.typeconversion.gson.GsonConversion;
import de.bussard30.jserverv2.java.networking.typeconversion.jackson.JacksonConversion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class ConversionManager {
    public static final String USE_GSON = "gson";
    public static final String USE_JACKSON = "jackson";
    private static Conversion conv;

    private static HashSet<Class<?>> classes;
    private static HashMap<Class<?>, Conversion> customMappings;


    /**
     * will use gson on default
     */
    public static void initialize() {
        classes = new HashSet<>();
        customMappings = new HashMap<>();

        conv = new GsonConversion();
        conv.initialize();
    }

    /**
     * Initializes manager with given converter type.
     *
     * @param type either gson or jackson.
     * @throws InitializationException
     */
    public static void initialize(String type) throws InitializationException {
        if (type.equals(USE_GSON)) {
            conv = new GsonConversion();
            conv.initialize();
        } else if (type.equals(USE_JACKSON)) {
            conv = new JacksonConversion();
            conv.initialize();
        } else {
            throw new InitializationException("Could not initialize ConversionManager: Did not recognize type.");
        }
    }

    /**
     * Initializes manager with given converter.
     *
     * @param converter
     */
    public static void initialize(Conversion converter) {
        conv = converter;
    }

    /**
     * Adds a converter for a specific class.
     *
     * @param c
     * @param conv
     */
    public static void addCustomConverter(Class c, Conversion conv) throws ConflictingMappingsException {
        if (classes.contains(c) || customMappings.containsKey(c)) {
            throw new ConflictingMappingsException("Could not add custom mapping in ConversionManager " +
                    "because one mapping for this class already exists.");
        }
        classes.add(c);
        customMappings.put(c, conv);
    }

    /**
     * Will convert String s to an object of class c. Will use custom mapping if available.
     *
     * @param c
     * @param s
     * @return deserialized object
     * @throws IOException
     */
    public static Object convertFrom(@NotNull Class<?> c, @NotNull String s) throws IOException {
        if (classes.contains(c)) {
            return customMappings.get(c).convertFrom(c, s);
        }
        return conv.convertFrom(c, s);
    }

    /**
     * @param o
     * @return serialized json string
     * @throws JsonProcessingException
     */
    public static String convertTo(@Nullable Object o) throws JsonProcessingException {
        if (classes.contains(o.getClass())) {
            return customMappings.get(o.getClass()).convertTo(o);
        }
        return conv.convertTo(o);
    }
}
