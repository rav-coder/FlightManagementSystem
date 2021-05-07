package sait.frms.manager;

import java.io.*;
import java.util.*;

import sait.frms.exception.InvalidCitizenshipOrNameException;
import sait.frms.exception.NoMoreSeatsException;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

public class ReservationManager
{
	private ArrayList<Reservation> reservations;
	// binary file bytes
	private static final int RESERVATIONS_SIZE = 331;
	private static final String BINARY_FILE = "res/reservations.bin";
	private static final String MODE = "rw";
	private RandomAccessFile raf;

	public ReservationManager() throws IOException
	{
		this.raf = new RandomAccessFile(BINARY_FILE, MODE);
		reservations = new ArrayList<>();
		populateFromBinary();
	}
	
	//Author: YunZe (David) Wei
	public Reservation makeReservation(Flight flight, String name, String citizenship)
			throws IOException, InvalidCitizenshipOrNameException, NoMoreSeatsException
	{

		Reservation tempRes = new Reservation();
		
		//if statement to make sure the name is not null or empty and alphabets only
		// space in between letters is ok
		if ((!name.matches("^[a-zA-Z]*$") || !citizenship.matches("^[a-zA-Z]*$"))
				|| (name.equals("") || citizenship.equals("")))
		{
			throw new InvalidCitizenshipOrNameException();
		} else
		{
			String reservationCode = "";
			String flightCode = "";
			String airlineName = "";
			String customerName = "";
			String customerCitizenship = "";
			int seats;
			double seatCost = 0;
			boolean active;

			
			flightCode = flight.getCode();
			airlineName = flight.getAirlineName();
			seats = flight.getSeats();
			
			// if statement to check if flight is full
			if (seats == 0)
			{
				throw new NoMoreSeatsException();
			}
			//take a seat off the flight once a reservation is made
			flight.bookSeat();
			seatCost = flight.getCostPerSeat();
		
			customerName = name;
		
			customerCitizenship = citizenship;

			// if no exceptions active is changed to true
			active = true;
			
			//calls method to generate a reservation code 
			reservationCode = this.generateReservationCode(flight);

			// adds the reservation object to the arraylist
			tempRes = new Reservation(reservationCode, flightCode, airlineName, customerName, customerCitizenship,
					active, seatCost);
			
			reservations.add(tempRes);
			
			//writes to binary file
			this.persist();
			
		}

		return tempRes;

	}

	//Author: Rafael Garcia Oporto
	public ArrayList<Reservation> findReservations(String code, String airline, String name) throws Exception
	{
		ArrayList<Reservation> searchResults = new ArrayList<Reservation>();
		if (code != null && airline == null && name == null)
		{
			searchResults.add(findReservationByCode(code));
		} else
		{
			// We need to compare the information given and generate results based on matches
			for (int i = 0; i < reservations.size(); i++)
			{
				// add code match
				if (reservations.get(i).getCode().toLowerCase().equals(code.toLowerCase()) && code != null)
				{
					searchResults.add(reservations.get(i));
					continue;
				}
				// add name match
				if (reservations.get(i).getName().toLowerCase().equals(name.toLowerCase()) && name != null)
				{
					searchResults.add(reservations.get(i));
					continue;
				}
				// add airline match
				if (reservations.get(i).getAirline().toLowerCase().equals(airline.toLowerCase()) && airline != null)
				{
					searchResults.add(reservations.get(i));
					continue;
				}
			}
		}

		return searchResults;

	}
	
	//Author: Saurav Adhikari
	private Exception ReservationCodeNotFoundException(String string)
	{
		// error message and back to the gui
		return null;
	}

	//Author: Saurav Adhikari
	public Reservation findReservationByCode(String code) throws Exception
	{
		boolean testCode = false;
		long position;
		raf.seek(0);
		for (position = 0; position < this.raf.length(); position += RESERVATIONS_SIZE)
		{

			Reservation r = this.readRecord();

			if (r.getCode().toLowerCase().equals(code.toLowerCase()))
			{
				testCode = true;
				return r;
			}
		}

		if (!testCode)
		{
			throw ReservationCodeNotFoundException(
					"Reservcation code " + code + " was not found.\n" + "Please re-enter the code.");
		}
		return null;

	}

