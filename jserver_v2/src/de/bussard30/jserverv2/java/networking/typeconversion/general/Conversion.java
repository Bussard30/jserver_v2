package de.bussard30.jserverv2.java.networking.typeconversion.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public interface Conversion {

    void initialize();

    String convertTo(@Nullable Object o) throws JsonProcessingException;

    Object convertFrom(Class<?> c, @NotNull String s) throws IOException;

}
