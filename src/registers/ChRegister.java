package registers;

public class ChRegister {
	
	private int CH;
	ChRegister() {
		this.CH = 0;
	}
	
	/**
	 * Kanal� u�imtumo registras
	 * @return CH kanalo u�imtumo reik�m�
	 */
	public int get() {
		return this.CH;
	}
	/**
	 * Kanal� u�imtumo registro pakeitimas
	 * @param CH pertraukimo numeris
	 */
	public void set(int CH) {
		this.CH = CH;
	}
}
