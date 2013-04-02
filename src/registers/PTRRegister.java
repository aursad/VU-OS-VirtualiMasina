package registers;

public class PTRRegister {

	//private String PTR;
	private int[] PTR = new int[] { 0, 0, 0, 0};

	public PTRRegister(int x, int N, int x1, int x2) {
		this.PTR[0] = x;
		this.PTR[1] = N;
		this.PTR[2] = x1;
		this.PTR[3] = x2;
	}
	/**
	 * Registro PTR reik�m�
	 * @return PTR puslapiavimo registro reik�m�
	 */
	public String get() { 
		return ""+this.PTR[0]+this.PTR[1]+this.PTR[2]+this.PTR[3]; 
	}
	public int getBlock() {
		return this.PTR[1];
	}
	public int getPageTable() {
		return this.PTR[2]*10+this.PTR[3];
	}
	/**
	 * Nustatoma nauja PTR reik�m�
	 * @param NewPtr puslapi� reik�m�
	 */
	public void setTableSize(int N) {
		this.PTR[1] = N;
	}
	public void setPageTableNumber(int x1, int x2) {
		this.PTR[2] = x1;
		this.PTR[3] = x2;
	}
}
