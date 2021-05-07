package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import sait.frms.exception.InvalidCitizenshipOrNameException;
import sait.frms.exception.InvalidFlightCodeException;
import sait.frms.exception.NoMoreSeatsException;
import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the flights tab.
 * 
 */
public class FlightsTab extends TabBase
{
	/**
	 * Instance of flight manager.
	 */
	private FlightManager flightManager;

	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;

	/**
	 * List of flights.
	 */
	private JList<Flight> flightsList;

	private JComboBox status;
	private JComboBox airportChoicesTo;
	private JComboBox airportChoicesFrom;
	private JTextField flight;
	private JTextField airline;
	private JTextField day;
	private JTextField time;
	private JTextField cost;
	private JTextField name;
	private JTextField citizenship;
	private String daySelection;
	private String toSelection;
	private String fromSelection;
	private Flight selectedFlight = new Flight();

	private DefaultListModel<Flight> flightsModel;

	/**
	 * Creates the components for flights tab.
	 */
	/**
	 * Creates the components for flights tab.
	 * 
	 * @param flightManager
	 *            Instance of FlightManager.
	 * @param reservationManager
	 *            Instance of ReservationManager
	 * @throws FileNotFoundException
	 */
	public FlightsTab(FlightManager flightManager, ReservationManager reservationManager) throws FileNotFoundException
	{
		this.flightManager = flightManager;
		this.reservationManager = reservationManager;

		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel()
	{
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Creates the center panel.
	 * 
	 * @return JPanel that goes in center.
	 */
	private JPanel createCenterPanel()
	{
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);

		// User can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.flightsList);

		flightsList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane);

		return panel;
	}

