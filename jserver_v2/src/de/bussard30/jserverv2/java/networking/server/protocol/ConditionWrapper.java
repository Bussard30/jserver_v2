package de.bussard30.jserverv2.java.networking.server.protocol;

public class ConditionWrapper
{
	private final Condition c;
	private final LogicalOperator lO;

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
