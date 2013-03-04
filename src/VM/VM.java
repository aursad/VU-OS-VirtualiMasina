package VM;

/**
 * Virtuali mağina
 * @author Aurimas
 *
 */
public class VM {
	/**
	 * Bendrojo naudojimo registras
	 */
	private int R=0;
	/**
	 * Komandø skaitiklis
	 */
	private int IC=0;
	/**
	 * Poşymiø registras
	 */
	private int C=0;
	
	
	public VM() {
		this(0000);
	}
	VM(int R) {
		this.R = R;
	}
	/**
	 * Bendrojo naudojimo registro reikğmë
	 * @return R registro reikğmë
	 */
	public int getR() {
		return this.R;
	}
	/**
	 * Nustatoma nauja bendrojo naudojimo registro reikğmë
	 * @param R nauja reikğmë
	 */
	public void setR(int R) {
		this.R = R;
	}
	/**
	 * Komandø skaitliuko reikğmë
	 * @return IC skaitliuko reikğmë
	 */
	public int getIC() {
		return this.IC;
	}
	/**
	 * Nustatoma nauja komandø skaitliuko reikğmë
	 * @param IC nauja skaitliuko reikğmë
	 */
	public void setIC(int IC) {
		this.IC = IC;
	}
	/**
	 * Loginio trigerio reikğmë
	 * @return C trigerio reikğmë
	 */
	public int getC() {
		return this.C;
	}
}
