package registers;

public class DataRegister {
	
	private int R;
	public DataRegister() {
		this.R = 0;
	}
	/**
	 * Bendrojo naudojimo registro reik�m�
	 * @return R registro reik�m�
	 */
	public int get() {
		return this.R;
	}
	/**
	 * Nustatoma nauja bendrojo naudojimo registro reik�m�
	 * @param R nauja reik�m�
	 */
	public void set(int R) {
		this.R = R;
	}
}
