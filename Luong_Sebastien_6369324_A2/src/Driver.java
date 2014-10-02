/**
 * Author Sebastien Luong
 * Comp 346 A2
 */


import java.util.ArrayList;
import java.util.Iterator;


public class Driver {

	public static void main(String[] args) {
		int p1Value=0;
		int p1ArrSize=0;
		int p2Value=0;
		int p3Value=0;
		int quantCounter=0;
		
		if (args.length == 5) { // Make sure 4 are passed
			
				// Followed by 3 Integers
				p1Value=Integer.parseInt(args[0]);
				p1ArrSize=Integer.parseInt(args[1]);
				p2Value=Integer.parseInt(args[2]);
				p3Value=Integer.parseInt(args[3]);
				quantCounter=Integer.parseInt(args[4]);
			
		}
		
		
		int p1ValArrPos=0;
		int p2ValArrPos=0;
		int p3ValArrPos=0;
		Process p1;
		Process p2;
		Process p3;
		Scheduler sched;
		
		p1 = new Process(1, p1Value, p1ValArrPos);
		p2 = new Process(2, p2Value, p2ValArrPos);
		p3 = new Process(3, p3Value, p3ValArrPos); 
		CPU test = new CPU(quantCounter);
		sched = new Scheduler(p1,p2,p3,quantCounter,test);
		/*
		 * Setup local memory
		 */
		final int ARRSIZE=p1ArrSize;
		final int ARRSTARTPOSITION=3;
		int[] localMem=test.getLocalMem();
		// keeps track of position of values added in local memory
		int arrCounter=ARRSIZE;
		localMem[0]=p1Value;
		localMem[1]=ARRSIZE;
		localMem[2]=ARRSTARTPOSITION;
		
		// Populate array
		for(int i=ARRSTARTPOSITION; i<ARRSIZE; i++){
			localMem[i]=(int) Math.floor((Math.random()*10));
		}
		
		System.out.println("Parameters passed");
		System.out.print("___________________________________________________");
		System.out.println("Number of quantum instructions: "+quantCounter);
		System.out.println("p1 value to search: "+p1Value);
		System.out.println("p1 Size of the array to search: "+p1ArrSize);
		System.out.println("p2 value to be squared: "+p2Value);
		System.out.println("p3 value to be summed: "+p3Value);
		System.out.println("___________________________________________________");

		System.out.println("Array to be searched will be: ");
		for (int i=ARRSTARTPOSITION; i<=ARRSIZE; i++){
			System.out.println(test.getLocalMem()[i]);
		}
		System.out.println("___________________________________________________");

		p2ValArrPos=arrCounter;
		localMem[p2ValArrPos]=p2Value;
		arrCounter++;
		p3ValArrPos=arrCounter;
		localMem[p3ValArrPos]=p3Value;
		// set CPU local memory to the one created here
		p2.setParamArrPos(p2ValArrPos);
		p3.setParamArrPos(p3ValArrPos);
		test.setLocalMem(localMem);

		
		sched.schedExec();
		
		/*
		while(test.getPgmCounter()>=0){
			test.execModule(test.getArrInstruction().get(test.getPgmCounter()));
		}
		*/
		
	}

}
