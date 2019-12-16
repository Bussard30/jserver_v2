package networking.server.protocol;

public class ConditionWrapper
{
	private Condition c;
	private LogicalOperator lO;

	public ConditionWrapper(Condition c, LogicalOperator lO)
	{
		this.c = c;
		this.lO = lO;
	}

	public Condition getCondition()
	{
		return c;
	}

	public LogicalOperator getLogicalOperator()
	{
		return lO;
	}
}
