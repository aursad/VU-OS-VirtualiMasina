package OS;

/**
 * Operacin�s sistemos komandos
 * @author Aurimas
 *
 */
public class Records {

	/**
	 * Load Register
	 * Registras R �gauna a reik�m�
	 * @param R Bendrojo naudojimo registras
	 * @param a 
	 * @return R Nauja bendrojo naudojimo registro reik�m�
	 */
	public int LR(int R, int a) {
		R = a;
		return R;
	}
	/**
	 * Save Register
	 * �simenama registro reik�m�.
	 * @param a
	 * @param R Bendrojo naudojimo registras
	 * @return a
	 */
	public int SR(int a, int R) {
		a = R;
		return a;
	}
	/**
	 * Null
	 * R registrui priskiriama reik�m� 0.
	 * @param R Bendrojo naudojimo registras
	 */
	public void NL(int R) {
		R = 0;
	}
	/**
	 * Adition
	 * @param R
	 * @param a
	 * @return
	 */
	public int AD(int R, int a) {
		R = R + a;
		return R;
	}
	/**
	 * Substract
	 * @param R
	 * @param a
	 * @return
	 */
	public int SU(int R, int a) {
		R = R - a;
		return R;
	}
	/**
	 * Multiply
	 * @param R
	 * @param a
	 * @return
	 */
	public int MU(int R, int a) {
		R = R * a;
		return R;
	}
	/**
	 * Divide
	 * @param R
	 * @param a
	 * @return
	 */
	public int DI(int R, int a) {
		R = R / a;
		return R;
	}
	/**
	 * Module
	 * @param R
	 * @param a
	 * @return
	 */
	public int MO(int R, int a) {
		R = R % a;
		return R;
	}

}
