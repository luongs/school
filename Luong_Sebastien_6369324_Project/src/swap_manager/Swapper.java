package swap_manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.Scanner;

import mem_mgmt_unit.Process;
/**
 * Swap manager reads all process files into one binary file 'swap.os'
 * Methods store swapped frames when a victim frame is identified
 * @author Sebastien Luong
 *
 */
public class Swapper {

	static final String FILENAME="swap.os";
	static final String PERMISSION="rw";
	static final int NXTBYTEPOS=4;
	static final int NXTPAGEPOS=16;
	static final int COLMAX=16;
	public static long slotPos;
	static LinkedList<Process> prQueue;
	static RandomAccessFile raf;
	static long[] procStart; 		// keeps track of position of last line in process
	
	public Swapper(){
		prQueue=null;
		raf=null;
		procStart=null;
	}
	
	public Swapper(LinkedList<Process> prQueue){
		Swapper.prQueue=prQueue;
		procStart=new long[prQueue.size()]; 
	}
	

	public static void createSwapFile(){
		try {
			raf=new RandomAccessFile(FILENAME, PERMISSION);
			Scanner sc=new Scanner(System.in);
			int rdInt;
			long procStartIndex=0; 
			String s;
			
			for(int i=0; i<prQueue.size(); i++){
				String procFileName=prQueue.get(i).getProcFileName();
				procStart[i]=procStartIndex;
				procStartIndex+=prQueue.get(i).getAmtValues();
				sc=new Scanner(new FileInputStream(procFileName));
				// Fill raf file with data from process
				while (sc.hasNextLine()){
					rdInt=sc.nextInt();
					s=sc.nextLine();
					raf.writeInt(rdInt);
				}
				
			}
			

		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	/*
	 *  Returns value defined at process position w/ no victim frame
	 */
	public static int readSwapFile(int newProc, int pageNum, int offset){
		int value=-1;
		try{
	
			raf.seek((pageNum*NXTPAGEPOS+offset+procStart[newProc])*NXTBYTEPOS);			
			slotPos=raf.getFilePointer();
			//System.out.printf("Process %d: BACKING STORE. Process %d, page %d retrieved from slot %d %n",
			//newProc, newProc, pageNum, slotPos);
					value = raf.readInt();

			
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return value; 
	}
	
	
	/*
	 *  Returns value defined at process position if victim frame found
	 */
	public static int readSwapFile(int newProc, int victimProc, int pageNum, int offset){
		int value=-1;
		try{
			raf.seek((pageNum*NXTPAGEPOS+offset+procStart[victimProc])*NXTBYTEPOS);
			 slotPos=raf.getFilePointer();
			 //System.out.printf("Process %d: BACKING STORE. Process %d, page %d retrieved from slot %d %n",
			//					newProc, victimProc, pageNum, slotPos);
			value = raf.readInt();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return value; 
	}
	
	/*
	 * Writes modified value to swap file if no victim frame present 
	 */
	public static void writeSwapFile(int newProc, int pageNum, int offset, int value){
		
		try {
			raf.seek((pageNum*NXTPAGEPOS+offset+procStart[newProc])*NXTBYTEPOS);
			slotPos=raf.getFilePointer();
			//System.out.printf("Process %d: BACKING STORE. Process %d, page %d written to slot %d %n",
			//					newProc,newProc, pageNum, slotPos);
			raf.writeInt(value);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Writes modified value to swap file if victim frame present 
	 */
	public static void writeSwapFile(int newProc, int victimProc, int pageNum, int offset, int value){
		
		try {
			raf.seek((pageNum*NXTPAGEPOS+offset+procStart[victimProc])*NXTBYTEPOS);
			slotPos=raf.getFilePointer();
			//System.out.printf("Process %d: BACKING STORE. Process %d, page %d written to slot %d %n",
			//					newProc, victimProc, pageNum, slotPos);
			raf.writeInt(value);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
