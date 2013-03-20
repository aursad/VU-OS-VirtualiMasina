package registers;

import RM.RM;

public class PTRRegister {

	private int PTR;

	public PTRRegister() {
		this.PTR = 0;
	}
	/**
	 * Registro PTR reik�m�
	 * @return PTR puslapiavimo registro reik�m�
	 */
	public int get() { 
		return this.PTR; 
	}
	/**
	 * Nustatoma nauja PTR reik�m�
	 * @param NewPtr puslapi� reik�m�
	 */
	public void set(int NewPtr) {
		this.PTR = NewPtr;
	}
}
