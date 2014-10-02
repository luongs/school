/**
 * Author Sebastien Luong
 * Comp 346 A2
 */

import java.util.ArrayList;


public class Process {

	int processNum;
	int param;
	int paramArrPos=0;
	ArrayList<Instruction> instrArr;
	final int INITIALCOUNTER=0;
	final int INCREMENTVALUE=1;
	
	Process(){
		processNum=0;
		param=0;
		paramArrPos=0;
		instrArr=null;
	}
	
	Process(int processNum, int param, int paramArrPos ){
		this.processNum=processNum;
		this.param=param;
		this.paramArrPos=paramArrPos;
		
		instrArr=new ArrayList<Instruction>();
		
		if (processNum==1){
			/*
			 * Reg0: Value to be searched
			 * Reg1: Array size
			 * Reg2: Starting position
			 * Reg3: Counter
			 * Reg4: Number to compare
			 */
			Instruction p11=new Instruction(Instruction.LOAD,0,0);
			Instruction p12a=new Instruction(Instruction.ASSIGN_TO_REG,1,1);
			Instruction p12=new Instruction(Instruction.LOAD,1,1);
			Instruction p13=new Instruction(Instruction.ASSIGN_TO_REG,2,2);
			Instruction p14=new Instruction(Instruction.ASSIGN_TO_REG,3,1);
			Instruction p15=new Instruction(Instruction.DECREMENT,1);
			Instruction p16=new Instruction(Instruction.JUMP_EQ,2,1,11);
			Instruction p17=new Instruction(Instruction.ADD,2,3,2);
			Instruction p18=new Instruction(Instruction.LOAD,2,4);
			Instruction p19=new Instruction(Instruction.JUMP_EQ,0,4,12);
			Instruction p111=new Instruction(Instruction.GOTO,6);
			Instruction p112=new Instruction(Instruction.EXIT);
			Instruction p113=new Instruction(Instruction.PRINT,1);
			Instruction p114=new Instruction(Instruction.GOTO,6);
			instrArr.add(p11);
			instrArr.add(p12a);
			instrArr.add(p12);
			instrArr.add(p13);
			instrArr.add(p14);
			instrArr.add(p15);
			instrArr.add(p16);
			instrArr.add(p17);
			instrArr.add(p18);
			instrArr.add(p19);
			instrArr.add(p111);
			instrArr.add(p112);
			instrArr.add(p113);
			instrArr.add(p114);
		}
		else if (processNum==2){
			/*
			 * Reg0: Value to be squared
			 * Reg1: Max bound to search
			 * Reg2: Counter
			 * Reg3: Incrementing value
			 * Reg4: Current value
			 * 
			 */
			//param is value to be squared
			Instruction p20=new Instruction(Instruction.ASSIGN_TO_REG,0,param);
			//If the user inputs 0, then squared value is 0 
			Instruction p20a=new Instruction(Instruction.JUMP_EQ,0,5,12);
			Instruction p21=new Instruction(Instruction.ASSIGN_FROM_REG,1,0);
			Instruction p21a=new Instruction(Instruction.DECREMENT,1);
			Instruction p22=new Instruction(Instruction.ASSIGN_TO_REG,2,INITIALCOUNTER);
			Instruction p23=new Instruction(Instruction.ASSIGN_TO_REG,3,INCREMENTVALUE);
			Instruction p24=new Instruction(Instruction.ASSIGN_FROM_REG,4,0);
			Instruction p25=new Instruction(Instruction.JUMP_EQ,2,1,12);
			Instruction p26=new Instruction(Instruction.PRINT,2);
			Instruction p27=new Instruction(Instruction.ADD,0,4,4);
			Instruction p28=new Instruction(Instruction.INCREMENT,2);
			Instruction p29=new Instruction(Instruction.GOTO,7);
			Instruction p210=new Instruction(Instruction.PRINT,2);
			Instruction p211=new Instruction(Instruction.EXIT);
			instrArr.add(p20);
			instrArr.add(p20a);
			instrArr.add(p21);
			instrArr.add(p21a);
			instrArr.add(p22);
			instrArr.add(p23);
			instrArr.add(p24);
			instrArr.add(p25);
			instrArr.add(p26);
			instrArr.add(p27);
			instrArr.add(p28);
			instrArr.add(p29);
			instrArr.add(p210);
			instrArr.add(p211);
		}
		else if (processNum==3){
			/*
			 * Reg0: nth integer to search for 
			 * Reg1: Max bound to search for 
			 * Reg2: Counter
			 * Reg3: Increment value
			 * Reg4: Current value
			 * Reg5: Next value 
			 */
			
			// param is range of value to search in
			Instruction p30 = new Instruction(Instruction.ASSIGN_TO_REG,0,param);
			Instruction p31 = new Instruction(Instruction.ASSIGN_TO_REG,1,param);
			// to get the right bound value
			Instruction p32 = new Instruction(Instruction.DECREMENT,1);
			Instruction p33 = new Instruction(Instruction.ASSIGN_TO_REG,2,INITIALCOUNTER);
			Instruction p34 = new Instruction(Instruction.ASSIGN_TO_REG,3,INCREMENTVALUE);
			Instruction p35 = new Instruction(Instruction.ASSIGN_TO_REG,4,1);
			Instruction p36 = new Instruction(Instruction.ADD,3,4,5);
			Instruction p37 = new Instruction(Instruction.JUMP_EQ,1,2,13);
			//Print current value
			Instruction p38 = new Instruction(Instruction.ADD,4,5,4);
			Instruction p39 = new Instruction(Instruction.PRINT,3);
			Instruction p310 = new Instruction(Instruction.INCREMENT,2);
			Instruction p311 = new Instruction(Instruction.INCREMENT,5);
			Instruction p312 = new Instruction(Instruction.GOTO,7);
			Instruction p313 = new Instruction(Instruction.PRINT,3);
			Instruction p314 = new Instruction(Instruction.EXIT);
			instrArr.add(p30);
			instrArr.add(p31);
			instrArr.add(p32);
			instrArr.add(p33);
			instrArr.add(p34);
			instrArr.add(p35);
			instrArr.add(p36);
			instrArr.add(p37);
			instrArr.add(p38);
			instrArr.add(p39);
			instrArr.add(p310);
			instrArr.add(p311);
			instrArr.add(p312);
			instrArr.add(p313);
			instrArr.add(p314);
		}
	}

	public int getProcessNum() {
		return processNum;
	}

	public void setProcessNum(int processNum) {
		this.processNum = processNum;
	}

	public ArrayList<Instruction> getInstrArr() {
		return instrArr;
	}

	public void setInstrArr(ArrayList<Instruction> instrArr) {
		this.instrArr = instrArr;
	}

	public int getParam() {
		return param;
	}

	public void setParam(int param) {
		this.param = param;
	}

	public int getParamArrPos() {
		return paramArrPos;
	}

	public void setParamArrPos(int paramArrPos) {
		this.paramArrPos = paramArrPos;
	}
	
	
}
