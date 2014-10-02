/**
 * Author Sebastien Luong
 * Comp 346 A2
 */

public class Instruction {
	private String name;
	private int param1;
	private int param2;
	private int param3;
	
	// Constant variables
	static final String ADD ="add";
	static final String SUB ="sub";
	static final String INCREMENT="increment";
	static final String DECREMENT="decrement";
	static final String LOAD = "load";
	static final String LOADREG = "loadreg";
	static final String STORE = "store";
	static final String ASSIGN_TO_REG = "assign_to_reg";
	static final String ASSIGN_FROM_REG= "assign_from_reg";
	static final String GOTO = "goto";
	static final String JUMP_EQ = "jump_eq";
	static final String PRINT = "print";
	static final String EXIT = "exit";
	static final int FIRSTPROCESS=1;
	static final int SECONDPROCESS=2;
	static final int THIRDPROCESS=3;
	
	
	public Instruction(){
		name=null;
		param1=0;
		param2=0;
		param3=0;
	}
	
	public Instruction(String name){
		this.name=name;
	}
	
	public Instruction(String name, int param1){
		this.name=name;
		this.param1=param1;
	}
	
	public Instruction(String name, int param1, int param2){
		this.name=name;
		this.param1=param1;
		this.param2=param2;
	}

	public Instruction(String name, int param1, int param2, int param3){
		this.name=name;
		this.param1=param1;
		this.param2=param2;
		this.param3=param3;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParam1() {
		return param1;
	}

	public void setParam1(int param1) {
		this.param1 = param1;
	}

	public int getParam2() {
		return param2;
	}

	public void setParam2(int param2) {
		this.param2 = param2;
	}

	public int getParam3() {
		return param3;
	}

	public void setParam3(int param3) {
		this.param3 = param3;
	}
	
	
}
