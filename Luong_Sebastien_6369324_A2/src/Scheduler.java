/**
 * Author Sebastien Luong
 * Comp 346 A2
 */

import java.util.LinkedList;
import java.util.Queue;


public class Scheduler {
	
	private Process p1;
	private Process p2;
	private Process p3;
	private int quantValue;
	private CPU cpu;
	private int[] p1Registers;
	private int[] p2Registers;
	private int[] p3Registers;
	private int p1PgmCounter;
	private int p2PgmCounter;
	private int p3PgmCounter;
	final int REGISTER_AMT=8;
	private int prevProcess;
	private Queue<Process> readyQueue;
	private Queue<Process> ioQueue;
	private int ioWaitTime; 
	final int UNSET_WAIT_TIME=-999;
	
	public Scheduler(){
		p1=null;
		p2=null;
		p3=null;
		quantValue=0;
		cpu=null;
		p1Registers=null;
		p2Registers=null;
		p3Registers=null;
		p1PgmCounter=0;
		p2PgmCounter=0;
		p3PgmCounter=0;
		prevProcess=0;
		readyQueue=null;
		ioQueue=null;
		ioWaitTime=UNSET_WAIT_TIME;
	}

	public Scheduler(Process p1, Process p2, Process p3, int quantValue, CPU cpu){
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
		this.quantValue=quantValue;
		this.cpu=cpu;
		p1Registers=new int[REGISTER_AMT];
		p2Registers=new int[REGISTER_AMT];
		p3Registers=new int[REGISTER_AMT];
		p1PgmCounter=cpu.getPgmCounter();
		p2PgmCounter=cpu.getPgmCounter();
		p3PgmCounter=cpu.getPgmCounter();
		prevProcess=0;
		readyQueue=new LinkedList<Process>();
		readyQueue.add(p1);
		readyQueue.add(p2);
		readyQueue.add(p3);
		ioQueue=new LinkedList<Process>();
		ioWaitTime=UNSET_WAIT_TIME;
	}
	
