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
			System.out.println("Type \"Customer checkin <Name> <Preferred Barber> \" to begin!");
			
			// All input will be processed as lowercase
			input = commandInputReader.nextLine().toLowerCase();
			
			// Space delimited commands
			delimitedInput = input.split(" ");
			
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
								int foundPosition = -1;
								
								for (int i = 0; i < workingBarbers.size(); i++) {
									if (workingBarbers.get(i).getName().equals(delimitedInput[2])) {
										foundPosition = i;
									}
								}
								
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
							// TODO duplicated code
							else {
								int foundPosition = -1;
								
								for (int i = 0; i < workingBarbers.size(); i++) {
									if (workingBarbers.get(i).getName().equals(delimitedInput[2])) {
										foundPosition = i;
									}
								}
								
								if (foundPosition >= 0) {
									workingBarbers.get(foundPosition).barberCheckOut();
								}
								else {
									System.err.println("Could not find barber named " + delimitedInput[2]);
								}
							}
							break;
						}
					}
					break;
				}
				
				// Commands intended to be used by Customers
				case "customer": {
					
					// Process second word
					switch(delimitedInput[1]) {
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
								
								boolean askCustomer = true;
								System.out.println("Welcome " + delimitedInput[2] + "!");
								
								while (askCustomer) {
									System.out.println("Do you want to see a particular barber? y/n");
									
									// Get first word
									String result = commandInputReader.nextLine().toLowerCase().split(" ")[0];
									
									if (result.startsWith("y")) {
										askCustomer = false;
										System.out.println("Enter barber name:");
										//TODO: finish code in these blocks
										
									}
									else if (result.startsWith("n")) {
										askCustomer = false;
									}
								}
								break;
							}
						}
						
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
					}
					break;
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
	 * Code block to call whenever there is an error: could be improved to be more specific
	 */
	private static void processError() {
		System.err.println("Incorrect command! Please try again!");
	}

}
