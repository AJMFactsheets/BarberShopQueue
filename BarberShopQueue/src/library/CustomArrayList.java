package library;

/**
 * @author AJMFactsheets
 *
 * @param <T> Data Type Object to store in ArrayList 
 */
public class CustomArrayList<T> {
	
	// Underlying resizing array
	private T[] array;
	private int size;
	
	
	/**
	 * Generics don't operate at compile time, so there is always an Object cast involved
	 * 
	 * This method creates a default array of size 2
	 */
	@SuppressWarnings("unchecked")
	public CustomArrayList() {
		this.array = (T[]) new Object[2];
		this.size = 0;
	}
	
	/**
	 * Generics don't operate at compile time, so there is always an Object cast involved
	 * 
	 * This method creates an array with the size entered by the user
	 * 
	 * @param size User defined size of internal array
	 */
	@SuppressWarnings("unchecked")
	public CustomArrayList(int size) {
		this.array = (T[]) new Object[size];
		this.size = 0;
	}
	
	/**
	 * Wrap if a negative index is used. Inspired by Python's slicing behavior :)
	 * @param position Original position to check
	 * @return Wraped value if position is negative; same value if position is positive
	 */
	protected int wrapPosition(int position) {
		if (position < 0 && position >= -this.size) {
			position = this.size + position;
		}
		
		return position;
	}
	
	/**
	 * Return an object based on position
	 * 
	 * @param position Slot in internal array
	 * @return Generic Object of type T at position
	 */
	public T get(int position) {
		position = this.wrapPosition(position);
		
		return this.array[position];
	}
	
	
	/**
	 * Set an object to a custom object based on position
	 * 
	 * @param position Slot in internal array
	 * @param data Generic Object of type T to set position to
	 */
	public void set(int position, T data) {
		
		position = this.wrapPosition(position);
		
		// Only increase the size if the position was already null.
		if (this.array[position] == null) {
			this.size++;
		}
		
		this.array[position] = data;
	}
	
	/**
	 * Return size of internal array
	 * @return size of internal array
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Finds the first empty position in the internal array
	 * @return position of first empty slot in internal array or -1 if array is full
	 */
	protected int findFirstFreeSpot() {
		for (int i = 0; i < this.array.length; i++) {
			if (this.array[i] == null) {
				return i;
			}
		}
		return -1;
	}
	
	
	/**
	 * Attempt to add the object in the internal array, reallocating if too full
	 * @param data Object to add to internal array
	 */
	public void add(T data) {
		
		int firstFreeSpot = findFirstFreeSpot();
		
		// If current internal array is full, allocate a larger one
		if (firstFreeSpot < 0) {
			
			// Since the array is full, the first free spot will be
			// the next position outside of the current array bounds
			firstFreeSpot = this.array.length;
			
			// Create new array with double the capacity
			@SuppressWarnings("unchecked")
			T[] newArray = (T[]) new Object[this.array.length * 2];
			
			// Copy old array elements to new array
			for (int i = 0; i < this.array.length; i++) {
				newArray[i] = this.array[i];
			}
			
			// Set internal array to be internal array
			this.array = newArray;
			
		}
		
		// Place the data in the first available spot
		this.array[firstFreeSpot] = data;
		this.size++;
	}
	
	/**
	 * Removes data from list
	 * @param data Object to remove
	 */
	public void remove(T data) {
		int position = -1;
		
		// Find object in internal array, otherwise position is -1.
		for (int i = 0; i < this.size; i++) {
			
			if (this.array[i] != null && this.array[i].equals(data)) {
				position = i;
			}
		}
		
		if (position > -1) {
			this.array[position] = null;
		}
		// Called if the object to be deleted could not be found in the internal array.
		else {
			throw new ArrayIndexOutOfBoundsException("Could not find the following object in ArrayList: " + data.toString());
		}
	}
}
