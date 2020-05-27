package eventhandling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import threading.types.ThreadPriority;
import threading.types.ThreadProcessingSpeed;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandler
{
	String[] networkPhase();

	ThreadPriority tp() default ThreadPriority.MID;

	ThreadProcessingSpeed tps() default ThreadProcessingSpeed.FAST;
}
