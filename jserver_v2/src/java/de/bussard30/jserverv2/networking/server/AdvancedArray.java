package de.bussard30.jserverv2.networking.server;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class AdvancedArray<T> implements Iterable<T>
{
	private final T[] array;
	private volatile int offset = 0;
	private final int length;
	private volatile boolean concurrentModification;

	public AdvancedArray(Class<T> c, int length)
	{
		@SuppressWarnings("unchecked")
		final T[] array = (T[]) Array.newInstance(c, length);
		this.array = array;
		this.length = length;
	}

	public void put(T object, int index)
	{
		if (!concurrentModification)
		{
			if (index >= length)
			{
				index = index % length;
				//throw new ArrayIndexOutOfBoundsException("Index [" + index + "] is out of bounds.");
			}
			if ((index + offset) >= length)
			{
				array[(index + offset) - length] = object;
			} else
			{
				array[index + offset] = object;
			}
		} else
			throw new ConcurrentModificationException();
	}

	public T get(int index)
	{
		if (!concurrentModification)
		{
			if ((index + offset) >= length)
			{
				return (T) array[(index + offset) - length];
			} else
			{
				return (T) array[index + offset];
			}
		} else
			throw new ConcurrentModificationException();
	}

	public void removeFirst()
	{
		if (!concurrentModification)
		{
			array[offset] = null;
			offset += 1;
			if (offset >= length)
				offset -= length;
		} else
			throw new ConcurrentModificationException();
	}

	public int getLength()
	{
		return length;
	}

	public int getOffset()
	{
		return offset;
	}

	public boolean concurrentModification()
	{
		return concurrentModification;
	}

	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<>() {
			private int currentIndex = offset;
			private final int l = length;

			@Override
			public boolean hasNext() {
				if (currentIndex == (offset + l)) {
					concurrentModification = false;
					return false;
				} else
					return true;

			}

			@Override
			public T next() {
				concurrentModification = true;
				T object = null;
				if (currentIndex >= length) {
					object = get(currentIndex - length);
				} else {
					object = get(currentIndex);
				}
				currentIndex++;

				return object;
			}
		};
	}
}
