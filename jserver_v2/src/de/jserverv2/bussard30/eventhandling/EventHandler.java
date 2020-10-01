package de.jserverv2.bussard30.eventhandling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.jserverv2.bussard30.threading.types.ThreadPriority;
import de.jserverv2.bussard30.threading.types.ThreadProcessingSpeed;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandler
{
	//all networkphases in which the packet can be handled
	String[] networkPhase();

	//TODO ThreadPriority tp() default ThreadPriority.NORMAL;

	ThreadProcessingSpeed tps() default ThreadProcessingSpeed.FAST;
}
