package de.bussard30.jserverv2.networking.logger;

public class LogWrapper
{
	private String message;
	private Object caster;
	private String time;
	private String type;
	private String ip;

	public LogWrapper(String message, String time, String type, Object caster)
	{
		this.message = message;
		this.caster = caster;
		this.time = time;
		this.type = type;
		this.ip = null;
	}

	public LogWrapper(String message, String time, String type, String ip, Object caster)
	{
		this.message = message;
		this.caster = caster;
		this.time = time;
		this.type = type;
		this.ip = ip;
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

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getIP()
	{
		return ip;
	}

	public void setIP(String ip)
	{
		this.ip = ip;
	}
}
