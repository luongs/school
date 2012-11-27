import java.io.IOException;


public class binaryTree2 {

	private arrayList a;
	private int cursor;
	
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
		a.set(1, x);
	}
	
	public void addLeft(Character x)
	{
		a.set(cursor*2, x);
	}
	
	public void addRight(Character x)
	{
		a.set((cursor*2) + 1, x);
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
	
	public void remove()
	{
		a.set(cursor, null);
	}
	
	// Added methods
	
	public int size(){
		return a.getSize();
	}
	
	public boolean isEmpty(){
		return a.getSize()== 0;
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
				a.set(index*2, val);
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
				a.set(index*2+1, val);
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
	
}
