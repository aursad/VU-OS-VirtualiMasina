package registers;

public class DataRegister {
	
	private int R;
	public DataRegister() {
		this.R = 0;
	}
	/**
	 * Bendrojo naudojimo registro reikğmë
	 * @return R registro reikğmë
	 */
	public int get() {
		return this.R;
	}
	/**
	 * Nustatoma nauja bendrojo naudojimo registro reikğmë
	 * @param R nauja reikğmë
	 */
	public void set(int R) {
		this.R = R;
	}
}
