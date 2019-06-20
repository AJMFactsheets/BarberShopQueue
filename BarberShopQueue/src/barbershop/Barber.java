package barbershop;
import library.Queue;

/**
 * Models a basic Barber working in a Barber Shop
 * 
 * @author AJMFactsheets
 *
 */
public class Barber {
	private int id;
	private String name;
	private boolean isWorking;
	private Queue<Customer> barberQueue;
	
	// Null constructor, probably shouldn't ever be called
	public Barber() {
		this.id = 0;
		this.name = "";
		this.isWorking = false;
		this.barberQueue = new Queue<Customer>();
	}
	
	// Basic constructor, called when Barber Shop first opens
	public Barber(int id, String name) {
		this.id = id;
		this.name = name;
		this.isWorking = false;
		this.barberQueue = new Queue<Customer>();
	}
	
	// Basic constructor, called if the Barber Shop is already open
	public Barber(int id, String name, boolean isWorking) {
		this.id = id;
		this.name = name;
		this.isWorking = isWorking;
		this.barberQueue = new Queue<Customer>();
	}
	
	// Getters for various methods, no setters for security purposes
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isWorking() {
		return isWorking;
	}

	/**
	 * Called when the Barber clocks in for work
	 */
	public void barberCheckIn() {
		this.isWorking = true;
	}
	
	/**
	 * The current length of the barber's queue
	 * The size of his waitlist
	 * 
	 * @return Size of barber's queue
	 */
	public int getQueueSize() {
		return this.barberQueue.getSize();
	}
	
	/**
	 * Called when the Barber clocks out for work
	 */
	public void barberCheckOut() {
		this.isWorking = false;
	}
	
	/**
	 * Called when a customer requests a particular barber
	 * 
	 * @param customer Customer who is requesting service from this barber
	 */
	public void addCustomer(Customer customer) {
		this.barberQueue.enqueue(customer);
	}
	
	/**
	 * Called after the barber finishes with a customer so he can get the next customer
	 * 
	 * The barber must be working so that the next customer can be admitted.
	 * 
	 * @return Next customer waiting in line, null if line is empty
	 */
	public Customer getNextCustomer() {
		return this.isWorking ? this.barberQueue.dequeue() : null;
	}
}
