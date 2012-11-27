
public class arrayList {

	private Character[] a;
	private int index;
	private int size;
	private Character[] temp;
	
	public arrayList()
	{
		a=null;
		index=0;
		size=0;
	}
	
	public arrayList(int Size)
	{
		a = new Character[Size];
		index = 0;
		size = Size;
	}
	
	public void add(int key, Character value)
	{
		/*
		checkFull();
		a[index] = value;
		setIndex(index++);
		*/
		

		a[key] = value;
		index++;
		checkFull();
	}
	
	public void set(int position, Character value)
	{

		a[position] = value;
		checkFull();
	}
	
	public Character get(int position)
	{
		return a[position];
	}
	
	public void remove()
	{
		a[--index] = 0;
		setIndex(index--);
	}
	
	public void checkFull()
	{
		if(a[size-1] != null)
			expand();	
	}
	
	public void expand()
	{
		size *= 2;
		temp = new Character[size];
		
		for(int j = 0; j < a.length; j++)
			temp[j] = a[j];
		
		a = new Character[size];
		
		for(int j = 0; j < temp.length; j++)
			a[j] = temp[j];
	}
	
	public int getSize()
	{
		return size;
	}
	
	public void setSize(int size){
		this.size = size;
		
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setIndex(int i){
		index=i;		
	}
	
	public Character[] getArray()
	{
		return a;
	}
	
	public void print(){
		System.out.print("(");
		for(int i=0; i<a.length; i++)
			System.out.print(a[i]+", ");
		System.out.print(") \n");
	
	}
}
