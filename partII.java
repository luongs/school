//-----------------------------------------------------------//
// Assignment 1												 //
// Part II													 //
// Written by: Sebastien Luong, 6369324						 //
// The driver contains methods used for displaying the 		 //
// Bookstore's main menu and contains the methods required 	 //
// to run the main menu system								 //
//-----------------------------------------------------------//

import java.util.Scanner;
public class partII {
	
	static Scanner myK= new Scanner(System.in);
	private static int option=0;
	private static int maxBooks=0;
	private static Book [] inventory;
	private static int addBooks=0;
	private static int freeSpace=0;				// Counts number of available space in inventory array
    private static int index=0;
    private static int minIndex=0;
	private final static String password = "password";
	private static  String passInput; 
	private static int option2Book=0; 
	private static int option2Choice=0;
	private static String option2NoBook="";
	private static String newTitle="";		
	private static String newAuthor="";
	private static long newISBN=0;
	private static double newPrice=0;
	private static String option3Author="";
	private static double option4Price=0;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
				
		System.out.println("Welcome to the book sorting program");
		partII.maxBook();							// Prompt user for max number of books
		partII.inventoryArray(partII.getMaxBook());		//Create object array of Books
		partII.mainMenu();
		
	}

	
	public static int maxBook()
	{
		System.out.println("Please input the maximum number of books present" +
				" in your bookstore: ");
		do{
			maxBooks=myK.nextInt();
			if (maxBooks <=0)
			{
				System.out.println("Please input at least one book, negative numbers"+
				" are not accepted.");
			}
		} while (maxBooks<=0);
		return maxBooks;
	}
	
	public static int getMaxBook()
	{
		return maxBooks;				//use maxBooks value to create space for Book objects
	}
	
	public static void inventoryArray(int userMaxBooks)		//Create inventory array and initialize book objects
	{
		
		inventory= new Book [userMaxBooks];
		for (int i=0; i<inventory.length;i++)
		{
			inventory[i]= new Book();
		}
	}
	// Display main menu and provide user options
	 public static void mainMenu()
	{
		do{
			System.out.println("What do you want to do?");
			System.out.println("1. Enter new books (password required)");
			System.out.println("2. Change information of a book (password required)");
			System.out.println("3. Display all books by a specific author.");
			System.out.println("4. Display all books under a certain price. ");
			System.out.println("5. Quit ");
			System.out.print("Please enter your choice > ");
			option = myK.nextInt();
			System.out.println("");
			if (option<1||option>5)			
			{
				System.out.println("Invalid choice. Please enter an option between 1 and 5");
			}
		//Option 1 
			if (option==1)	
			{
				partII.passPrompt();
				partII.option1();
			}
			//Option 2
			if (option == 2)
			{
				partII.passPrompt();
				partII.option2();
			}
			//Option 3
			if (option == 3)
			{
				partII.option3();
			}
			if (option == 4)
			{
				partII.option4();
			}
			if (option ==5)
			{
				partII.option5();
			}
		  } while (option<1||option>5);		// Loop to prevent invalid entries
		
	 }
		 
	 // Option 1
	 public static void option1()
	 {
		 System.out.println("How many books would you like to add?");
			addBooks = myK.nextInt();
			
			for (int i=0; i<inventory.length;i++)	// Check for empty space
				{
				if (inventory[i].getISBN()==0 && inventory[i].getPrice()==0)		// If object's ISBN is 0 then space is free
				{
					freeSpace++;
					if (i<minIndex)
					minIndex= i;
				}
				else 
				{
					freeSpace=0;
				}
				}
			
			if (addBooks<=freeSpace)
			{
				for (int x=index; x<index+addBooks;x++)
				{
					System.out.println("Please input title >");
					newTitle=myK.next();
					inventory[x].setTitle(newTitle);
					System.out.println("Please input author >");
					newAuthor=myK.next();
					inventory[x].setAuthor(newAuthor);
					System.out.println("Please input ISBN >");
					newISBN=myK.nextLong();
					inventory[x].setISBN(newISBN);
					System.out.println("Please input price >");
					newPrice=myK.nextDouble();
					inventory[x].setPrice(newPrice);
				}
				partII.mainMenu();
				
			}
			else 
			{
				System.out.println("Too many books specified. There is only enough space to add "
						+freeSpace+" books.");
				partII.mainMenu();
				
			}
		}
	 
	
	 // Option 2
	 public static void option2()
	 {
		System.out.println("Which book number would you like to update? Starting with 0");
		option2Book=myK.nextInt();
		//No book at the specified call number
		if (inventory[option2Book].getISBN()==0 && inventory[option2Book].getPrice()==0)		// If object's ISBN and price is 0 then there's no book
		{
			System.out.println("No book is present at book number "+option2Book+". Type 'enter' to try again. Type 'quit' to return to main menu");
			option2NoBook=myK.nextLine();
			option2NoBook=myK.nextLine();
			if(option2NoBook.equalsIgnoreCase("enter"))
				partII.option2();
			if(option2NoBook.equalsIgnoreCase("quit"))
				partII.mainMenu();
		}
		else{
			partII.displayInfo(option2Book);
			partII.option2Menu();
			}
				
	 }
	 
	 // Display Book information
	 public static void displayInfo(int option2Book)
	 {
		 System.out.println("Book # "+ option2Book+"\n"
					+"Author "+inventory[option2Book].getAuthor()+"\n"
					+"Title "+ inventory[option2Book].getTitle()+"\n"+
					"ISBN "+ inventory[option2Book].getISBN()+"\n"+
					"Price: "+inventory[option2Book].getPrice());
	 }
	 
	 // Display option 2 changes and info
	public static void option2Menu()
	 {
		 	do{
		 		System.out.println("What information would you like to change?"+"\n"+
						"\t 1. \t author"+
						"\n \t 3. \t ISBN"+
						"\n \t 4. \t price"+
						"\n \t 5. \t quit"+
						"\n Enter your choice >");
			 		option2Choice=myK.nextInt();
			 		
			 		if (option2Choice==1)
			 		{
			 			System.out.println("Please input author >");
						newAuthor=myK.next();
			 			inventory[option2Book].setAuthor(newAuthor);
			 			partII.displayInfo(option2Book);
			 			partII.option2Menu();
			 		}
			 		if (option2Choice==2)
			 		{
			 			System.out.println("Please input title >");
						newTitle=myK.next();
						inventory[option2Book].setTitle(newTitle);
						partII.displayInfo(option2Book);
						partII.option2Menu();
			 		}
			 		if (option2Choice==3)
			 		{
			 			System.out.println("Please input ISBN >");
						newISBN=myK.nextLong();
						inventory[option2Book].setISBN(newISBN);
						partII.displayInfo(option2Book);
						partII.option2Menu();
			 		}
			 		if (option2Choice==4)
			 		{
			 			System.out.println("Please input price >");
						newPrice=myK.nextDouble();
						inventory[option2Book].setPrice(newPrice);
						partII.displayInfo(option2Book);
						partII.option2Menu();
			 		}
					if (option2Choice==5)
					{
						partII.mainMenu();
					}
					if (option2Choice<1||option2Choice>5)
					{
						System.out.println("Please enter a number between 1 and 5");
					}
		 	} while (option2Choice<1||option2Choice>5);
	 }
	 
	 //Option 3 static void method
	 public static void option3()
	 {
		 System.out.println("Please enter the author's name >");
		 option3Author=myK.next();
		 partII.findBooksBy(option3Author);
		 partII.mainMenu();
	 }
	
	 // Find books by author
	 public static void findBooksBy(String authorInput)
	 { 
		  for (int i=0; i<inventory.length; i++ )
		  {
			  String option3Author=inventory[i].getAuthor();
			  if (option3Author.equalsIgnoreCase(authorInput))
			  	  { partII.displayInfo(i);
			  	  System.out.println("");
			  	  break;}
			  else if (option3Author.equalsIgnoreCase("null"))
				  {System.out.println("No author present");
				  partII.mainMenu();
				  }
			 /* else
				  {System.out.println("Invalid author input");
				  Book.mainMenu();
				  } */
		  }
		  
	 }
	 
	 //Option 4 Static void method
	 
	 public static void option4()
	 {
		 System.out.println("Please input price >");
		 option4Price=myK.nextDouble();
		 partII.findCheaperThan(option4Price);
	 }
	 
	 //Find cheaper price method
	 
	 public static void findCheaperThan(double priceInput)
	 {
		 for (int i=0; i<inventory.length; i++)
		 {
			 if(inventory[i].getPrice()< priceInput)
			 {	 partII.displayInfo(i);
			  	 System.out.println("");}
			 else 
			 { System.out.println("No cheaper books were found");
			 partII.mainMenu();
			 }
		 }
	 }
	 
	 // Option 5 
	 
	 public static void option5()
	 {
		 System.out.println("Thank you for using the library system.");
		 System.exit(0);
	 }
	 
	 // Password prompting method
	 public static void passPrompt()
	 {
		 for (int i=0; i<=3;i++)
		 {
			 System.out.println("Please enter password > ");
			 passInput = myK.next();
			 if (i==3)
			 {
				 partII.mainMenu();
			 }
			 if (passInput.equals(password))
			 {
				break; 
			 }
			 else 
			 {
				 continue; 
			 }
		 }
	 }
	}


