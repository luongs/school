//-----------------------------------------------------------//
// Assignment 1												 //
// Part I													 //
// Written by: Sebastien Luong, 6369324						 //
// The class provides instance variables and methods to		 // 
// the main driver class of the driver.  					 //
//-----------------------------------------------------------//
import java.util.Scanner;
public class Book 
{

	private String title;
	private String author;
	private long ISBN;
	private double price; 
	
	
	public Book ()
	{
		title=null;
		author = null;
		ISBN = 0;
		price = 0;
	}
	public Book (String title, String author, long ISBN, double price)
	{
		title=this.title;
		author=this.author;
		ISBN = this.ISBN;
		price = this.price;
	}
	public String getTitle(){
		return title; }
	public String getAuthor()
	{
		return author;
	}
	public long getISBN()
	{
		return ISBN;
	}
	public double getPrice()
	{
		return price;
	}
	public void setTitle(String title)
	{
		this.title=title;
	}
	public void setAuthor(String author)
	{
		this.author=author;
	}
	public void setISBN(long ISBN)
	{
		this.ISBN=ISBN;
	}
	public void setPrice(double price)
	{
		this.price=price;
	}
	public String toString()
	{
		return ("Title is "+title+". Author is "+author+". ISBN is "+ISBN+". Price is "+price);
	}
	public static int findNumberOfCreatedBooks(Book[] inventory)
	{
		int countBook=0;
		for (int i=0; i<inventory.length;i++)
		{
			countBook++;
		}
		return countBook;
	}
	
	public boolean equals(Book anotherBook)
	{
		if (anotherBook.ISBN==this.ISBN&& anotherBook.price==this.price)
			{
			return true;
			}
		else
		return false; 
	}
		
}

