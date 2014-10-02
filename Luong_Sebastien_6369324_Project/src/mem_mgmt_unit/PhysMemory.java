package mem_mgmt_unit;

import java.util.LinkedList;
import java.util.Queue;
/**
 * Frame table
 * @author Sebastien Luong
 *
 */
public class PhysMemory {
	static int frameIndex;
	static int[][] frameTable;
	static int[] frameProcessTable;
	static Queue<Integer> LRUQueue; 
	static final int FRAMEAMT=1000;
	static final int VALUEAMT=16;
	
	
	public PhysMemory(){
		frameIndex=0;
		frameTable=new int[FRAMEAMT][VALUEAMT];
		LRUQueue=new LinkedList<Integer>();
		frameProcessTable=new int[FRAMEAMT];
	}
	
	public static void incFrameIndex(){
		frameIndex++;
	}

	public int getFRAMEAMT() {
		return FRAMEAMT;
	}

	public int getVALUEAMT() {
		return VALUEAMT;
	}
	
}
