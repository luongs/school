import java.io.IOException;
import java.lang.reflect.Array;


public class DQ<T> {

	private T[] element;		//array to to store all values
	private int front, back;	//pointers to keep track of the last added value either to the front or back where front starts at 0 of array and back starts at the last last point
	private int numOfElements;			//counter to keep track of the number of elements in the array
	private int size;
	private int mid; 
	private boolean doubling = true;
	private boolean increasing = false;
	
	
	@SuppressWarnings("unchecked")
	public  DQ()
	{
		 size= 10; 
		 mid = size/2;
		 element =  (T[]) new Object[(size)];		//initialize size of array to arbitrary value
		 front = mid; 
		 back = mid;		
		 numOfElements = 0;
	}

	
	public void addFirst(T firstValue)
	{
		if(front!=back +1){				// avoids overwrite of value in tail slots
			
			element[front%element.length] = firstValue; 		// allows circular loop
			
			if(front == 0 )				// Allows for looping to the other end of array when array index = 0
				front = element.length;
			front --; 
			numOfElements++;
		}
		else {
			System.out.println("Array full");
			// ADD: Expansion
		}
		
		/*element[++front] = firstValue;
		numOfElements++;
		
		/*Algorithm to increase the size of the array when the size of the array is >= 90% of its capacity
		 * The idea of this is let's say you addFirst the values 1, 2 and addLast the values 4, 5. So then the
		 * array will look like [1, 2, 0, 5, 4]. However addFirst the value 3 again will put the array >= 90% of 
		 * capacity. So regularly it would look like this if the capacity is doubled and you put the values in normally: 
		 * [1, 2, 3, 5, 4, 0, 0, 0, 0, 0]. What my algorithm is trying to do is to get it to appear like this 
		 * [1, 2, 3, 0, 0, 0, 0, 0, 5, 4]. I think this is what we're supposed to do. The algorithm doens't fully work yet.
		 * But read the question and stuff and let me know what you think if this is what we're supposed to do. 
		 *
		 
		if(numOfElements/size*100 >= 90)
		{
			int[] asdf;		//creates a temporary array
			int temp = element.length;
			
			if(doubling)
				asdf = new int[size *= 2];
			else
				asdf = new int[size += 10];
			
			//passes all the values of element into the temporary array
			for(int i=0; i < element.length; i++)	
				asdf[i] = element[i];
			
			//re-creates the element array with the new size
			element = new int[asdf.length];		
			
			for(int i=0; i < size; i++)		//passes the values of the temporary array back to the element array
				element[i] = asdf[i];
			
			for(int i=element.length-1; i >= front; i--)	//attempts to put the addLast values in the last index, but doesn't work
				element[i] = asdf[i];
			
			back = element.length - temp;	//re-assigns the back value so it points to the appropriate index with the newly positioned addLast values
		} */
	}
	
	public void addLast(T lastValue)
	{
		
		if(back!=front-1){
			element[back%element.length] = lastValue; 
			back ++;
			numOfElements++;
		}
		else {
			System.out.println("Array full");
			// ADD: Expansion
		}
		/*element[--back] = lastValue;
		numOfElements++;
		
		if(numOfElements/element.length*100 >= 90)
		{
			int[] asdf;
			int temp = element.length;
			
			if(doubling)
				asdf = new int[size *= 2];
			else
				asdf = new int[size += 10];
			
			for(int i=0; i < element.length; i++)
				asdf[i+temp] = element[i];
			
			element = new int[asdf.length];
		
			for(int i=0; i < element.length; i++)
				element[i] = asdf[i];
			
			for(int i=element.length-1; i > front; i--)
				element[i] = asdf[i];
			
			back = element.length - temp;
		}*/
	}
	
	public T removeFirst() throws IOException
	{
		T temp=null;	
		
		try{
			if(element[front+1]==null)			// + 1 since head has moved to an unused space after addFirst() call
				throw new IOException();	// Implement specific error and message
			else{
				temp=element[front+1];
				element[front+1]=null;
				front++;
				numOfElements--;
			}
		}
		catch(Exception e){
			System.out.println("Cannot remove. Array is empty");
		}
		return temp;
	}
	
	
	public T removeLast()
	{
		T temp =null;	
		
		try{
			if(element[back-1]==null)						
				throw new IOException();	
			else{
				temp=element[back-1];
				element[back-1]=null;
				back--;
				numOfElements--;
			}
		}
		catch(Exception e){
			System.out.println("Cannot remove. Array is empty");
			
		}
		return temp;
		
		/*
		int temp = element[back++];		//moves the pointer to the next value
		numOfElements--;
		return temp;*/
		
	}
	
	public boolean isEmpty()
	{
		if(front==back)
			return true;
		else 
			return false;
		//return numOfElements == 0;
	}
	
	public T peekFirst()
	{
		T temp;
		return temp=element[front+1];
	}
	
	public T peekLast()
	{
		T temp;
		return temp=element[back-1];
	}
	
	@SuppressWarnings("unchecked")
	public T[] truncate()		
	{
		int ctrA=0;
		int ctrB=0;
		T[] temp;
		
		// Determine number of used slots in array 
		for(int i=0; i<element.length;i++){			
			if(element[i]!=null)
				ctrA++;	}
		
		temp = (T[])new Object[(size)];
		
		// Add values in truncated array 'temp'
		for (int i=0; i<element.length; i++)
				if(element[i]!=null){
					temp[ctrB]=element[i];
					ctrB++;}
		
		element = temp;
		size = ctrA;
		return element;
					
		
		/*int[] temp = new int[numOfElements];
		while(i <= numOfElements)
		{
			temp[i] = element[i];
			i++;
		}*/
	}
	
	public void setExpansionRule(char value)
	{
		if(value == 'd')
		{
			doubling = true;
			increasing = false;
		}
			
		else
		{
			doubling = false;
			increasing = true;
		}
	}
	
	public void print(){
		System.out.print("(");
		for(int i=0; i<element.length; i++)
			System.out.print(element[i]+", ");
		System.out.print(") \n");
	
	}
	
	public String toString(int i)		//method to test the outputs
	{
		return "" + element[i];
	}

	public T[] getElement() {
		return element;
	}

	public void setElement(T[] element) {
		this.element = element;
	}

	public int getFront() {
		return front;
	}

	public void setFront(int front) {
		this.front = front;
	}

	public int getBack() {
		return back;
	}

	public void setBack(int back) {
		this.back = back;
	}

	public int getNumOfElements() {
		return numOfElements;
	}

	public void setNumOfElements(int numOfElements) {
		this.numOfElements = numOfElements;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isDoubling() {
		return doubling;
	}

	public void setDoubling(boolean doubling) {
		this.doubling = doubling;
	}

	public boolean isIncreasing() {
		return increasing;
	}

	public void setIncreasing(boolean increasing) {
		this.increasing = increasing;
	}
	
	
}
