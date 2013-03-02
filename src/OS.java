/**
 * Operacin� sistema
 * @author Aurimas Sadauskas
 * @version 1.0
 * @since 2013.03.02
 */
public class OS {
	/**
	 * Puslapiavimo registras
	 */
	private int PTR;
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
	/**
	 * Taimerio veiksm� registras
	 */
	private int T=10;
	/**
	 * Supervizorini� pertraukim� registras
	 */
	private int SI=0;
	/**
	 * Programinio pertraukimo registras
	 */
	private int PI=0;
	/**
	 * Taimerio pertraukimo registras
	 */
	private int TI=0;
	/**
	 * Registras nusakantis procesoriaus darbo re�im�
	 */
	private int MODE=1;
	/**
	 * Kanal� u�imtumo registras 
	 */
	private int CH=0;
	
	/**
	 * Konstruktorius
	 */
	public OS() {
		this(0000);
	}
	OS(int PTR) {
		this.PTR = PTR;
	}
	
	/**
	 * Registro PTR reik�m�
	 * @return PTR puslapiavimo registro reik�m�
	 */
	public int getPTR() { 
		return this.PTR; 
	}
	/**
	 * Nustatoma nauja PTR reik�m�
	 * @param NewPtr puslapi� reik�m�
	 */
	public void setPTR(int NewPtr) {
		this.PTR = NewPtr;
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
	/**
	 * Nustatoma nauja loginio trigerio reik�m�
	 * @param C nauja trigerio reik�m�
	 */
	public void setC(int C) {
		this.C = C;
	}
	/**
	 * Taimerio skaitiklis
	 * @return T taimerio reik�m�
	 */
	public int getT() {
		return this.T;
	}
	/**
	 * Suma�inama taimerio registro reik�m�
	 */
	public void setT() {
		this.T = this.T - 1;
	}
	/**
	 * Atstotoma taimerio skaitiklio registro reik�m�
	 */
	public void updateT() {
		this.T = 10;
	}
	/**
	 * Supervizorini� pertraukim� registras
	 * @return SI pertraukimo reik�m�
	 */
	public int getSI() {
		return this.SI;
	}
	/**
	 * Supervizorini� pertraukim� registro reik�m�s pakeitimas
	 * @param SI pertraukimo numeris
	 */
	public void setSI(int SI) {
		this.SI = SI;
	}
	/**
	 * Programinio pertraukimo registras
	 * @return PI pertraukimo reik�m�
	 */
	public int getPI() {
		return this.PI;
	}
	/**
	 * Programinio pertraukimo registro pakeitimas
	 * @param PI pertraukimo numeris
	 */
	public void setPI(int PI) {
		this.PI = PI;
	}
	/**
	 * Taimerio pertraukimo registras
	 * @return TI petraukimo reik�m�
	 */
	public int getTI() {
		return this.TI;
	}
	/**
	 * Taimerio pertraukimo registro pakeitimas
	 * @param TI pertraukimo numeris
	 */
	public void setTI(int TI) {
		this.TI = TI;
	}
	/**
	 * Procesoriaus darbo re�imo reik�m�
	 * @return MODE re�imo reik�m�
	 */
	public int getMODE() {
		return this.MODE;
	}
	/**
	 * Procesoriaus darbo re�imo pakeitimas
	 * @param MODE naujas re�imas
	 */
	public void setMODE(int MODE) {
		this.MODE = MODE;
	}
	/**
	 * Kanal� u�imtumo registras
	 * @return CH kanalo u�imtumo reik�m�
	 */
	public int getCH() {
		return this.CH;
	}
	/**
	 * Kanal� u�imtumo registro pakeitimas
	 * @param CH pertraukimo numeris
	 */
	public void setCH(int CH) {
		this.CH = CH;
	}
}
