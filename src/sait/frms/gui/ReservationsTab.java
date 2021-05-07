package sait.frms.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sait.frms.exception.InvalidCitizenshipOrNameException;
import sait.frms.exception.InvalidFlightCodeException;
import sait.frms.manager.FlightManager;
import sait.frms.manager.ReservationManager;
import sait.frms.problemdomain.Flight;
import sait.frms.problemdomain.Reservation;

/**
 * Holds the components for the reservations tab.
 * 
 */
public class ReservationsTab extends TabBase
{
	/**
	 * Instance of reservation manager.
	 */
	private ReservationManager reservationManager;
	private DefaultListModel<Reservation> reservationModel;

	private JTextField eastpanelcode;
	private JTextField flight;
	private JTextField eastpanelairline;
	private JTextField cost;
	private JTextField eastpanelname;
	private JTextField citizenship;

	private JTextField southpanelcode;
	private JTextField southpanelname;
	private JTextField southpanelairline;
	
	private JComboBox status;

	String searchCode;
	String searchName;
	String searchAirline;
	
	private Reservation selectedReservation = new Reservation();

	private JList<Reservation> reservationsList;

	/**
	 * Creates the components for reservations tab.
	 */
	public ReservationsTab(ReservationManager reservationManager)
	{
		this.reservationManager = reservationManager;
		panel.setLayout(new BorderLayout(20, 5));

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel eastPanel = createEastPanel();
		panel.add(eastPanel, BorderLayout.EAST);

		String[] reservations = { "1", "2", "3" };
		reservationsList = new JList(reservations);
		reservationsList.setFixedCellWidth(450);

		/*
		 * JPanel westPanel = new JPanel(); String[] reservations = { "1", "2", "3" }; JList
		 * reservation = new JList(reservations); reservation.setFixedCellWidth(450); //
		 * reservation.setFixedCellHeight(150); westPanel.add(reservation); panel.add(westPanel,
		 * BorderLayout.WEST);
		 */

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);
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

		reservationModel = new DefaultListModel<>();
		reservationsList = new JList<>(reservationModel);

