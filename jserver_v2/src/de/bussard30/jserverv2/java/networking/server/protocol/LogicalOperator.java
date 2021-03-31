package de.bussard30.jserverv2.java.networking.server.protocol;

import java.io.Serializable;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class LogicalOperator implements Comparable<Boolean>, Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4860225388961085458L;

	public static BiPredicate<Comparable<Boolean>, Boolean> and = (b0, b1) ->
			b0 == b1;

	public static BiPredicate<Comparable<Boolean>, Boolean> or = (b0, b1) ->
			b1 || b0.compareTo(b1) == 1;

	public static BiPredicate<Comparable<Boolean>, Boolean> xor = (b0, b1) ->
			b0 != b1;

	public static BiPredicate<Comparable<Boolean>, Boolean> nand = (b0, b1) ->
			!b1 || b0 == b1;

	public static BiPredicate<Comparable<Boolean>, Boolean> enor = (b0, b1) ->
			b0 == b1;

	public static LogicalOperator none = null;

	final BiPredicate<Comparable<Boolean>, Boolean> bc;

	private final Supplier<Comparable<Boolean>> boolFetcher0;
	private final Supplier<Boolean> boolFetcher1;

	/**
	 * 
	 * @param bc use one of the already existing static predicates in this class
	 * @param boolFetcher0
	 * @param boolFetcher1
	 */
	public LogicalOperator(BiPredicate<Comparable<Boolean>, Boolean> bc, Supplier<Comparable<Boolean>> boolFetcher0,
			Supplier<Boolean> boolFetcher1)
	{
		this.bc = bc;
		this.boolFetcher0 = boolFetcher0;
		this.boolFetcher1 = boolFetcher1;
	}

	public boolean compare(boolean b0, boolean b1)
	{
		return bc.test(b0, b1);
	}
	
	public boolean compare()
	{
		return bc.test(boolFetcher0.get(), boolFetcher1.get());
	}

	@Override
	public int compareTo(Boolean arg0)
	{
		return Boolean.compare(bc.test(boolFetcher0.get(), boolFetcher1.get()), arg0);
	}

	public boolean intToBoolean(int i)
	{
		return i == 0;
	}

}
