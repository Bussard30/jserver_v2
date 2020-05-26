package networking.server.protocol;

import java.util.function.BiPredicate;

public class LogicalOperator
{

	public static LogicalOperator and = new LogicalOperator((b0, b1) ->
	{
		return b0 && b1;
	});

	public static LogicalOperator or = new LogicalOperator((b0, b1) ->
	{
		return b0 || b1;
	});

	public static LogicalOperator xor = new LogicalOperator((b0, b1) ->
	{
		return b0 != b1;
	});

	public static LogicalOperator nand = new LogicalOperator((b0, b1) ->
	{
		return b0 ? !b1 : true;
	});

	public static LogicalOperator enor = new LogicalOperator((b0, b1) ->
	{
		return b0 == b1;
	});

	public static LogicalOperator none = null;

	BiPredicate<Boolean, Boolean> bc;

	public LogicalOperator(BiPredicate<Boolean, Boolean> bc)
	{
		this.bc = bc;
	}

	public boolean compare(boolean b0, boolean b1)
	{
		return bc.test(b0, b1);
	}
}
