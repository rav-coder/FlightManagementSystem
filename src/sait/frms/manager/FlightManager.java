package sait.frms.manager;

import java.io.*;
import java.util.*;

import sait.frms.exception.InvalidFlightCodeException;
import sait.frms.problemdomain.Flight;

public class FlightManager
{
	public final static String WEEKDAY_ANY = "Any";
	public final static String WEEKDAY_SUNDAY = "Sunday";
	public final static String WEEKDAY_MONDAY = "Monday";
	public final static String WEEKDAY_TUESDAY = "Tuesday";
	public final static String WEEKDAY_WEDNESDAY = "Wednesday";
	public final static String WEEKDAY_THURSDAY = "Thursday";
	public final static String WEEKDAY_FRIDAY = "Friday";
	public final static String WEEKDAY_SATURDAY = "Saturday";

	private ArrayList<Flight> flights = new ArrayList<Flight>();
	private ArrayList<String> airports = new ArrayList<String>();
	
	public FlightManager() throws IOException
	{
		this.populateFlights();
		this.populateAirports();
	}

	public ArrayList<Flight> getFlights() throws FileNotFoundException
	{
		return flights;
	}

	public ArrayList<String> getAirports() throws FileNotFoundException
	{
		return airports;
	}
	 //Author: YunZe (David) Wei
	public String findAirportByCode(String code)
	{
		String airport = "";
		//goes through the arraylist and finds the matching airport by code in airports.csv
		for (int i = 0; i < airports.size(); i++)
		{
			String[] tempCode = airports.get(i).split(" ");

			if (code.equals(tempCode[0]))
			{
				airport = tempCode[1];
			}
		}
		return airport;
	}
	
	//Author: Saurav Adhikari
	private Exception FLightCodeNotFoundException(String string)
	{
		// error message and back to the gui
		return null;
	}
	
	//Author: Saurav Adhikari
	public Flight findFlightByCode(String code) throws Exception
	{// 2 saurav
		boolean testCode = false;
		int i;
		for (i = 0; i < flights.size(); i++)
		{
			if (((flights.get(i)).getCode().toLowerCase()).equals(code.toLowerCase()))
			{
				testCode = true;
				break;
			}
		}

		// need an exception because the return is a flight object and need a condition when a
		// matching flight isn't found
		if (testCode)
			return flights.get(i);
		else
			throw FLightCodeNotFoundException(
					"Flight code " + code + " was not found.\n" + "Please re-enter the code.");
	}
	
	//Author: Rafael Garcia Oporto
	public ArrayList<Flight> findFlights(String from, String to, String weekday)
			throws FileNotFoundException, InvalidFlightCodeException
	{// 3 raf
		ArrayList<Flight> matchingFlights = new ArrayList<Flight>();

		if ((weekday != null && from != null && to != null) && (weekday != " " && from != " " && to != " "))
		{
			// We need to compare the from code all the from codes in flights
			for (int i = 0; i < flights.size(); i++)
			{
				// compare from values, move into next step if there's a match
				if (flights.get(i).getFrom().equals(from))
				{
					// from values matched, now we compare to values, move to next step if there's a match
					if (flights.get(i).getTo().equals(to))
					{
						// lastly, we compare weekday. Check that it is not "Any", if it is skip this and add the
						// flight to matchingFlights
						if (!weekday.equals("Any"))
						{
							if (flights.get(i).getWeekday().equals(weekday))
							{
								matchingFlights.add(flights.get(i));
							}
						} else
						{
							matchingFlights.add(flights.get(i));
						}
					}
				}
			}
		} else
		{
			throw new InvalidFlightCodeException();
		}

		return matchingFlights;
	}
	 //Author: Saurav Adhikari, YunZe (David) Wei
	public void writeToFlights() throws IOException
	{
		PrintWriter out = new PrintWriter(new FileWriter("res/flights.csv"));
		for (Flight flight : flights){
			out.println(flight.getCode() + "," + flight.getFrom() + "," + flight.getTo() + "," + flight.getWeekday()
			+ "," + flight.getTime() + "," + flight.getSeats()+ "," + flight.getCostPerSeat());
		}
		out.close();
	}

	//Author: YunZe (David) Wei
	private void populateFlights() throws FileNotFoundException
	{
		Scanner in = new Scanner(new File("res/flights.csv"));
		//while loop to read till end of file
		while (in.hasNext())
		{
			//array to split each field on a comma
			String line = in.nextLine();
			String[] fields = line.split(",");
			//array for holding just the 2 letter code
			String[] airlineCode = fields[0].split("-");
			
			String flightCode = fields[0];
			String airlineName = airlineCode[0];
			String departureCode = fields[1];
			String arrivalCode = fields[2];
			String weekday = fields[3];
			String time = fields[4];
			int seats = Integer.parseInt(fields[5]);
			double seatCost = Double.parseDouble(fields[6]);
			//adds from the file to the arraylist 
			flights.add(
					new Flight(flightCode, airlineName, departureCode, arrivalCode, weekday, time, seats, seatCost));
		}
	}

	//Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	private void populateAirports() throws FileNotFoundException
	{

		Scanner in = new Scanner(new File("res/airports.csv"));
		in.useDelimiter(","); // setup delimiter
		String temp; // string to hold string

		// while loop loads strings into the arraylist
		while (in.hasNext())
		{
			temp = in.next();
			airports.add(temp);
			in.nextLine();
		}

	}
}
