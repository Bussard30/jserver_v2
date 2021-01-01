package de.bussard30.jserverv2.java.networking.server.protocol.session;

import java.lang.reflect.Field;

public class SessionInjector {
    // TODO needs to extract variables from super class
    static void inject(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(KeepStored.class)) {
                KeepStored set = field.getAnnotation(KeepStored.class);
                field.setAccessible(true); // should work on private fields
                try {
                    // TODO
                    field.set(instance, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static SessionState retrieve(Object instance) {
        // TODO
        return null;
    }


}