package networking.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import networking.server.protocol.NetworkPhase;
import threading.types.ThreadPriority;
import threading.types.ThreadProcessingSpeed;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Handler
{
	/**
	 * Workaround for annotations not allowing interfaces
	 * @return
	 */
	String[] networkPhase();
	ThreadPriority tp();
	ThreadProcessingSpeed tps();
	Handlertype ht();
}
