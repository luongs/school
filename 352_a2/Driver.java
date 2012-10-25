import java.io.IOException;


public class Driver {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		DQ a = new DQ();
		
		a.addFirst(3);					// Stop first value from disappearing when calling addFirst and last. 
		a.addFirst(3);
		int peek=a.peekFirst();
		int temp=a.removeFirst();
		
		/*a.addFirst(3);
		a.addFirst(3);
		a.addFirst(3);
		a.addFirst(3);
		a.addFirst(3);
		a.addFirst(3);
		a.addFirst(3);
		a.addFirst(3);
		a.addFirst(3);*/
		a.addLast(5);
		a.addLast(5);
		a.addLast(5);
		a.addLast(5);
		
		int peekLast=a.peekLast();
		int lTemp=a.removeLast();
		
		if(a.isEmpty())
			System.out.println("Array is empty");
		else
			System.out.println("Not empty");
		
		System.out.print("(");
		for(int i=0; i<a.getSize(); i++)
			System.out.print(a.getElement()[i]+", ");
		System.out.print(") \n");
		
		
		a.truncate();
		System.out.println("__________________");
		System.out.println("Truncated array");
		for(int i=0; i<a.getSize(); i++)
			System.out.println(a.getElement()[i]);
		
		System.out.println("__________________");
		System.out.println("Peeked first value "+peek);
		System.out.println("Peeked last value "+peekLast);
		System.out.println("Removed first value "+temp);
		System.out.println("Removed last value "+lTemp);
	}
	
	

}
