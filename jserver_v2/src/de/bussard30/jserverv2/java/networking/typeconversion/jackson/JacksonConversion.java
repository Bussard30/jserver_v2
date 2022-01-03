package de.bussard30.jserverv2.java.networking.typeconversion.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bussard30.jserverv2.java.networking.typeconversion.general.Conversion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class JacksonConversion implements Conversion {

    private ObjectMapper mapper;

    @Override
    public void initialize() {
        mapper = new ObjectMapper();
    }

    @Override
    public String convertTo(@Nullable Object o) throws JsonProcessingException {
        return mapper.writeValueAsString(o);
    }

    @Override
    public Object convertFrom(@NotNull Class c, @NotNull String s) throws IOException {
        return mapper.readValue(s, c);
    }

}
