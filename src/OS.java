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
	
	/*
	 * Konstruktoriai
	 */
	public OS() {
		this(0000);
	}
	OS(int PTR) {
		this.PTR = PTR;
	}
	
	/*
	 * Puslapiavimo registras
	 */
	public int getPTR() { 
		return this.PTR; 
	}
	public void setPTR(int NewPtr) {
		this.PTR = NewPtr;
	}
	/*
	 * Bendro naudojimo registras
	 */
	public int getR() {
		return this.R;
	}
	public void setR(int R) {
		this.R = R;
	}
	/*
	 * Komandø skaitliukas
	 */
	public int getIC() {
		return this.IC;
	}
	public void setIC(int IC) {
		this.IC = IC;
	}
	/*
	 * Poþymiø registras
	 */
	public int getC() {
		return this.C;
	}
	public void setC(int C) {
		this.C = C;
	}
	/*
	 * Taimerio skaitiklis
	 */
	public int getT() {
		return this.T;
	}
	public void setT() {
		this.T = this.T - 1;
	}
	public void updateT() {
		this.T = 10;
	}
	/*
	 * Supervizorinio pertraukimo registras
	 */
	public int getSI() {
		return this.SI;
	}
	public void setSI(int SI) {
		this.SI = SI;
	}
	/*
	 * Programinio pertraukimo registras
	 */
	public int getPI() {
		return this.PI;
	}
	public void setPI(int PI) {
		this.PI = PI;
	}
	/*
	 * Taimerio pertraukimo registras
	 */
	public int getTI() {
		return this.TI;
	}
	public void setTI(int TI) {
		this.TI = TI;
	}
	/*
	 * Procesoriaus darbo rëþimas
	 */
	public int getMODE() {
		return this.MODE;
	}
	public void setMODE(int MODE) {
		this.MODE = MODE;
	}
	/*
	 * Kanalø uþimtumo registras
	 */
	public int getCH() {
		return this.CH;
	}
	public void setCH(int CH) {
		this.CH = CH;
	}
}
