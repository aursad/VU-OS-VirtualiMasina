package registers;

public class ChRegister {
	
	private int CH;
	ChRegister() {
		this.CH = 0;
	}
	
	/**
	 * Kanalø uþimtumo registras
	 * @return CH kanalo uþimtumo reikðmë
	 */
	public int get() {
		return this.CH;
	}
	/**
	 * Kanalø uþimtumo registro pakeitimas
	 * @param CH pertraukimo numeris
	 */
	public void set(int CH) {
		this.CH = CH;
	}
}
