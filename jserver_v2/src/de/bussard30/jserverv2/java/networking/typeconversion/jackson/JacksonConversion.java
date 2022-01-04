package de.bussard30.jserverv2.java.networking.typeconversion.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.bussard30.jserverv2.java.networking.typeconversion.general.Conversion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class JacksonConversion implements Conversion {

    private ObjectMapper mapper;

    @Override
    public void initialize() {
        // should provide safety i don't really know tbh
        // imagine if there was a lite version so i wouldn't have to disable features lmao
        // or just disable them by default 4Head
        MapperFeature.BLOCK_UNSAFE_POLYMORPHIC_BASE_TYPES.enabledByDefault();
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
