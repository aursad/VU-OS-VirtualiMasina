package registers;

public class CRegister {
	private int C;
	
	public CRegister() {
		this.C = 0;
	}
	
	/**
	 * Loginio trigerio reik�m�
	 * @return C trigerio reik�m�
	 */
	public int get() {
		return this.C;
	}
	/**
	 * Nustatoma nauja loginio trigerio reik�m�
	 * @param C nauja trigerio reik�m�
	 */
	public void set(int C) {
		this.C = C;
	}
}
