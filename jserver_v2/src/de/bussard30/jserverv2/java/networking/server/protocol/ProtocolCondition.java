package de.bussard30.jserverv2.java.networking.server.protocol;

public class ProtocolCondition 
{
	
	private NetworkPhase target;
	private ConditionWrapper[] conditions;
	
	public ProtocolCondition(NetworkPhase target, ConditionWrapper... conditions) 
	{
		this.target = target;
		this.conditions = conditions;
	}
	
	public NetworkPhase getTargetPhase()
	{
		return target;
	}
	
	public ConditionWrapper[] getConditions()
	{
		return conditions;
	}
}
