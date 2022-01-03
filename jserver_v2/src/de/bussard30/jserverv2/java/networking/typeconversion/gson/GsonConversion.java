package de.bussard30.jserverv2.java.networking.typeconversion.gson;

import com.google.gson.Gson;
import de.bussard30.jserverv2.java.networking.typeconversion.general.Conversion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GsonConversion implements Conversion {

    @Override
    public void initialize() {

    }

    @Override
    public String convertTo(@Nullable Object o) {
        return new Gson().toJson(o);
    }

    @Override
    public Object convertFrom(@NotNull Class<?> c, @NotNull String s) {
        return new Gson().fromJson(s, c);
    }
}
