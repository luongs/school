/**
 * Author Sebastien Luong
 * Comp 346 A2
 */


import java.util.ArrayList;



public class CPU {

	private int[] registers;
	private int[] localMem;
	private int pgmCounter;
	private int quantCounter;
	private boolean ioFlag;
	private ArrayList<Instruction> arrInstruction;
	private Process proc; 
	final int MEMSIZE=1000;
	final int REGISTER_AMT=8;
	
	public CPU(){
		registers= new int[REGISTER_AMT];
		localMem=new int[MEMSIZE];
		pgmCounter=0;
		quantCounter=0;
		ioFlag=false;
		arrInstruction=null;
		proc=null;
	}
	
	public CPU(int quantCounter){
		registers= new int[REGISTER_AMT];
		localMem=new int[MEMSIZE];
		pgmCounter=0;
		this.quantCounter=quantCounter;
		ioFlag=false;
		arrInstruction=null;
		proc=null;
	}
	
	
	public void execModule(Instruction instr)
	{
		int result; 
		if (instr.getName().equals(Instruction.ADD)){
			// access register values for first two parameter in Instruction
			result=registers[instr.getParam1()]+ registers[instr.getParam2()];
			// save result in third register
			registers[instr.getParam3()]=result;
			pgmCounter++;
			quantCounter--;
		}
		else if (instr.getName().equals(Instruction.SUB)){
			result=registers[instr.getParam2()]- registers[instr.getParam1()];
			// save result in third register
			registers[instr.getParam3()]=result;
			pgmCounter++;
			quantCounter--;
		}
		else if (instr.getName().equals(Instruction.INCREMENT)){
			registers[instr.getParam1()]++;
			pgmCounter++;
			quantCounter--;
		}
		else if (instr.getName().equals(Instruction.DECREMENT)){
			registers[instr.getParam1()]--;
			pgmCounter++;
			quantCounter--;
		}
		else if (instr.getName().equals(Instruction.LOAD)){
			// save local memory param in register
			registers[instr.getParam2()]=localMem[registers[instr.getParam1()]];
			pgmCounter++;
			quantCounter--;
		}
		else if (instr.getName().equals(Instruction.STORE)){
			registers[instr.getParam1()]=registers[instr.getParam2()];
			pgmCounter++;
			quantCounter--;
		}
		else if (instr.getName().equals(Instruction.ASSIGN_TO_REG)){
			// Store programmer input value from param2 into the register
			registers[instr.getParam1()]=instr.getParam2();
			pgmCounter++;
			quantCounter--;
		}
		else if (instr.getName().equals(Instruction.ASSIGN_FROM_REG)){
			// Store value from register2 into register1
			registers[instr.getParam1()]=registers[instr.getParam2()];
			pgmCounter++;
			quantCounter--;
		}
		else if (instr.getName().equals(Instruction.GOTO)){
			// go to particular instruction number
			pgmCounter=instr.getParam1();
			quantCounter--;
			execModule(arrInstruction.get(pgmCounter));
		
		}
		else if (instr.getName().equals(Instruction.JUMP_EQ)){
			// jump to instruction in param3 if both values in param registers are equal
			if(registers[instr.getParam1()]==registers[instr.getParam2()]){
				quantCounter--;
				pgmCounter=instr.getParam3();
				execModule(arrInstruction.get(pgmCounter));
				}
			else
				pgmCounter++;
			quantCounter--;			
		}
		else if (instr.getName().equals(Instruction.PRINT)){
			if(instr.getParam1()==Instruction.FIRSTPROCESS)
				System.out.println("-- Process "+instr.getParam1()+", output = found match at position "+registers[2]);
			else if (instr.getParam1()==Instruction.SECONDPROCESS){
				if (registers[2]!=registers[1])
					System.out.println("-- Process "+proc.getProcessNum()+", output = current value for squared number is "+registers[4]);
				else
					System.out.println("-- Process "+proc.getProcessNum()+", output = final value for squared number is "+registers[4]);

			}
			else if (instr.getParam1()==Instruction.THIRDPROCESS)
				if (registers[2]!=registers[1])
					System.out.println("-- Process "+proc.getProcessNum()+" Process "+instr.getParam1()+" running total for operation "+registers[2]+" is "+registers[4]);
				else
					System.out.println("-- Process "+proc.getProcessNum()+" Process "+instr.getParam1()+" final value is "+registers[4]);
			pgmCounter++;
			quantCounter--;
			ioFlag=true;
		}
		else if (instr.getName().equals(Instruction.EXIT)){
			//terminate process
			System.out.println("-- Process "+proc.getProcessNum()+", output = Process terminated");
			pgmCounter=-1;
			//System.exit(0);
			
		}
	}
	
	public int[] getRegisters() {
		return registers;
	}

	public void setRegisters(int[] registers) {
		this.registers = registers;
	}

	public int getPgmCounter() {
		return pgmCounter;
	}

	public void setPgmCounter(int pgmCounter) {
		this.pgmCounter = pgmCounter;
	}

	public int getQuantCounter() {
		return quantCounter;
	}

	public void setQuantCounter(int quantCounter) {
		this.quantCounter = quantCounter;
	}

	public boolean isIoFlag() {
		return ioFlag;
	}

	public void setIoFlag(boolean ioFlag) {
		this.ioFlag = ioFlag;
	}

	public int[] getLocalMem() {
		return localMem;
	}

	public void setLocalMem(int[] localMem) {
		this.localMem = localMem;
	}

	public ArrayList<Instruction> getArrInstruction() {
		return arrInstruction;
	}

	public void setArrInstruction(ArrayList<Instruction> arrInstruction) {
		this.arrInstruction = arrInstruction;
	}

	public Process getProc() {
		return proc;
	}

	public void setProc(Process proc) {
		this.proc = proc;
	}	
	
}
