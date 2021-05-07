package sait.frms.problemdomain;

import java.io.FileNotFoundException;

/**
 * creates flight object
 * 
 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
 */
public class Flight
{

	private String code;
	private String airlineName;
	private String from;
	private String to;
	private String weekday;
	private String time;
	private int seats;
	private double costPerSeat;

	public Flight()
	{

	}

	public Flight(String code, String airlineName, String from, String to, String weekday, String time, int seats,
			double costPerSeat)
	{
		super();
		this.code = code;
		this.airlineName = airlineName;
		this.from = from;
		this.to = to;
		this.weekday = weekday;
		this.time = time;
		this.seats = seats;
		this.costPerSeat = costPerSeat;
	}

	public String getCode()
	{
		return code;
	}

	public String getAirlineName()
	{
		parseCode(airlineName);
		return airlineName;
	}

	public String getFrom()
	{
		return from;
	}

	public String getTo()
	{
		return to;
	}

	public String getWeekday()
	{
		return weekday;
	}

	public String getTime()
	{
		return time;
	}

	public int getSeats()
	{
		return seats;
	}
	
	public void bookSeat()
	{
		seats -= 1;
	}
	
	public void addSeat()
	{
		seats += 1;
	}

	public double getCostPerSeat()
	{
		return costPerSeat;
	}

	public boolean isDomestic()
	{
		boolean check;
		
		if (this.from.charAt(0) == 'Y' && this.to.charAt(0) == 'Y')
		{
			check = true;
		} else
		{
			check = false;
		}
		
		return check;
	}
	//Rafael Garcia Oporto
	private void parseCode(String code)
	{
		//Convert code into airlineName
		if(code.charAt(0) == 'O') {
			airlineName = "Otto Airlines";
		}
		else if(code.charAt(0) == 'C') {
			airlineName = "Conned Air";
		}
		else if(code.charAt(0) == 'T') {
			airlineName = "Try a Bus Airways";
		}
		else if(code.charAt(0) == 'V') {
			airlineName = "Vertical Airways";
		}
	}

	@Override
	public String toString()
	{
		return code + ", From:" + from + ", To:"+ to + ", Day:" + weekday + ", Cost:" + costPerSeat;
	}
}
