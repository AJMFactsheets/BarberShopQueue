import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import library.*;
import barbershop.*;

public class BarberShopQueue {
	private static final String SHUTDOWN_COMMAND = "shutdown";
	private static final String BARBER_FILE_NAME = "Barbers.txt";

	public static void main(String[] args) {
		
		boolean isStoreOpen = true;
		String input;
		String[] delimitedInput;
		// Read all Barber info from a file
		CustomArrayList<Barber> workingBarbers = populateBarbers();
		
		// Processes user input
		Scanner commandInputReader = new Scanner(System.in);
		
		// Main program loop to accept commands
		while (isStoreOpen) {
			System.out.println("Welcome to our Barber Shop!");
			System.out.println("Type \"Customer checkin <Name>\" to begin!");
			
			
			input = commandInputReader.nextLine();
			
			// Space delimited commands
			
			delimitedInput = input.split(" ");
			
			// All input will be processed as lowercase
			for (int i = 0; i < delimitedInput.length; i++) {
				
				// Assume 3rd word will be the name (2nd since arrays count from 0)
				if (i == 2) {
					continue;
				}
				
				delimitedInput[i] = delimitedInput[i].toLowerCase();
			}
			
			// Process first word
			switch(delimitedInput[0]) {
				case "": {
					processError();
					break;
				}
				
				// Stop main loop and close up shop
				case SHUTDOWN_COMMAND: {
					isStoreOpen = false;
					commandInputReader.close();
					break;
				}
				
				// Commands intended to be used by Barbers
				case "barber": {
					
					// Process second word
					switch(delimitedInput[1]) {
						case "": {
							processError();
						}
						case "checkin": {
							
							// Process third word
							if (delimitedInput[2].equals("")) {
								processError();
							}
							
							// Try to find Barber in list, if no in list, log an error to console
							else {
								int foundPosition = getBarberPosition(workingBarbers, delimitedInput[2]);
								
								if (foundPosition >= 0) {
									workingBarbers.get(foundPosition).barberCheckIn();
								}
								else {
									System.err.println("Could not find barber named " + delimitedInput[2]);
								}
							}
							break;
						}
						
						case "checkout": {
							
							// Process third word
							if (delimitedInput[2].equals("")) {
								processError();
							}
							
							// Try to find Barber in list, if no in list, log an error to console
							else {
								int foundPosition = getBarberPosition(workingBarbers, delimitedInput[2]);
								
								// If the barber was found, toggle working status
								if (foundPosition >= 0) {
									workingBarbers.get(foundPosition).barberCheckOut();
								}
								else {
									System.err.println("Could not find barber named " + delimitedInput[2]);
								}
							}
							break;
						}
						
						// Called by Barber to get his next customer to work on
						case "dequeue": {
							
							// Process third word
							if (delimitedInput[2].equals("")) {
								processError();
							}
							
							// Try to find Barber in list, if no in list, log an error to console
							else {
								int foundPosition = getBarberPosition(workingBarbers, delimitedInput[2]);
								
								// Try to get the next customer for that barber
								if (foundPosition >= 0) {
									Customer currentCustomer = workingBarbers.get(foundPosition).getNextCustomer();
									
									// If there is a next customer
									if (currentCustomer != null) {
										System.out.println("Your next customer's name is " + currentCustomer.getName());
										System.out.println("Your next customer would like the " + currentCustomer.getHairstyle().toString() + " hairstyle");
										System.out.println("Please remember to run \"customer checkout <Name> before getting your next customer\"\n");
									}
									
									// If there is not a next customer this means one of two things:
									else {
										
										// There are no customers waiting in line
										if (workingBarbers.get(foundPosition).isWorking()) {
											System.out.println("You have no customers waiting for you! Time for a break!\n");
										}
										
										// The barber is not clocked in as working
										else {
											System.err.println("Barber " + delimitedInput[2] + " is not currently working");
											System.err.println("Please run \"barber checkin <Name>\" to begin accepting customers\n");
										}
									}
								}
								
								else {
									System.err.println("Could not find barber named " + delimitedInput[2]);
								}
							}
							break;
						}
						
						default: {
							processError();
						}
					}
					break;
				}
				
				// Commands intended to be used by Customers
				case "customer": {
					
					// Process second word
					switch (delimitedInput[1]) {
						case "": {
							processError();
							break;
						}
						
						case "checkin": {
							
							// Process third word
							if (delimitedInput[2].equals("")) {
								processError();
							}
							else {
								
								// For the new Customer Object to later be enqueued
								String customerName = delimitedInput[2];
								Barber customerBarber = null;
								EnumHairstyle customerHairstyle = null;
								int barberListPosition = -1;
								
								boolean askCustomer = true;
								
								System.out.println("Welcome " + delimitedInput[2] + "!");
								
								while (askCustomer) {
									System.out.println("Do you want to see a particular barber? y/n");
									
									// Get first word
									String result = commandInputReader.nextLine().toLowerCase().split(" ")[0];
									
									// Ask the customer for the barber's name and try to locate it
									if (result.startsWith("y")) {
										askCustomer = false;
										boolean askName = true;
										
										while (askName) {
											System.out.println("Enter barber name:");
											
											String inputName = commandInputReader.nextLine();
											// Makes error message actually reflect user input
											String sanitizedInputName = inputName.toLowerCase();
											
											// Search through all barbers to find the name the customer requests
											for (int i = 0; i < workingBarbers.size(); i++) {
												
												// This block runs if the barber is found
												// Duplicates are not handled, so the first barber in the list is taken.
												if (workingBarbers.get(i).getName().equals(sanitizedInputName)) {
													askName = false;
													customerBarber = workingBarbers.get(i);
													barberListPosition = i;
													break;
												}
											}
											
											if (barberListPosition < 0) {
												System.err.println("Could not find barber with name \"" + inputName + "\"");
											}
											
										}
										
									}
									
									// Put customer in shortest barber queue
									else if (result.startsWith("n")) {
										askCustomer = false;
										
										// Position 0 is the barber's position in the list
										// Position 1 is the value of the position
										int[] shortestLine = new int[2];
										shortestLine[0] = -1;
										shortestLine[1] = Integer.MAX_VALUE;
										
										// Search each working barber and use queue size as an estimate
										// Could change if statistics on the time for each haircut were known ahead of time
										for (int i = 0; i < workingBarbers.size(); i++) {
											if (workingBarbers.get(i).getQueueSize() < shortestLine[1]) {
												shortestLine[0] = i;
												shortestLine[1] = workingBarbers.get(i).getQueueSize();
											}
										}
										
										if (shortestLine[0] == -1) {
											throw new IllegalStateException("Could not find empty queue! Too many customers!");
										}
										else {
											// Assign barber to customer
											customerBarber = workingBarbers.get(shortestLine[0]);
											barberListPosition = shortestLine[0];
										}
									}
									
									// Get customer hairstyle type
									askCustomer = true;
									while (askCustomer) {
										System.out.println("Enter the number of the hairstyle you would like to get today: ");
										System.out.println("1. " + EnumHairstyle.STANDARD.toString());
										System.out.println("2. " + EnumHairstyle.PREMIUM.toString());
										System.out.println("3. " + EnumHairstyle.SUPREME.toString());
										
										// Had to resort to parseInt since nextInt caused the Scanner buffer to bug out.
										// Further split the input to parse the first character due to user testing issues.
										String inputHairstyle = commandInputReader.nextLine().toLowerCase();
										char parsedNumber = inputHairstyle.charAt(0);
										
										// If the value is not a digit, print an error and ask for correct input
										if (!(Character.isDigit(parsedNumber))) {
											System.err.println("Please enter either number 1, 2, or 3.");
										}
										
										// The value was a digit, so try to parse it.
										else {
											// Java is annoying sometimes isn't it?
											int hairstyleChoice = Integer.parseInt(Character.toString(parsedNumber));
											
											// Invalid number, so ask again
											if (hairstyleChoice < 1 || hairstyleChoice > 3) {
												System.err.println("Please enter 1, 2, or 3.");
											}
											
											// Valid number, so stop asking and assign hairstyle to customer
											else {
												askCustomer = false;
												
												switch (hairstyleChoice) {
													case 1: {
														customerHairstyle = EnumHairstyle.STANDARD;
														break;
													}
													case 2: {
														customerHairstyle = EnumHairstyle.PREMIUM;
														break;
													}
													case 3: {
														customerHairstyle = EnumHairstyle.SUPREME;
														break;
													}
													default: {
														throw new IllegalStateException("Improper Hairstyle Enum");
													}
												}
											}
										}

									}
									
									// Create Customer Object and add to appropriate Barber Queue
									Customer customer = new Customer(customerName, customerBarber, customerHairstyle);
									
									// Hooray! Finally got the customer in the system! Pray for no NPEs!
									workingBarbers.get(barberListPosition).addCustomer(customer);
									
									System.out.println("\nThank you " + customerName + "! You will be called when your Barber is ready.\n");
								}
								break;
							}
						}
						
						// Currently a dummy command which simply thanks the customer
						case "checkout": {
							
							if (delimitedInput[2].equals("")) {
								processError();
							}
							else {				
								System.out.println("Thank you for visiting our Barber " + delimitedInput[2] + "!");
								System.out.println("Have a nice day!\n");
							}
							
							break;
						}
						
						default: {
							processError();
						}
					}
					break;
				}
				
				default: {
					processError();
				}
			}
		}
		
		System.out.println("Thanks for using BarberShopQueue by AJMFactsheets!");
	}

	
	/**
	 * Reads the IDs and names of a bunch of barbers from a file
	 * 
	 * The file should have ID (integer) Name
	 * Delimited by a space
	 * 
	 * @return Custom Array List of Barber Objects
	 */
	private static CustomArrayList<Barber> populateBarbers() {
		
		CustomArrayList<Barber> barberList = new CustomArrayList<Barber>();
		
		try {
			Scanner barberScanner = new Scanner(new File(BARBER_FILE_NAME));
			
			String line;
			String[] parsedLine;
			
			// Space delimited, first should be an integer, the second a String
			while (barberScanner.hasNext()) {
				line = barberScanner.nextLine().toLowerCase();
				
				parsedLine = line.split(" ");
				
				Barber barber = new Barber(Integer.parseInt(parsedLine[0]), parsedLine[1]);
				
				barberList.add(barber);
			}
			
			barberScanner.close();
			
		} 
		catch (FileNotFoundException e) {
			System.err.println("Could not find file: " + BARBER_FILE_NAME + " in main program directory!");
			e.printStackTrace();
		}
		
		return barberList;
		
	}
	
	/**
	 * Takes a List of working barbers and finds the position of a barber by name: O(N) runtime
	 * Created to reduce code duplication and increase abstraction
	 * 
	 * @param workingBarbers List to search through
	 * @param name Barber's name to search for
	 * @return position of barber in workingBarbers; -1 if not in list
	 */
	private static int getBarberPosition(CustomArrayList<Barber> workingBarbers, String name) {
		int foundPosition = -1;
		
		for (int i = 0; i < workingBarbers.size(); i++) {
			if (workingBarbers.get(i).getName().equals(name)) {
				foundPosition = i;
				break;
			}
		}
		return foundPosition;
	}

	/**
	 * Code block to call whenever there is an error: could be improved to be more specific
	 */
	private static void processError() {
		System.err.println("Incorrect command! Please try again!");
	}
	
}
