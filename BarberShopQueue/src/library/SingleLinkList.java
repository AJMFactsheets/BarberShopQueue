package library;

/**
 * Models a basic singly-linked list data structure.
 * 
 * @author AJMFactsheets
 *
 * @param <T> Generic Object Type to store in the list
 */
public class SingleLinkList<T> {
	private Node head;
	
	public SingleLinkList() {
		this.head = null;
	}
	
	public SingleLinkList(T data) {
		this.head = new Node(data);
	}
	
	public Node getHead() {
		return head;
	}


	/**
	 * Add a value to the end of the single link list: O(n) runtime
	 * 
	 * @param data Data of generic type "T" to add to the end of the list
	 */
	public void add(T data) {
		
		// If the list is empty, set up the head with the first value
		if (this.head == null) {
			this.head = new Node(data);
		}
		
		// Otherwise the list is full, so scan the entire list to find the last element
		else {
			// Requires a local iterator so the global head does not shift position
			Node iterator = this.head;
			
			// Find the last node in the list
			while (iterator.getNext() != null) {
				iterator = iterator.getNext();
			}
			
			iterator.setNext(new Node(data));
		}
	}
	
	/**
	 * Removes a value from the list
	 * 
	 * @param data Object of Generic Type T to remove
	 * @return Object of Generic Type T which was removed; null if not found
	 */
	public T remove(T data) {
		
		// If the list is empty, just return null
		if (this.head == null) {
			return null;
		}
		
		// If the head of the list is the value to be deleted
		else if (this.head.getData().equals(data)) {
			T deletedData = this.head.getData();
			
			if (this.head.getNext() == null) {
				this.head = null;
			}
			else {
				this.head = this.head.getNext();
			}
			return deletedData;
		}
		
		// Otherwise, scan the entire list until the value to be deleted is found
		else {
			// Requires a local iterator so the global head does not shift position
			Node iterator = this.head;
			
			// Try to find the node with the correct data value
			while (iterator != null && !(iterator.getNext().getData().equals(data))) {
				iterator = iterator.getNext();
			}
			
			// If the value wasn't found, return null
			if (iterator == null) {
				return null;
			}
			// Otherwise, remove and return the value from the list
			else {
				Node deleteNode = iterator.getNext();
				T deleteData = deleteNode.getData();
				
				iterator.setNext(deleteNode.getNext());
				deleteNode = null;
				
				return deleteData;
			}
		}
	}
	
	
	
	/**
	 * Models a basic node which contains data and a pointer to the next node
	 * Intended for use in a single link list data struture
	 * 
	 * @author AJMFactsheets
	 */
	public class Node {
		private T data;
		private Node next;
		
		public Node() {
			this.data = null;
			this.next = null;
		}
		
		public Node(T data) {
			this.data = data;
			this.next = null;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
	}
}
