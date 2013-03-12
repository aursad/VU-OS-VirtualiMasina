package registers;

public class CRegister {
	private int C;
	
	public CRegister() {
		this.C = 0;
	}
	
	/**
	 * Loginio trigerio reikðmë
	 * @return C trigerio reikðmë
	 */
	public int get() {
		return this.C;
	}
	/**
	 * Nustatoma nauja loginio trigerio reikðmë
	 * @param C nauja trigerio reikðmë
	 */
	public void set(int C) {
		this.C = C;
	}
}
