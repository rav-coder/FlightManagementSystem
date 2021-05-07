package sait.frms.problemdomain;
/**
 * creates reservation object
 * 
 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
 */
public class Reservation
{

	private String code;
	private String flightCode;
	private String airline;
	private String name;
	private String citizenship;
	private boolean active;
	private double cost;

	public Reservation()
	{

	}

	public Reservation(String code, String flightCode, String airline, String name, String citizenship, boolean active,
			double cost)
	{
		super();
		this.code = code;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citizenship = citizenship;
		this.active = active;
		this.cost = cost;
	}

	public String getCode()
	{
		return code;
	}

	public String getFlightCode()
	{
		return flightCode;
	}

	public String getAirline()
	{
		return airline;
	}

	public String getName()
	{
		return name;
	}

	public String getCitizenship()
	{
		return citizenship;
	}

	public double getCost()
	{
		return cost;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setCitizenship(String citizenship)
	{
		this.citizenship = citizenship;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	@Override
	public String toString()
	{
		return code;
	}
	
	

}
