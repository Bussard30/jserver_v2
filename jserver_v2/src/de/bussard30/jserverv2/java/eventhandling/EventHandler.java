package de.bussard30.jserverv2.java.eventhandling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventHandler
{
	//all networkphases in which the packet can be handled
	String[] networkPhase();

	//TODO ThreadPriority tp() default ThreadPriority.NORMAL;

	boolean ignoreCanceled() default false;
}
