package hashing.pbkdf2;

import jcuda.Pointer;
import jcuda.runtime.JCuda;

public class PBKDF2Hasher
{
	public static void hash(byte[] b)
	{

	}

	public static void hash(String s)
	{

	}

	public static void hash(int i)
	{

	}

	/**
	 * TODO
	 * @param n
	 */
	public static void hash(Number n)
	{
		Pointer pointer = new Pointer();
		JCuda.cudaMalloc(pointer, 4);
		System.out.println("Pointer: " + pointer);
		JCuda.cudaFree(pointer);
	}
}