		// User can only select one item at a time.
		reservationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.reservationsList);

		reservationsList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane);

		return panel;
	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel()
	{
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}
	
	/**
	 * creates east panel
	 * 
	 * @Author: Saurav Adhikari
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

		JLabel messageCode = new JLabel("Code: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(messageCode, gbc);
		eastpanelcode = new JTextField(15);
		eastpanelcode.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(eastpanelcode, gbc);

		JLabel messageFlight = new JLabel("Flight: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageFlight, gbc);
		flight = new JTextField(15);
		flight.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 1;
		centerPanel.add(flight, gbc);

		JLabel messageAirline = new JLabel("Airline: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageAirline, gbc);
		eastpanelairline = new JTextField(15);
		eastpanelairline.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 2;
		centerPanel.add(eastpanelairline, gbc);

		JLabel messageCost = new JLabel("Cost: ");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageCost, gbc);
		cost = new JTextField(15);
		cost.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 3;
		centerPanel.add(cost, gbc);

		JLabel messageName = new JLabel("Name: ");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageName, gbc);

		eastpanelname = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 4;
		centerPanel.add(eastpanelname, gbc);

		JLabel messageCitizenship = new JLabel("Citizenship: ");
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageCitizenship, gbc);

		citizenship = new JTextField(15);
		gbc.gridx = 1;
		gbc.gridy = 5;
		centerPanel.add(citizenship, gbc);

		JLabel messageStatus = new JLabel("Status: ");
		String[] choice = {" ", "Active", "Inactive" };
		status = new JComboBox(choice);
		status.setPreferredSize(new Dimension(160, 25));
		status.setPrototypeDisplayValue(600);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageStatus, gbc);

		gbc.gridx = 1;
		gbc.gridy = 6;
		centerPanel.add(status, gbc);

		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new UpdateReservationListener());
		southPanel.add(updateButton);
		panel.add(southPanel, BorderLayout.SOUTH);

		return panel;
	}
	
	/**
	 * creates south panel
	 * 
	 * @Author: Saurav Adhikari
	 * @return panel
	 * @throws FileNotFoundException
	 */
	private JPanel createSouthPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 10));

		JPanel northPanel = new JPanel();
		JLabel title = new JLabel("Search", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 25));
		northPanel.add(title);
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JLabel messageCode = new JLabel("Code: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		centerPanel.add(messageCode, gbc);

		southpanelcode = new JTextField(40);
		gbc.gridx = 1;
		gbc.gridy = 0;
		centerPanel.add(southpanelcode, gbc);

		JLabel messageAirline = new JLabel("Airline: ");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(messageAirline, gbc);

		southpanelairline = new JTextField(40);
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(southpanelairline, gbc);

		JLabel messageName = new JLabel("Name: ");
		gbc.gridx = 0;
		gbc.gridy = 2;
		centerPanel.add(messageName, gbc);

		southpanelname = new JTextField(40);
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		centerPanel.add(southpanelname, gbc);

		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		JButton findButton = new JButton("Find Reservations");
		findButton.addActionListener(new FindReservationButtonListener());
		southPanel.add(findButton);
		panel.add(southPanel, BorderLayout.SOUTH);

		return panel;
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
			
			/*Displays the selected reservation from the jlist to the east panel fields as long as its within
			 * the lists index
			 */
			int temp = reservationsList.getSelectedIndex();
			if (temp != -1) {
			selectedReservation = reservationModel.getElementAt(temp);
			}
			String tempReservationCode = selectedReservation.getCode();
			String tempFlightCode = selectedReservation.getFlightCode();
			String tempAirline = selectedReservation.getAirline();
			double tempCost = selectedReservation.getCost();
			String tempName = selectedReservation.getName();
			String tempCitizenship = selectedReservation.getCitizenship();
			boolean tempActive = selectedReservation.isActive();
			
			//sets the east panel text fields based on whats selected
			eastpanelcode.setText(tempReservationCode);
			flight.setText(tempFlightCode);
			eastpanelairline.setText(tempAirline);
			cost.setText(tempCost+"");
			eastpanelname.setText(tempName);
			citizenship.setText(tempCitizenship);
			if(tempActive) {
			status.setSelectedIndex(1);
			}else {
				status.setSelectedIndex(2);
			}
			
		}

	}
	/**
	 * Action listener for retrieving the reservation based on what the user searched
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 */
	private class FindReservationButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			//clear previous search
			reservationsList.clearSelection();
			reservationModel.clear();
			searchName = southpanelname.getText();
			searchCode = southpanelcode.getText();
			searchAirline = southpanelairline.getText();

			ArrayList<Reservation> tempReservation = new ArrayList<>();
			//finds the reservation based on what the user searched
			try
			{
				tempReservation = reservationManager.findReservations(searchCode, searchAirline, searchName);
				for (int i = 0; i < tempReservation.size(); i++)
				{
					reservationModel.addElement(tempReservation.get(i));
					
				}
			} catch (IOException e1)
			{
				System.out.println("I/O Exception");
				e1.printStackTrace();
			} catch (Exception e1)
			{
				
				e1.printStackTrace();
			}
		
			String actionCommand = e.getActionCommand();
			
			//clears the eats panel text fields
			eastpanelcode.setText(" ");
			flight.setText(" ");
			eastpanelairline.setText(" ");
			cost.setText(" ");
			eastpanelname.setText(" ");
			citizenship.setText(" ");
			status.setSelectedIndex(0);

		}
	}
	/**
	 * Action listener for updating the reservation with the update button
	 * 
	 * @Author: Saurav Adhikari, YunZe (David) Wei, Rafael Garcia Oporto
	 */
	private class UpdateReservationListener implements ActionListener
	{
		// have to add listener to the "Update" button
		@Override
		public void actionPerformed(ActionEvent e)
		{

			// MyListSelectionListener
			Reservation tempReservation = new Reservation();
			//attempts to update the reservation
			//error will be thrown if citizenship, name or status is empty or null
			try
			{
				reservationManager.updateReservations(selectedReservation, eastpanelname.getText(),
						citizenship.getText(), (String) status.getSelectedItem());
				JOptionPane.showMessageDialog(null,
						"Your Reservation has been updated!");
			} catch (InvalidCitizenshipOrNameException e1)
			{
				
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,
						"One or more invalid update reservation options entered, please try again");
				e1.printStackTrace();
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
			
			
			String actionCommand = e.getActionCommand();

		}
	}


}