	public void schedExec(){
		while (p1PgmCounter>=0 || p2PgmCounter>=0 || p3PgmCounter>=0){
			
			if(ioQueue.contains(p1) && ioQueue.contains(p2) && ioQueue.contains(p3)){
				readyQueue.add(ioQueue.remove());
				System.out.println("%% Process returned to ready queue");
			}
			
			
			//PROCESS 1
			if(((prevProcess==0 || prevProcess==3) || (prevProcess==2 && p3PgmCounter<0) || ((prevProcess==1 && p2PgmCounter<0) && (prevProcess==1 && p3PgmCounter<0)) || ((ioQueue.contains(p2) && ioQueue.contains(p3)) || (ioQueue.contains(p2) && p3PgmCounter<0) || (ioQueue.contains(p3) && p2PgmCounter<0)) )&& (p1PgmCounter>=0)) {
				if (readyQueue.contains(p1)){
				if(prevProcess==2)
					System.out.println("**Context switch, process 2 replaced by process 1");
				else if (prevProcess==3)
					System.out.println("**Context switch, process 3 replaced by process 1");

				cpu.setProc(p1);
				cpu.setRegisters(p1Registers);
				cpu.setPgmCounter(p1PgmCounter);
				cpu.setArrInstruction(p1.getInstrArr());
				
				// Give control to CPU
				while (cpu.getQuantCounter()>0 && cpu.getPgmCounter()>=0){
					cpu.execModule(cpu.getArrInstruction().get(cpu.getPgmCounter()));
					if(ioWaitTime!=UNSET_WAIT_TIME && ioWaitTime>0){
						ioWaitTime--;
					}
					
					
				}
				// Return control to scheduler
				
				if(ioWaitTime!=UNSET_WAIT_TIME && ioWaitTime<=0){
					if(!ioQueue.isEmpty()){
						readyQueue.add(ioQueue.remove());
						System.out.println("%% Process returned to ready queue");
					}
					// reset wait time if its empty
					else
						ioWaitTime=UNSET_WAIT_TIME;
				}
				
				// Check to see if io flag is set to true. If yes, move to IO queue
				if (cpu.isIoFlag()){
					cpu.setIoFlag(false);
					readyQueue.remove(p1);
					ioQueue.add(p1);
					System.out.println("%% Process 1 moved to IO Queue");
					// Set wait time if its not been set yet
					if(ioWaitTime==UNSET_WAIT_TIME){
						ioWaitTime=(int) (Math.floor(Math.random()*20));
					}
				}
				
				// reset quant counter
				cpu.setQuantCounter(quantValue);
				// Context switch here
				p1PgmCounter=cpu.getPgmCounter();
				prevProcess=1;
			}else if(ioQueue.contains(p1) && (p2PgmCounter<0 && p3PgmCounter<0)){
				readyQueue.add(ioQueue.remove());
				System.out.println("%% Process returned to ready queue");
			}
			else if(ioQueue.contains(p1) && ioQueue.contains(p2) && p3PgmCounter<0){
				readyQueue.add(ioQueue.remove());
				System.out.println("%% Process returned to ready queue");
			}
			else if(ioQueue.contains(p1) && ioQueue.contains(p3) && p2PgmCounter<0){
				readyQueue.add(ioQueue.remove());
				System.out.println("%% Process returned to ready queue");
			}
		
			}
			// PROCESS 2
			else if(((prevProcess==1) || ((prevProcess==3) && p1PgmCounter<0) || ((prevProcess==2 && p1PgmCounter<0) && (prevProcess==2 && p3PgmCounter<0)) || ((ioQueue.contains(p1) && ioQueue.contains(p3)) || (ioQueue.contains(p2) && p3PgmCounter<0) || (ioQueue.contains(p3) && p2PgmCounter<0))) && p2PgmCounter>=0){
				if (readyQueue.contains(p2)){
				if(prevProcess==1)
					System.out.println("**Context switch, process 1 replaced by process 2");
				else if (prevProcess==3)
					System.out.println("**Context switch, process 3 replaced by process 2");
				
				cpu.setProc(p2);
				cpu.setRegisters(p2Registers);
				cpu.setPgmCounter(p2PgmCounter);
				cpu.setArrInstruction(p2.getInstrArr());
			
					while (cpu.getQuantCounter()>0  && cpu.getPgmCounter()>=0){
						cpu.execModule(cpu.getArrInstruction().get(cpu.getPgmCounter()));
						if(ioWaitTime!=UNSET_WAIT_TIME && ioWaitTime>0){
							ioWaitTime--;
						}
						
					}
				
				// returns head process of ioQueue to readyQueue
				 if(ioWaitTime!=UNSET_WAIT_TIME && ioWaitTime<=0){
					 if(!ioQueue.isEmpty()){
						 readyQueue.add(ioQueue.remove());
						 System.out.println("%% Process returned to ready queue");
					 }
					 else
					// reset wait time if its empty
						 ioWaitTime=UNSET_WAIT_TIME;
				}
				if (cpu.isIoFlag()){
					cpu.setIoFlag(false);
					readyQueue.remove(p2);
					ioQueue.add(p2);
					System.out.println("%% Process 2 moved to IO Queue");
					// Set wait time if its not been set yet
					if(ioWaitTime==UNSET_WAIT_TIME){
						ioWaitTime=(int) (Math.floor(Math.random()*20));
					}
				}
				cpu.setQuantCounter(quantValue);
				// Context switch here
				p2PgmCounter=cpu.getPgmCounter();
				prevProcess=2;
				}
				else if(ioQueue.contains(p2) && (p1PgmCounter<0 && p3PgmCounter<0)){
					readyQueue.add(ioQueue.remove());
					System.out.println("%% Process returned to ready queue");
				}
				else if(ioQueue.contains(p2) && ioQueue.contains(p1) && p3PgmCounter<0){
					readyQueue.add(ioQueue.remove());
					System.out.println("%% Process returned to ready queue");
				}
				else if(ioQueue.contains(p2) && ioQueue.contains(p3) && p1PgmCounter<0){
					readyQueue.add(ioQueue.remove());
					System.out.println("%% Process returned to ready queue");
				}
			}
			// PROCESS 3
			else if(((prevProcess== 2) || (prevProcess==1 && p2PgmCounter<0) || ((prevProcess==3 && p2PgmCounter<0) && (prevProcess==3 && p1PgmCounter<0)) || ((ioQueue.contains(p2) && ioQueue.contains(p1)) || (ioQueue.contains(p2) && p3PgmCounter<0) || (ioQueue.contains(p3) && p2PgmCounter<0))) && p3PgmCounter>=0){
				if(readyQueue.contains(p3)){
				if(prevProcess==2)
					System.out.println("**Context switch, process 2 replaced by process 3");
				else if (prevProcess==1)
					System.out.println("**Context switch, process 1 replaced by process 3");
				cpu.setProc(p3);
				cpu.setRegisters(p3Registers);
				cpu.setPgmCounter(p3PgmCounter);
				cpu.setArrInstruction(p3.getInstrArr());
				
					while (cpu.getQuantCounter()>0  && cpu.getPgmCounter()>=0){
						cpu.execModule(cpu.getArrInstruction().get(cpu.getPgmCounter()));
						
						if(ioWaitTime!=UNSET_WAIT_TIME && ioWaitTime>0){
							ioWaitTime--;
						}
					}
				// returns head process of ioQueue to readyQueue
				if(ioWaitTime!=UNSET_WAIT_TIME && ioWaitTime<=0){
					if (!ioQueue.isEmpty()){
						readyQueue.add(ioQueue.remove());
						System.out.println("%% Process returned to ready queue");
					}
					// reset wait time if its empty
					else
						ioWaitTime=UNSET_WAIT_TIME;
				}
				if (cpu.isIoFlag()){
					cpu.setIoFlag(false);
					readyQueue.remove(p3);
					ioQueue.add(p3);
					System.out.println("%% Process 3 moved to IO Queue");
					// Set wait time if its not been set yet
					if(ioWaitTime==UNSET_WAIT_TIME){
						ioWaitTime=(int) (Math.floor(Math.random()*20));
					}
				}
				cpu.setQuantCounter(quantValue);
				// Context switch here
				p3PgmCounter=cpu.getPgmCounter();
				prevProcess=3;
			}
			else if(ioQueue.contains(p3) && (p1PgmCounter<0 && p2PgmCounter<0)){
				readyQueue.add(ioQueue.remove());
				System.out.println("%% Process returned to ready queue");
			}
			else if(ioQueue.contains(p3) && ioQueue.contains(p2) && p1PgmCounter<0){
				readyQueue.add(ioQueue.remove());
				System.out.println("%% Process returned to ready queue");
			}
			else if(ioQueue.contains(p3) && ioQueue.contains(p1) && p2PgmCounter<0){
				readyQueue.add(ioQueue.remove());
				System.out.println("%% Process returned to ready queue");
			}
				
			}
		}
		System.out.println("______________________________________");
		System.out.println("Summary of results:");
		System.out.println("p2 squared final value: "+p2Registers[4]);
		System.out.println("p3 sum of values: "+p3Registers[4]);
	}
	
}
