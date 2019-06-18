package barbershop;

/**
 * Models a basic customer waiting in the Barber Shop
 * 
 * @author AJMFactsheets
 *
 */
public class Customer {
	private String name;
	private Barber assignedBarber;
	private EnumHairstyle hairstyle;
	
	public Customer() {
		this.name = "";
		this.assignedBarber = null;
		this.hairstyle = EnumHairstyle.STANDARD;
	}
	
	public Customer(String name) {
		this.name = name;
		this.assignedBarber = null;
		this.hairstyle = EnumHairstyle.STANDARD;
	}
	
	public Customer(String name, Barber barber) {
		this.name = name;
		this.assignedBarber = barber;
		this.hairstyle = EnumHairstyle.STANDARD;
	}
	
	public Customer(String name, Barber barber, EnumHairstyle hairstyle) {
		this.name = name;
		this.assignedBarber = barber;
		this.hairstyle = hairstyle;
	}

	public String getName() {
		return name;
	}

	public Barber getAssignedBarber() {
		return assignedBarber;
	}

	public EnumHairstyle getHairstyle() {
		return hairstyle;
	}
	
}
