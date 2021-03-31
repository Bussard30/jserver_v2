<<<<<<< HEAD:jserver_v2/src/de/bussard30/jserverv2/java/networking/types/ConvertionHandling.java
package networking.types;
=======
package de.bussard30.jserverv2.java.networking.types;
>>>>>>> develop:jserver_v2/src/de/jserverv2/bussard30/networking/types/ConvertionHandling.java

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConvertionHandling {
    Class<?> target();
}
