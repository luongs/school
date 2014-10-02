import java.io.IOException;


public class binaryTree2 {

	private arrayList a;
	private int cursor;
	private boolean heapChoice; 		// default is minHeap; true is maxHeap
		
	public binaryTree2()
	{
		a = new arrayList(10);
		cursor = 1;
	}
	
	public binaryTree2(int size)
	{
		a = new arrayList(size);
		cursor = 1;
	
	}
	
	public void addFirstNode(Character x)
	{
		a.add(1, x);
	}
	
	public void addLeft(Character x)
	{
		if(a.get(cursor*2)==null){
			a.add(cursor*2, x);
			
		}
		else {
			cursor*=2;
			a.add(cursor*2, x);
		
			System.out.println("Improper tree!");
		}
	}
	
	public void addRight(Character x)
	{
		if(a.get(cursor*2+1)==null){
			a.add((cursor*2) + 1, x);
			cursor+=1;
		}
		else{
			cursor*=2+1;
			a.add((cursor*2) + 1, x);
			//last=cursor*4+1;
			System.out.println("Improper tree!");			
		}
	}
	
	public Character goToParent()		// Similar to parent() 
	{
		cursor = cursor/2;
		return a.get(cursor);
	}
	
	public Character goToLeft()			// must implement left() when access to random node required
	{
		cursor *= 2;
		return a.get(cursor);
	}
	
	public Character goToRight()		
	{
		cursor = (cursor * 2) + 1;
		return a.get(cursor);
	}
	
	/*
	public void remove()
	{
		a.set(cursor, null);
	}
	*/
	// Added methods
	
	public int size(){
		return a.getIndex();
	}
	
	public boolean isEmpty(){
		return a.getIndex()== 0;
	}
	
	public Character root(){
		Character temp=null;
		try{
			if(a.getIndex() == 0){
				throw new IOException();
			}
			else 
				temp=a.get(1);
			}
				catch(Exception e){
				System.out.println("Empty array error");
			}
		return temp;		
	}
	
	public Character parent(int index){
		Character temp=null;
		try{
			if(a.getIndex() <= 1){
				throw new IOException();
			}
			else 
				temp=a.get(index/2);
			}
				catch(Exception e){
				System.out.println("Empty array error");
			}
		return temp;
	}
	
	public void insertLeft(int index, Character val){
		try{
			if(index*2 >= a.getSize())
				a.expand();
			if(hasLeft(index)==false)
				a.add(index*2, val);
		else 
			throw new IOException();
		}
		catch(Exception e){
			System.out.println("Left child already present. Cannot add new value");
		}
	}
	
	public void insertRight(int index, Character val){
		try{
			if(index*2+1 >= a.getSize())
				a.expand();
			if(hasRight(index)==false)
				a.add(index*2+1, val);
		else 
			throw new IOException();
		}
		catch(Exception e){
			System.out.println("Right child already present. Cannot add new value");
		}
	}
	
	public boolean isInternal(int index){
		return a.get(index*2) != null;
	}
	
	public boolean isExternal(int index){
		return a.get(index*2)== null;		
	}
	
	public boolean isRoot(int index){
		return(index==1);		
	}
	
	public void replace(int index, Character val){
		a.set(index, val);		
	}
	
	public boolean hasLeft(int index){
		if(a.get(index*2)!= null)
			return true;
		else return false;
	}
	
	public boolean hasRight(int index){
		if(a.get(index*2+1)!= null)
			return true;
		else return false;
	}
	
	public Character left(int index){
		Character temp=null;
		try{
			if(hasLeft(index)==true)
				temp=a.get(index*2);
		else 
			throw new IOException();
		}
		catch(Exception e){
			System.out.println("No left child present.");
		}
		return temp;
	}
	
	public Character right(int index){
		Character temp=null;
		try{
			if(hasRight(index)==true)
				temp=a.get(index*2+1);
		else 
			throw new IOException();
		}
		catch(Exception e){
			System.out.println("No right child present.");
		}
		return temp;
	}
	
	public void print() {
		System.out.print("(");
		for(int i=0; i<a.getSize(); i++)
			System.out.print(a.get(i)+", ");
		System.out.print(") \n");
	
	}
	
	// New heap methods
	
	public void toggleHeap(){
		heapChoice = !heapChoice;
	}
	
	public void switchMinHeap(){
		heapChoice = false;
	}
	
	public void switchMaxHeap(){
		heapChoice = true; 		
	}
	
	public Character remove(){
		Character temp = null;
		if(heapChoice == false){
			temp=root();
			a.set(1,a.get(getIndex()));	//Replace root with last node 
			
			downHeap(getIndex(), 1);
			
			a.setIndex(getIndex()-1);			
		}
		if(heapChoice == true){
			temp=a.get(getIndex());
			a.set(getIndex(), null);		//Set max value to null 
			a.setIndex(getIndex()-1);
		}
		return temp;
	}
	
	public void downHeap(int heapIndex, int nextIndex){
		
		while(nextIndex<=heapIndex){
		if(hasLeft(nextIndex)){
				replace(nextIndex,left(nextIndex));
				nextIndex=nextIndex*2;
				
		}
		else if(hasRight(nextIndex)){
			replace(nextIndex, right(nextIndex));
			nextIndex = nextIndex*2+1;
		}
		else {
			a.set(nextIndex, a.get(getIndex()));
			a.set(getIndex(), null);	
			return;
		}
	}
		
	}
	
	
	// Getters and setter methods
	
	public int getCursor()
	{
		return cursor;
	}
	
	public arrayList getArrayList()
	{
		return a;
	}
	
	public int getIndex()
	{
		return a.getIndex();
	}
	
	public int getSize()
	{
		return a.getSize();
	}

	public boolean isHeapChoice() {
		return heapChoice;
	}

	public void setHeapChoice(boolean heapChoice) {
		this.heapChoice = heapChoice;
	}
	
}