	/**
	 * creates east panel
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 * @return panel
	 */
	private JPanel createEastPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 10));

		JPanel northPanel = new JPanel();
		JLabel title = new JLabel("Reserve", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		northPanel.add(title);
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel messageFlight = new JLabel("Flight: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(messageFlight, gbc);
		flight = new JTextField(15);
		flight.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(flight, gbc);

		JLabel messageAirline = new JLabel("Airline: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageAirline, gbc);
		airline = new JTextField(15);
		airline.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(airline, gbc);

		JLabel messageDay = new JLabel("Day: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageDay, gbc);
		day = new JTextField(15);
		day.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 2;
		centerPanel.add(day, gbc);

		JLabel messageTime = new JLabel("Time: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageTime, gbc);
		time = new JTextField(15);
		time.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 3;
		centerPanel.add(time, gbc);

		JLabel messageCost = new JLabel("Cost: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageCost, gbc);

		cost = new JTextField(15);
		cost.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 4;
		centerPanel.add(cost, gbc);

		JLabel messageName = new JLabel("Name: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageName, gbc);

		name = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 5;
		centerPanel.add(name, gbc);

		JLabel messageCitizenship = new JLabel("CitizenShip: ");
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageCitizenship, gbc);

		citizenship = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 6;
		centerPanel.add(citizenship, gbc);

		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		JButton reserveButton = new JButton("Reserve");
		reserveButton.addActionListener(new ReservationListener());
		southPanel.add(reserveButton);
		panel.add(southPanel, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * creates south panel
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 * @return panel
	 * @throws FileNotFoundException
	 */
	private JPanel createSouthPanel() throws FileNotFoundException
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 10));

		JPanel northPanel = new JPanel();
		JLabel title = new JLabel("Flight Finder", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		northPanel.add(title);
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel airportPickerFrom = new JLabel("From: ");
		ArrayList<String> tempAirports = flightManager.getAirports();
		tempAirports.add(0, " ");
		String[] airports = tempAirports.toArray(new String[tempAirports.size()]);
		airportChoicesFrom = new JComboBox(airports);
		airportChoicesFrom.addActionListener(new ComboBoxListenerFrom());
		airportChoicesFrom.setPreferredSize(new Dimension(400, 25));
		airportChoicesFrom.setPrototypeDisplayValue(600);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(airportPickerFrom, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(airportChoicesFrom, gbc);

		JLabel airportPickerTo = new JLabel("To: ");
		airportChoicesTo = new JComboBox(airports);
		airportChoicesTo.addActionListener(new ComboBoxListenerTo());
		airportChoicesTo.setPreferredSize(new Dimension(400, 25));
		airportChoicesTo.setPrototypeDisplayValue(600);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(airportPickerTo, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(airportChoicesTo, gbc);

		JLabel dayPicker = new JLabel("Day: ");
		String[] weekDays = { "", FlightManager.WEEKDAY_ANY, FlightManager.WEEKDAY_MONDAY,
				FlightManager.WEEKDAY_TUESDAY, FlightManager.WEEKDAY_WEDNESDAY, FlightManager.WEEKDAY_THURSDAY,
				FlightManager.WEEKDAY_FRIDAY, FlightManager.WEEKDAY_SATURDAY, FlightManager.WEEKDAY_SUNDAY };
		status = new JComboBox(weekDays);
		status.setPreferredSize(new Dimension(400, 25));
		status.setPrototypeDisplayValue(600);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(dayPicker, gbc);
		status.addActionListener(new ComboBoxListener());
		gbc.gridx = 1;
		gbc.gridy = 2;
		centerPanel.add(status, gbc);

		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		JButton findButton = new JButton("Find Flights");
		findButton.addActionListener(new FindFlightsListner());
		southPanel.add(findButton);
		panel.add(southPanel, BorderLayout.SOUTH);

		return panel;
	}

	/**
	 * Action listener for retrieving what the customer selected for day of flight they want
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 */
	private class ComboBoxListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			daySelection = (String) status.getSelectedItem();
		}
	}

	/**
	 * Action listener for retrieving which airport the customer wants to fly to
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 */
	private class ComboBoxListenerTo implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			toSelection = (String) airportChoicesTo.getSelectedItem();
		}
	}
	
	/**
	 * Action listener for retrieving which airport the customer wants to fly from
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 */
	private class ComboBoxListenerFrom implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			fromSelection = (String) airportChoicesFrom.getSelectedItem();
		}
	}
	
	/**
	 * Action listener for retrieving the available flights from flights.csv file based on what the customer selected
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 */
	private class FindFlightsListner implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			//clearing the default list model and what it showed on the previous search
			flightsList.clearSelection();
			flightsModel.clear();
			String actionCommand = e.getActionCommand();
			ArrayList<Flight> flightArray = new ArrayList<>();
			//matching the customer selection to the flights file we have and catching any exceptions
			try
			{

				flightArray = flightManager.findFlights(fromSelection, toSelection, daySelection);
				
				for (int i = 0; i < flightArray.size(); i++)
				{
					// if a match is found add it to the default list model
					flightsModel.addElement(flightArray.get(i));
					
				}
			} catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
			} catch (InvalidFlightCodeException e1)
			{

				JOptionPane.showMessageDialog(null, "One or more invalid flight fields selected");
				e1.printStackTrace();
			}
			
			//Removing the previous selections once the customer trys to find new flights
			flight.setText(" ");
			airline.setText(" ");
			day.setText(" ");
			time.setText(" ");
			cost.setText(" ");
			name.setText(" ");
			citizenship.setText(" ");
		}

	}

	private class MyListSelectionListener implements ListSelectionListener
	{
		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			//Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
			
			/*Displays the selected flight from the jlist to the east panel fields as long as its within
			 * the lists index
			 */
			int temp = flightsList.getSelectedIndex();
			if (temp != -1)
			{
				selectedFlight = flightsModel.getElementAt(temp);
			}
			String tempAirline = selectedFlight.getAirlineName();
			String tempFlightCode = selectedFlight.getCode();
			String tempDay = selectedFlight.getWeekday();
			String tempTime = selectedFlight.getTime();
			double tempCost = selectedFlight.getCostPerSeat();
			flight.setText(tempFlightCode);
			airline.setText(tempAirline);
			day.setText(tempDay);
			time.setText(tempTime);
			cost.setText(tempCost + "");
			temp = 0;

		}

	}
	/**
	 * Action listener for reserving the flight the customer has selected
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 */
	private class ReservationListener implements ActionListener
	{
		// have to add listener to the "Reserve" button
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			Reservation tempReservation = new Reservation();
			//making the reservation based on what flight the user selected and has entered for their name and 
			// citizenship
			//if the name or citizenship is null or empty exception will be thrown
			//if there are no more seats on the flight, exception will be thrown
			try
			{
				tempReservation = reservationManager.makeReservation(selectedFlight, name.getText(),
						citizenship.getText());
				flightManager.writeToFlights();
				JOptionPane.showMessageDialog(null,
						"Reservation made! Your Reservation code is " + tempReservation.getCode());
			} catch (IOException e1)
			{
				System.out.println("I/O Exception");
				e1.printStackTrace();
			} catch (InvalidCitizenshipOrNameException e1)
			{
				JOptionPane.showMessageDialog(null, "Invalid Name or Citizenship entry, please enter again");
				e1.printStackTrace();
			} catch (NoMoreSeatsException e1)
			{
				JOptionPane.showMessageDialog(null, "No more seats available for this flight");
				e1.printStackTrace();
			}
			String actionCommand = e.getActionCommand();

		}
	}
}