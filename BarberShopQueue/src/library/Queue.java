package library;

import library.SingleLinkList;

/**
 * Models a simple FIFO queue using the custom Single Link List
 * 
 * @author AJMFactsheets
 *
 * @param <T> Generic Type Object to store in internal Link List
 */
public class Queue<T> {
	private SingleLinkList<T> queue;
	private int size;
	
	public Queue() {
		this.queue = new SingleLinkList<T>();
		this.size = 0;
	}
	
	public Queue(T data) {
		this.queue = new SingleLinkList<T>(data);
		this.size = 1;
	}
	
	/**
	 * @return Current size of queue: O(1) method
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Add object to back of queue
	 * 
	 * @param data Object of generic type T to add
	 */
	public void enqueue(T data) {
		this.queue.add(data);
		this.size++;
	}
	
	/**
	 * Remove object from front of queue
	 * 
	 * @return Object removed of generic type T; null if queue is empty
	 */
	public T dequeue() {
		T data;
		
		data = this.queue.removeFirst();
		
		// Because removeFirst() can return null (list empty)
		if (data != null) {
			this.size--;
		}
		
		return data;
	}

}
