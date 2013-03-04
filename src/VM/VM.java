package VM;

/**
 * Virtuali ma�ina
 * @author Aurimas
 *
 */
public class VM {
	/**
	 * Bendrojo naudojimo registras
	 */
	private int R=0;
	/**
	 * Komand� skaitiklis
	 */
	private int IC=0;
	/**
	 * Po�ymi� registras
	 */
	private int C=0;
	
	
	public VM() {
		this(0000);
	}
	VM(int R) {
		this.R = R;
	}
	/**
	 * Bendrojo naudojimo registro reik�m�
	 * @return R registro reik�m�
	 */
	public int getR() {
		return this.R;
	}
	/**
	 * Nustatoma nauja bendrojo naudojimo registro reik�m�
	 * @param R nauja reik�m�
	 */
	public void setR(int R) {
		this.R = R;
	}
	/**
	 * Komand� skaitliuko reik�m�
	 * @return IC skaitliuko reik�m�
	 */
	public int getIC() {
		return this.IC;
	}
	/**
	 * Nustatoma nauja komand� skaitliuko reik�m�
	 * @param IC nauja skaitliuko reik�m�
	 */
	public void setIC(int IC) {
		this.IC = IC;
	}
	/**
	 * Loginio trigerio reik�m�
	 * @return C trigerio reik�m�
	 */
	public int getC() {
		return this.C;
	}
}
