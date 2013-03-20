package registers;

import RM.RM;

public class PTRRegister {

	private String PTR;

	public PTRRegister(String PTR) {
		this.PTR = PTR;
	}
	/**
	 * Registro PTR reik�m�
	 * @return PTR puslapiavimo registro reik�m�
	 */
	public String get() { 
		return this.PTR; 
	}
	private int getPTRNumber(int key) {
		String[] value = this.PTR.split("(?<=\\G.{1})");
		int value1 = Integer.parseInt(value[key]);
		return value1;
	}
	public int getBlock() {
		return getPTRNumber(1);
	}
	public int getPageTable() {
		return getPTRNumber(2)*10+getPTRNumber(3);
	}
	/**
	 * Nustatoma nauja PTR reik�m�
	 * @param NewPtr puslapi� reik�m�
	 */
	public void set(String NewPtr) {
		this.PTR = NewPtr;
	}
}
