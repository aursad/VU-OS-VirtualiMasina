package OS;
/**
 * Operacinë sistema
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
	 * Komandø skaitiklis
	 */
	private int IC=0;
	/**
	 * Poþymiø registras
	 */
	private int C=0;
	/**
	 * Taimerio veiksmø registras
	 */
	private int T=10;
	/**
	 * Supervizoriniø pertraukimø registras
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
	 * Registras nusakantis procesoriaus darbo reþimà
	 */
	private int MODE=1;
	/**
	 * Kanalø uþimtumo registras 
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
	 * Registro PTR reikðmë
	 * @return PTR puslapiavimo registro reikðmë
	 */
	public int getPTR() { 
		return this.PTR; 
	}
	/**
	 * Nustatoma nauja PTR reikðmë
	 * @param NewPtr puslapiø reikðmë
	 */
	public void setPTR(int NewPtr) {
		this.PTR = NewPtr;
	}
	/**
	 * Bendrojo naudojimo registro reikðmë
	 * @return R registro reikðmë
	 */
	public int getR() {
		return this.R;
	}
	/**
	 * Nustatoma nauja bendrojo naudojimo registro reikðmë
	 * @param R nauja reikðmë
	 */
	public void setR(int R) {
		this.R = R;
	}
	/**
	 * Komandø skaitliuko reikðmë
	 * @return IC skaitliuko reikðmë
	 */
	public int getIC() {
		return this.IC;
	}
	/**
	 * Nustatoma nauja komandø skaitliuko reikðmë
	 * @param IC nauja skaitliuko reikðmë
	 */
	public void setIC(int IC) {
		this.IC = IC;
	}
	/**
	 * Loginio trigerio reikðmë
	 * @return C trigerio reikðmë
	 */
	public int getC() {
		return this.C;
	}
	/**
	 * Nustatoma nauja loginio trigerio reikðmë
	 * @param C nauja trigerio reikðmë
	 */
	public void setC(int C) {
		this.C = C;
	}
	/**
	 * Taimerio skaitiklis
	 * @return T taimerio reikðmë
	 */
	public int getT() {
		return this.T;
	}
	/**
	 * Sumaþinama taimerio registro reikðmë
	 */
	public void setT() {
		this.T = this.T - 1;
	}
	/**
	 * Atstotoma taimerio skaitiklio registro reikðmë
	 */
	public void updateT() {
		this.T = 10;
	}
	/**
	 * Supervizoriniø pertraukimø registras
	 * @return SI pertraukimo reikðmë
	 */
	public int getSI() {
		return this.SI;
	}
	/**
	 * Supervizoriniø pertraukimø registro reikðmës pakeitimas
	 * @param SI pertraukimo numeris
	 */
	public void setSI(int SI) {
		this.SI = SI;
	}
	/**
	 * Programinio pertraukimo registras
	 * @return PI pertraukimo reikðmë
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
	 * @return TI petraukimo reikðmë
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
	 * Procesoriaus darbo reþimo reikðmë
	 * @return MODE reþimo reikðmë
	 */
	public int getMODE() {
		return this.MODE;
	}
	/**
	 * Procesoriaus darbo reþimo pakeitimas
	 * @param MODE naujas reþimas
	 */
	public void setMODE(int MODE) {
		this.MODE = MODE;
	}
	/**
	 * Kanalø uþimtumo registras
	 * @return CH kanalo uþimtumo reikðmë
	 */
	public int getCH() {
		return this.CH;
	}
	/**
	 * Kanalø uþimtumo registro pakeitimas
	 * @param CH pertraukimo numeris
	 */
	public void setCH(int CH) {
		this.CH = CH;
	}
	/**
	 * Load Register
	 * Registras R ágauna a reikðmæ
	 * @param a 
	 * @return R Nauja bendrojo naudojimo registro reikðmë
	 */
	public int LR(int a) {
		this.R = a;
		return this.R;
	}
	/**
	 * Save Register
	 * Ásimenama registro reikðmë.
	 * @param a
	 * @return a
	 */
	public int SR(int a) {
		a = this.R;
		return a;
	}
	/**
	 * Null
	 * R registrui priskiriama reikðmë 0.
	 * @param R Bendrojo naudojimo registras
	 */
	public void NL() {
		this.R = 0;
	}
	/**
	 * Addition
	 * @param a
	 * @return
	 */
	public int AD(int a) {
		this.R = this.R + a;
		return this.R;
	}
	/**
	 * Substract
	 * @param a
	 * @return
	 */
	public int SU(int a) {
		this.R = this.R - a;
		return this.R;
	}
	/**
	 * Multiply
	 * @param a
	 * @return
	 */
	public int MU(int a) {
		this.R = this.R * a;
		return this.R;
	}
	/**
	 * Divide
	 * @param a
	 * @return
	 */
	public int DI(int a) {
		this.R = this.R / a;
		return this.R;
	}
	/**
	 * Module
	 * @param a
	 * @return
	 */
	public int MO(int a) {
		this.R = this.R % a;
		return this.R;
	}
	/**
	 * Compare Register
	 * Palyginamos registrø reikðmës
	 * @param a
	 */
	public void CR(int a) {
		if (this.R > a) {
			this.C = 1;
		} else if(this.R < a) {
			this.C = 2;
		} else {
			this.C = 0;
		}
	}
	/**
	 * Jump
	 * Valdymo perdavimo komanda
	 * @param a Registro adresas
	 */
	public void JP(int a) {
		this.IC = a;
	}
	/**
	 * Jump if equal
	 * Perduodamas valdymas jei Poþymio registras C yra 0
	 * @param a Registro adresas
	 */
	public void JE(int a) {
		if (this.C == 0) {
			this.IC = a;
		}
	}
	/**
	 * Jump if greater
	 * Perduodamas valdymas jei Poþymio registras C yra 1
	 * @param a Registro adresas
	 */
	public void JG(int a) {
		if (this.C == 1) {
			this.IC = a;
		}
	}
	/**
	 * Jump if less
	 * Perduodamas valdymas jei Poþymio registras C yra 2
	 * @param a Registro adresas
	 */
	public void JL(int a) {
		if (this.C == 2) {
			this.IC = a;
		}
	}
}
