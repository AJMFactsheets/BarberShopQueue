import library.*;

public class BarberShopQueue {

	public static void main(String[] args) {
		CustomArrayList<Integer> mylist = new CustomArrayList<>();
		
		mylist.add(7);
		mylist.add(5);
		mylist.add(3);
		mylist.add(1);
		mylist.add(-1);
		
		mylist.remove(3);
		//mylist.remove(-100);
		
		
		for (int i = 0; i < mylist.size(); i++) {
			System.out.println(mylist.get(i) == null ? null : mylist.get(i).toString());
		}
		
		System.out.println("\n" + mylist.size()); 
		
		SingleLinkList<Integer> myList = new SingleLinkList<>();
		
		myList.add(5);
		myList.add(12);
		myList.add(3);
		myList.add(-700);
		myList.add(7);
		myList.add(4);
		
		myList.remove(3);
		
		SingleLinkList<Integer>.Node iterator = myList.getHead();
		
		while(iterator != null) {
			System.out.println(iterator.getData());
			iterator = iterator.getNext();
		} 
		
		Queue<Integer> q = new Queue<>();
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);
		
		Integer i = q.dequeue();
		while (i != null) {
			System.out.println(i);
			i = q.dequeue();
		}
		System.out.println(q.dequeue());
		q.enqueue(55);
		System.out.println(q.dequeue());
	}

}
