package mem_mgmt_unit;


import java.util.LinkedList;
import java.util.Queue;
/**
 * Scheduler Class
 * @author Sebastien Luong
 *
 */
public class Scheduler {
	final int QUANTUMCTR=5; 
	Process currentProc; 
	Queue<Process> readyQueue;
	Queue<Process> completeQueue;
	int ctr;
	int processAmt; 
	public Scheduler(){
		currentProc=null;
		readyQueue=new LinkedList<Process>();
		completeQueue=new LinkedList<Process>();
		ctr=QUANTUMCTR;
		processAmt=0;
	}

	public void addProcess(Process proc){
		readyQueue.add(proc);
		++processAmt;
	}
	
	/*
	 * Executes the current process makeFramePageTable()
	 * in a round robin schedule. Until the ready queue is empty
	 */
	public void execute(){
		currentProc=readyQueue.remove();
		while(completeQueue.size() != processAmt){
			// executes function
			if(currentProc.makeFramePageTable()){ 	
			// when process finished iterating through access list
				System.out.printf("Process %d: has terminated and its frames will be re-used. %n", currentProc.getPrName());
				System.out.println();
				currentProc.releaseFrames();
				completeQueue.add(currentProc);
				if (completeQueue.size() == processAmt){
					System.out.println("Last value to add. Round robin finished"); 
					return; 
				}
				currentProc=readyQueue.remove();	
				ctr=QUANTUMCTR;
			}
			// when current process' quantum finishes
			if (ctr==0){	
				readyQueue.add(currentProc);
				currentProc=readyQueue.remove();
				ctr=QUANTUMCTR;
			}
			else{	
				--ctr;
			}
		}
		
	}
}
