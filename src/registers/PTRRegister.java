package registers;

import RM.RM;

public class PTRRegister {

	private int PTR;

	public PTRRegister() {
		this.PTR = 0;
	}
	/**
	 * Registro PTR reikðmë
	 * @return PTR puslapiavimo registro reikðmë
	 */
	public int get() { 
		return this.PTR; 
	}
	/**
	 * Nustatoma nauja PTR reikðmë
	 * @param NewPtr puslapiø reikðmë
	 */
	public void set(int NewPtr) {
		this.PTR = NewPtr;
	}
}
