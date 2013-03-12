package registers;

public class ModeRegister {
	
	private int Mode;

	ModeRegister() {
		this.Mode = 0;
	}
	/**
	 * Procesoriaus darbo reþimo reikðmë
	 * @return MODE reþimo reikðmë
	 */
	public int get() {
		return this.Mode;
	}
	/**
	 * Procesoriaus darbo reþimo pakeitimas
	 * @param MODE naujas reþimas
	 */
	public void set(int MODE) {
		this.Mode = MODE;
	}
}
