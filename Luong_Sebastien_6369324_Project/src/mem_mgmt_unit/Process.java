package mem_mgmt_unit;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import swap_manager.Swapper;
/**
 * Process class
 * @author Sebastien Luong
 *
 */
public class Process {
	final int ROWMAX=4096;	//Represent max number of pages
	final int COLMAX=16;	// max number of values per page
	final int MINVALUE=60000;
	final int MAXVALUE=65535;
	final int MINACCESS=50; 
	final int MAXACCESS=300;
	final int OFFSET=15;
	// variables
	private int amtValues; 
	private int prName;
	private String procFileName;
	private String accessFileName;
	private int numAccess;
	private ArrayList<Integer> logicalMemory;	//stores page num of each address in access file
	private LinkedList<Process> prQueue;
	private int instrNum; 
	// create page frame table
	private int frameTableCtr; 
	private ArrayList<Integer> pageAddressTable;
	public static int mmuActivity;
	private ArrayList<Integer> pageFrameTable; 
	
	
	public Process(){
		prName=0;
		// get a value between 60000 - 65535
		amtValues=0;
		procFileName=null;
		numAccess=0;
		logicalMemory=null;
		frameTableCtr=0;
		mmuActivity=1;
		prQueue=null;
		instrNum=1;
	}
	
	public Process(int prName){
		this.prName=prName;
		// get a value between 60000 - 65535
		amtValues=(int) (MINVALUE+Math.floor(Math.random()*((MAXVALUE-MINVALUE)+1)));
		numAccess=(int) (MINACCESS+Math.floor(Math.random()*((MAXACCESS-MINACCESS)+1))); 
		procFileName="p"+prName+".process.text";
		accessFileName="p"+prName+".access.text"; 
		logicalMemory=new ArrayList<Integer>();
		frameTableCtr=0;
		mmuActivity=1;
		instrNum=1;
	}
	
	/*
	 * Write to process text file and populate pageArray
	 */
	public void writeToText(){
		PrintWriter pw=null;
		int tempValue=amtValues;
		int row=0;
		int col=0;
		try{
			pw = new PrintWriter(new FileOutputStream(procFileName));
			while(tempValue>0){
				if(col==COLMAX){
					row++;
					col=0;
				}
				pw.println((int) (Math.random()*1000));
				col++;
				tempValue--;
			}
		}
		catch (FileNotFoundException e){
			System.out.println("Could not create file to write to"); 
			System.out.println("Program will exit now");
			System.exit(0);
		}
		
		pw.close();
	}
	
