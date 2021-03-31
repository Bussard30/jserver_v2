package eventhandling;

import threading.types.ThreadPriority;
import threading.types.ThreadProcessingSpeed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandler {
    //all networkphases in which the packet can be handled
    String[] networkPhase();

    ThreadPriority tp() default ThreadPriority.NORMAL;

    ThreadProcessingSpeed tps() default ThreadProcessingSpeed.FAST;
}
