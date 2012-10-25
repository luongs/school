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
		a.addLast(5);
		a.addLast(5);
		a.addLast(5);
		a.addLast(5);
		
		
		System.out.println("Testing methods within DQ program ");
		System.out.println("Initial test array: ");
		a.print();
	
		System.out.println("__________________");
		System.out.println("isEmpty method call: ");
		if(a.isEmpty()) 	System.out.println("Array is empty");
		else		System.out.println("Array is not empty");
		
		System.out.println("__________________");
		System.out.println("Peek first method: ");
		int peek=a.peekFirst();
		System.out.println("Peeked first value "+peek);
		a.print();
		
		System.out.println("__________________");
		System.out.println("Peek last method: ");
		int peekLast=a.peekLast();
		System.out.println("Peeked last value "+peek);
		a.print();
		
		System.out.println("__________________");
		System.out.println("Remove first method: ");
		int temp=a.removeFirst();
		a.print();
		System.out.println("Removed first value "+temp);
		
		System.out.println("__________________");
		System.out.println("Remove last method: ");
		int lTemp=a.removeLast();
		a.print();
		System.out.println("Removed last value "+lTemp);
		
		System.out.println("__________________");
		System.out.println("Truncate method: ");
		a.truncate();
		System.out.println("Truncated array");
		a.print();
		
		System.out.println("__________________");
		
		// Stack implementation
		
		DQ stack = new DQ();
				
		System.out.println("Stack implementation example. ");
		System.out.println("Pushing values");
		stack.addFirst(1);
		stack.print();
		stack.addFirst(2);
		stack.print();
		stack.addFirst(3);
		stack.print();
		System.out.println("__________________");
		
		
		System.out.println("Popping values");
		stack.removeFirst();
		stack.print();
		stack.removeFirst();
		stack.print();
		stack.removeFirst();
		stack.print();
		stack.removeFirst();
		
		System.out.println("__________________");
				
		// Queue implementation
		
		DQ q = new DQ();
		
		System.out.println("Queue implementation example. ");
		System.out.println("Queue values");
		q.addLast(1);
		q.print();
		q.addLast(2);
		q.print();
		q.addLast(3);
		q.print();
		System.out.println("__________________");
		
		System.out.println("Dequeue values");
		q.removeLast();
		q.print();
		q.removeLast();
		q.print();
		q.removeLast();
		q.print();
		
		
	}
	
	

}
