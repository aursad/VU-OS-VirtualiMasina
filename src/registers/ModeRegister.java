package registers;

public class ModeRegister {
	
	private int Mode;

	ModeRegister() {
		this.Mode = 0;
	}
	/**
	 * Procesoriaus darbo re�imo reik�m�
	 * @return MODE re�imo reik�m�
	 */
	public int get() {
		return this.Mode;
	}
	/**
	 * Procesoriaus darbo re�imo pakeitimas
	 * @param MODE naujas re�imas
	 */
	public void set(int MODE) {
		this.Mode = MODE;
	}
}
