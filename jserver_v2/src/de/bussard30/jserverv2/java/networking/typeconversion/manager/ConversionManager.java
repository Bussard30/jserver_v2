package de.bussard30.jserverv2.java.networking.typeconversion.manager;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.bussard30.jserverv2.java.networking.typeconversion.exceptions.InitializationException;
import de.bussard30.jserverv2.java.networking.typeconversion.general.Conversion;
import de.bussard30.jserverv2.java.networking.typeconversion.gson.GsonConversion;
import de.bussard30.jserverv2.java.networking.typeconversion.jackson.JacksonConversion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ConversionManager {
    public static final String USE_GSON = "gson";
    public static final String USE_JACKSON = "jackson";
    private static Conversion conv;

    /**
     * will use gson on default
     */
    public static void initialize() {
        conv = new GsonConversion();
        conv.initialize();
    }

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

    public static void initialize(Conversion converter) {
        conv = converter;
    }

    public static Object convertFrom(@NotNull Class<?> c, @NotNull String s) throws IOException {
        return conv.convertFrom(c, s);
    }

    public static String convertTo(@Nullable Object o) throws JsonProcessingException {
        return conv.convertTo(o);
    }
}
