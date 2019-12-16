package networking.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

import networking.server.protocol.NetworkPhase;
import networking.types.Handler;
import networking.types.Packet;

public class Main
{

	private static HandlerManager hm = new HandlerManager();

	public static void addHandler(Packet p)
	{
		final Vector<Consumer<Object>> v;
		final List<Field> allFields = new ArrayList<Field>(Arrays.asList(p.getClass().getFields()));
		for (final Field field : allFields)
		{
			if (field.isAnnotationPresent(Handler.class))
			{
				Annotation annotInstance = field.getAnnotation(Handler.class);
				if (annotInstance.annotationType().equals(Handler.class))
					break;
				Object o = null;
				try
				{
					o = field.get(p);
				} catch (IllegalArgumentException | IllegalAccessException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (o != null)
				{
					if (o instanceof Consumer<?>)
					{
						for (String n : ((Handler) annotInstance).networkPhase())
						{
							//TODO get all classes that implement NetworkPhase and assign string to that
						}
					}
				}
			}
		}
	}

	public static List<Field> getFieldsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation)
	{
		final List<Field> fields = new ArrayList<Field>();
		final List<Field> allFields = new ArrayList<Field>(Arrays.asList(type.getFields()));
		for (final Field field : allFields)
		{
			if (field.isAnnotationPresent(annotation))
			{
				Annotation annotInstance = field.getAnnotation(annotation);
				// TODO process annotInstance
				fields.add(field);
			}
		}
		return fields;
	}
}