	//Author: Saurav Adhikari
	public Reservation readRecord() throws IOException
	{
		Reservation r = new Reservation();
		if (raf.getFilePointer() != raf.length())
		{
			String code = this.raf.readUTF();
			String flightCode = this.raf.readUTF();
			String airline = this.raf.readUTF().trim();
			String name = this.raf.readUTF().trim();
			String citizenship = this.raf.readUTF().trim();
			double cost = this.raf.readDouble();
			boolean active = this.raf.readBoolean();

			r = new Reservation(code, flightCode, airline, name, citizenship, active, cost);

		}

		return r;
	}

	//Author: Saurav Adhikari
	private void populateFromBinary() throws IOException
	{

		for (long position = 0; position < this.raf.length(); position += RESERVATIONS_SIZE)
		{
			this.raf.seek(position);
			Reservation r = this.readRecord();
			// if (r.isActive())
			reservations.add(r);
		}

	}

	//Author: Saurav Adhikari
	public void persist() throws IOException
	{// saves all reservation objects to a binary file on the hard drive.

		raf.seek(0);
		for (int i = 0; i < reservations.size(); i++)
		{
			Reservation r = reservations.get(i);

			String code = String.format("%-5s", r.getCode());
			this.raf.writeUTF(code);

			String flightCode = String.format("%-7s", r.getFlightCode());
			this.raf.writeUTF(flightCode);

			String airline = String.format("%-100s", r.getAirline());
			this.raf.writeUTF(airline);

			String name = String.format("%-100s", r.getName());
			this.raf.writeUTF(name);

			String citizenship = String.format("%-100s", r.getCitizenship());
			this.raf.writeUTF(citizenship);

			this.raf.writeDouble(r.getCost());
			this.raf.writeBoolean(r.isActive());
		}

	}

	//Author: Rafael Garcia Oporto
	private int getAvailableSeats(Flight flight)
	{
		// need exception
		return flight.getSeats();
	}
	
	//Author: YunZe (David) Wei
	private String generateReservationCode(Flight flight)
	{
		String departureCode = "";
		String arrivalCode = "";
		char reservationLetter;
		int reservationNumber;
		int minNumber = 1000;
		int maxNumber = 9999;

		// try catch to make sure the flight objects are not null or empty
		// try {
		departureCode = flight.getFrom();
		arrivalCode = flight.getTo();
		// } catch (NoFlightSelectedException) {
		// error message
		// }
		// checks if the flight is domestic or international
		if (departureCode.charAt(0) == arrivalCode.charAt(0))
		{
			reservationLetter = 'D';
		} else
		{
			reservationLetter = 'I';
		}

		reservationNumber = (int) (Math.random() * (maxNumber - minNumber + 1) + minNumber);

		return String.valueOf(reservationLetter) + reservationNumber;
	}

	//Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	public void updateReservations(Reservation reservation, String name, String citizenship, String active)
			throws Exception
	{
		if ((!name.matches("^[a-zA-Z]*$") || !citizenship.matches("^[a-zA-Z]*$") || !active.matches("^[a-zA-Z]*$"))
				|| (name == "" || citizenship == "" || active == ""))
		{
			throw new InvalidCitizenshipOrNameException();
		} else
		{
			boolean activeCheck = false;

			String tempCode = reservation.getCode().toLowerCase();

			Reservation reservationCheck = new Reservation();

			int tempIndex = 0;

			for (int i = 0; i < reservations.size(); i++)
			{
				// add code match
				if (reservations.get(i).getCode().toLowerCase().equals(tempCode) && tempCode != null)
				{
					tempIndex = i;
					if (active.charAt(0) == 'A')
					{
						activeCheck = true;
					}
				}

			}

			reservationCheck = reservations.get(tempIndex);

			reservationCheck.setName(name);
			reservationCheck.setCitizenship(citizenship);
			reservationCheck.setActive(activeCheck);

			
			//Author: Saurav Adhikari, YunZe (David) Wei
			FlightManager a = new FlightManager();
			ArrayList<Flight> flights = a.getFlights();
			if (!activeCheck)
			{

				for (int i = 0; i < flights.size(); i++)
				{
					if (reservationCheck.getFlightCode().equals(flights.get(i).getCode()))
					{
						flights.get(i).addSeat();
						a.writeToFlights();
					}

				}
			}
			if (active.charAt(0) == ('A') && activeCheck)
			{
				for (int i = 0; i < flights.size(); i++)
				{
					if (reservationCheck.getFlightCode().equals(flights.get(i).getCode()))
					{
						flights.get(i).bookSeat();
						a.writeToFlights();
					}
				}
			}

			this.persist();
		}
	}

}
