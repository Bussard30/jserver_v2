package networking.server;

import java.lang.reflect.Array;
import java.util.Iterator;

public class AdvancedArray<T> implements Iterable<T>
{
	T[] array;
	int offset = 0;
	int length;

	public AdvancedArray(Class<T> c, int length)
	{
		@SuppressWarnings("unchecked")
		final T[] array = (T[]) Array.newInstance(c, length);
		this.array = array;
		this.length = length;
	}

	public void put(T object, int index)
	{
		if (index >= length)
		{
			throw new ArrayIndexOutOfBoundsException("Index " + index + " is out of bounds.");
		}
		if ((index + offset) >= length)
		{
			array[(index + offset) - length] = object;
		} else
		{
			array[index + offset] = object;
		}
	}

	public T get(int index)
	{
		if ((index + offset) >= length)
		{
			return (T) array[(index + offset) - length];
		} else
		{
			return (T) array[index + offset];
		}
	}

	public void removeFirst()
	{
		array[offset] = null;
		offset += 1;
		if (offset >= 10)
			offset -= 10;
	}

	public int getLength()
	{
		return length;
	}

	public int getOffset()
	{
		return offset;
	}

	@Override
	public Iterator<T> iterator()
	{
		return new Iterator<T>()
		{
			private int currentIndex = offset;
			private int l = length;

			@Override
			public boolean hasNext()
			{
				if (currentIndex == (offset + l))
				{
					System.out.println("bra");
					return false;
				} else
					return true;

			}

			@Override
			public T next()
			{
				System.out.println("Current index:" + currentIndex);
				System.out.println("offset:" + offset);
				System.out.println("l:" + l);
				T object = null;
				if (currentIndex >= length)
				{
					object = get(currentIndex - length);
					currentIndex++;
				} else
				{
					object = get(currentIndex);
					currentIndex++;
				}

				return object;
			}
		};
	}
}
