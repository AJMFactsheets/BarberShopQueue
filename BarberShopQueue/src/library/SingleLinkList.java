package library;

public class SingleLinkList {

	
	
	private class Node<T> {
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
	}
}
