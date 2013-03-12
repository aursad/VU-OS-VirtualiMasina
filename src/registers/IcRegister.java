package registers;

public class IcRegister {
	
	private int IC;
	public IcRegister() {
		this.IC = 0;
	}
	/**
	 * Komandø skaitliuko reikğmë
	 * @return IC skaitliuko reikğmë
	 */
	public int get() {
		return this.IC;
	}
	/**
	 * Nustatoma nauja komandø skaitliuko reikğmë
	 * @param IC nauja skaitliuko reikğmë
	 */
	public void set(int IC) {
		this.IC = IC;
	}
}
