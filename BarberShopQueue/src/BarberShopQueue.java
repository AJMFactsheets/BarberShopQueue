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
			System.out.println(mylist.get(i));
		}

	}

}