	/*
	 * Creates access file
	 */
	public void readFromText(){
		PrintWriter pw=null; 
		
		try{
			pw=new PrintWriter(new FileOutputStream(accessFileName)); 
			// Calculate random memory access in array
			while (numAccess>0){
				int randomAddress=(int) (Math.floor(Math.random()*((MAXVALUE)+1)));
				if (randomAddress>amtValues){
					//System.out.println("Amt values "+amtValues+" random address "+randomAddress);
					//System.out.println("Address outside of array bounds");
				}
				else{
					pw.println(randomAddress);
					// set up logicalMemory array for frame page table 
					logicalMemory.add(randomAddress);
				}
				numAccess--;
			}
			pageAddressTable=new ArrayList<Integer>(logicalMemory.size());
			pageFrameTable=new ArrayList<Integer>(logicalMemory.size());
			pw.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Could not open file for reading. Check if file exists");
		}
	}
	
	
	/*
	 * Create logical memory table, populate physical memory table at the same time
	 */
	public boolean makeFramePageTable(){
		int pagePosition;
		int offsetPosition;
		int address;
		int value;
		if (instrNum>5){
			instrNum=1; 
			System.out.println();
		}
		if (PhysMemory.frameIndex >= PhysMemory.FRAMEAMT){
			if (frameTableCtr==logicalMemory.size())
				return true;
			else{
				pagePosition= returnPageNum(logicalMemory.get(frameTableCtr));
				offsetPosition = returnOffsetNum(logicalMemory.get(frameTableCtr));
				address= logicalMemory.get(frameTableCtr);
				value=Swapper.readSwapFile(prName, pagePosition, offsetPosition);
				
				// Page in memory, will get modified
				if (pageAddressTable.contains(address)){
					System.out.printf("%d] Process %d: address %d [page %d, offset %d], mapped to " +
							"frame %d, value = %d %n",instrNum, prName, address ,pagePosition, offsetPosition, PhysMemory.frameIndex,value);
					Swapper.writeSwapFile(prName, pagePosition, offsetPosition, value);
	
				}
				// Page fault
				else{
					System.out.printf("%d] Process %d: address %d [page %d, offset %d], PAGE FAULT %n",
							instrNum,prName,address ,pagePosition, offsetPosition);
					
					// Get victim process and frame
					int victimFrame=(int) PhysMemory.LRUQueue.remove();
					int victimProcNum=PhysMemory.frameProcessTable[victimFrame];
					// Get victim process' page mapping of frame
					Process victimProc=prQueue.get(victimProcNum);
					ArrayList<Integer> victimPageFrameTable=victimProc.getPageFrameTable();
					int victimPage=victimPageFrameTable.indexOf(victimFrame);

					System.out.printf("%d] Process %d: SWAP. LRU FRAME: %d, original mapping: process %d, page %d %n",
							instrNum,prName,victimFrame ,victimProcNum, victimPage);

					

					for (int i=0; i<COLMAX; i++){
						Swapper.writeSwapFile(prName, victimProcNum, victimPage, i, PhysMemory.frameTable[victimFrame][i]);
						if(i==0){
							System.out.printf("%d] Process %d: BACKING STORE. Process %d, page %d written to slot %d %n",
									instrNum,prName, victimProcNum, victimPage, Swapper.slotPos);
						}
					}
										
					// Populate frame with all values at address
					for (int i=0; i<COLMAX; i++){	
						PhysMemory.frameTable[victimFrame][i]=Swapper.readSwapFile(prName, pagePosition, i);
						if (i==0){
							System.out.printf("%d] Process %d: BACKING STORE. Process %d, page %d retrieved from slot %d %n",
									instrNum,prName, prName, pagePosition, Swapper.slotPos);
						}
					}

					System.out.printf("%d] Process %d: address %d [page %d, offset %d], mapped to " +
							"frame %d, value = %d %n",instrNum,prName, address ,pagePosition, offsetPosition, PhysMemory.frameIndex,value);
					
					PhysMemory.frameProcessTable[victimFrame]=prName;
					pageAddressTable.add(address);
					pageFrameTable.add(victimFrame);
					PhysMemory.LRUQueue.add(victimFrame);
				}
				frameTableCtr++;
				PhysMemory.incFrameIndex();
			}
		}
		// Makes sure not all frames in Physical Memory are used
		else if (PhysMemory.frameIndex < PhysMemory.FRAMEAMT){
			if (frameTableCtr==logicalMemory.size())
				return true;
			else{
				pagePosition= returnPageNum(logicalMemory.get(frameTableCtr));
				offsetPosition = returnOffsetNum(logicalMemory.get(frameTableCtr));
				address= logicalMemory.get(frameTableCtr);
				value=Swapper.readSwapFile(prName, pagePosition, offsetPosition);
				// Page in memory, will get modified
				if (pageAddressTable.contains(address)){
					System.out.printf("%d] Process %d: address %d [page %d, offset %d], mapped to " +
							"frame %d, value = %d %n",instrNum,prName, address ,pagePosition, offsetPosition, PhysMemory.frameIndex,value);
					Swapper.writeSwapFile(prName, pagePosition, offsetPosition, value);
	
				}
				// Page fault
				else{
					System.out.printf("%d] Process %d: address %d [page %d, offset %d], PAGE FAULT %n",
							instrNum,prName,address ,pagePosition, offsetPosition);
					// Populate frame with all values at address
					for (int i=0; i<COLMAX; i++){			
						PhysMemory.frameTable[PhysMemory.frameIndex][i]=Swapper.readSwapFile(prName, pagePosition, i);
						if (i==0){
							System.out.printf("%d] Process %d: BACKING STORE. Process %d, page %d retrieved from slot %d %n",
									instrNum,prName, prName, pagePosition, Swapper.slotPos);
						}
					}
					
					System.out.printf("%d] Process %d: address %d [page %d, offset %d], mapped to " +
							"frame %d, value = %d %n",instrNum,prName, address ,pagePosition, offsetPosition, PhysMemory.frameIndex,value);
					
					PhysMemory.frameProcessTable[PhysMemory.frameIndex]=prName;
					pageAddressTable.add(address);
					pageFrameTable.add(PhysMemory.frameIndex);
					PhysMemory.LRUQueue.add(PhysMemory.frameIndex);
				
				}
				frameTableCtr++;
				PhysMemory.incFrameIndex();
			}
		}
		++instrNum;
		// continue until the counter has traversed the entire access file
		return (frameTableCtr==logicalMemory.size());
	}
	
	public void releaseFrames(){
		// Remove page frames from LRUQueue
		for (int i=0; i<pageFrameTable.size(); i++){
			PhysMemory.LRUQueue.remove(pageFrameTable.get(i));
		}
		// Add them to the beginning
		for (int i=0; i<pageFrameTable.size(); i++){
			PhysMemory.LRUQueue.add(pageFrameTable.get(i));
		}
	}
	
	
	public int returnPageNum(int address){
		return address>>4;
	}

	
	public int returnOffsetNum(int address){
		return address & OFFSET;
	}
	
	
	public int getAmtValues() {
		return amtValues;
	}

	public void setAmtValues(int amtValues) {
		this.amtValues = amtValues;
	}

	public int getPrName() {
		return prName;
	}

	public void setPrName(int prName) {
		this.prName = prName;
	}

	public int getNumAccess() {
		return numAccess;
	}

	public void setNumAccess(int numAccess) {
		this.numAccess = numAccess;
	}

	public ArrayList<Integer> getLogicalMemory() {
		return logicalMemory;
	}

	public void setLogicalMemory(ArrayList<Integer> logicalMemory) {
		this.logicalMemory = logicalMemory;
	}

	public String getProcFileName() {
		return procFileName;
	}

	public void setProcFileName(String procFileName) {
		this.procFileName = procFileName;
	}

	public LinkedList<Process> getPrQueue() {
		return prQueue;
	}

	public void setPrQueue(LinkedList<Process> prQueue) {
		this.prQueue = prQueue;
	}

	public ArrayList<Integer> getPageFrameTable() {
		return pageFrameTable;
	}

	public void setPageFrameTable(ArrayList<Integer> pageFrameTable) {
		this.pageFrameTable = pageFrameTable;
	}
	
	
}
