package de.jserverv2.bussard30.networking.logger;

public class LogWrapper
{
	private String message;
	private Object caster;

	public LogWrapper(String message, Object caster)
	{
		this.message = message;
		this.caster = caster;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public Object getCaster()
	{
		return caster;
	}

	public void setCaster(Object caster)
	{
		this.caster = caster;
	}
}
