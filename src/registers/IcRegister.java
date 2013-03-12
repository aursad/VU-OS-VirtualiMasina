package registers;

public class IcRegister {
	
	private int IC;
	public IcRegister() {
		this.IC = 0;
	}
	/**
	 * Komand� skaitliuko reik�m�
	 * @return IC skaitliuko reik�m�
	 */
	public int get() {
		return this.IC;
	}
	/**
	 * Nustatoma nauja komand� skaitliuko reik�m�
	 * @param IC nauja skaitliuko reik�m�
	 */
	public void set(int IC) {
		this.IC = IC;
	}
}
