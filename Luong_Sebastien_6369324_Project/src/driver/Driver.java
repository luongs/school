package driver;

import java.util.LinkedList;

import mem_mgmt_unit.PhysMemory;
import mem_mgmt_unit.Process;
import mem_mgmt_unit.Scheduler;
import swap_manager.Swapper;
/**
 * Driver class
 * @author bao
 *
 */
public class Driver {
	@SuppressWarnings("static-access")
	public static void main(String[] args){	
	int processAmt=0;
	if (args.length == 1) { // Make sure 4 are passed
			
			processAmt=Integer.parseInt(args[0]);
			
		
	}
	else{
		System.out.println("Invalid number of arguments input. ");
		System.out.println("Exiting program");
		System.exit(1);
	}
		
		
		LinkedList<Process> prQueue=new LinkedList<Process>();
		PhysMemory globalPhysMemory=new PhysMemory();
		Scheduler roundRobin=new Scheduler();
		Swapper sw;
		// create process and access files
		for (int i=0; i<processAmt; i++){
			prQueue.add(new Process(i));
			prQueue.get(i).writeToText();
			prQueue.get(i).readFromText();
		}
		// set correct prQueue for all process
		for (int i=0; i<processAmt; i++){
			prQueue.get(i).setPrQueue(prQueue);
		}
		
		// create swap file 
		sw=new Swapper(prQueue);
		sw.createSwapFile();
		
		// add process to scheduler
		for (int i=0; i<processAmt; i++){
			roundRobin.addProcess(prQueue.get(i));
		}
		roundRobin.execute();

	}
}
