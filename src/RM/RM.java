package RM;

import registers.CRegister;
import registers.ChRegister;
import registers.DataRegister;
import registers.IcRegister;
import registers.IntRegister;
import registers.ModeRegister;
import registers.PTRRegister;
import registers.TimerMechRegister;
import registers.TimerRegister;

/**
 * Operacinė sistema
 * @author Aurimas Sadauskas
 * @version 1.0.9
 * @since 2013.03.02
 */
public class RM {
	/**
	 * Puslapiavimo registras
	 */
	static public PTRRegister PTR;
	/**
	 * Bendrojo naudojimo registras
	 */
	public DataRegister R;
	/**
	 * Komandų skaitiklis
	 */
	public static IcRegister IC;
	/**
	 * Požymių registras
	 */
	public CRegister C;
	/**
	 * Taimerio veiksmų registras
	 */
	static public TimerRegister T;
	/**
	 * Supervizorinių pertraukimų registras
	 */
	static public IntRegister SI;
	/**
	 * Programinio pertraukimo registras
	 */
	static public IntRegister PI;
	/**
	 * Taimerio pertraukimo registras
	 */
	static public TimerMechRegister TI;
	/**
	 * Registras nusakantis procesoriaus darbo režimą
	 */
	static public ModeRegister MODE;
	/**
	 * Kanalų užimtumo registras 
	 */
	static public ChRegister CH;
	public static RealMemory memory;
	static public PageTable PageTable;
	/**
	 * Konstruktorius
	 */
	public RM() {
        memory = new RealMemory(100);
		PTR = new PTRRegister(0, 9, 1, 0);
		PageTable = new PageTable();
		R = new DataRegister();
        IC = new IcRegister();
        C = new CRegister();
        T = new TimerRegister(0); 
        TI = new TimerMechRegister(10);
        MODE  = new ModeRegister();
        PI = new IntRegister();
        SI = new IntRegister();
        CH = new ChRegister();
	}
	
	
	/**
	 * Iš atminties adresu XX paimama reikšmę, bei pašalinami tarpai
	 * @param xx Atminties adresas
	 * @return XY Reikšmė adresu XX
	 */
	public int getWord(int xx) {
		String Word = memory.getWord(xx);
		Word = Word.replaceAll("\\s", "");
		int XY = Integer.parseInt(Word);
		return XY;
	}
	/**
	 * Atminties vietai adresu XX nustatoma nauja reikðmë
	 * @param xx Atminties adresas
	 * @param R Nauja reikðmë
	 */
	public void setWord(int xx, int R) {
		memory.set(xx, Integer.toString(R));
	}
	/**
	 * Load Register
	 * @param xx
	 */
	public void LR(int xx) {
		R.set(getWord(xx));
		IC.set(IC.get()+1);
	}
	/**
	 * Save Register
	 * @param xx
	 */
	public void SR(int xx) {
		setWord(xx, R.get());
		IC.set(IC.get()+1);
	}
	/**
	 * Null
	 */
	public void NL() {
		R.set(0);
		IC.set(IC.get()+1);
	}
	/**
	 * Sudëtis
	 * @param xx
	 */
	public void AD(int xx) {
		int xm = R.get() + getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Atimtis
	 * @param xx
	 */
	public void SU(int xx) {
		int xm = R.get() - getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Daugyba
	 * @param xx
	 */
	public void MU(int xx) {
		int xm = R.get() * getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Dalyba
	 * @param xx
	 */
	public void DI(int xx) {
		if(getWord(xx) != 0) {
			int xm = R.get() / getWord(xx);
			R.set(xm);
			IC.set(IC.get()+1); 
		} else {
			this.PI.set(4);
		}
	}
	/**
	 * Liekana
	 * @param xx
	 */
	public void MO(int xx) {
		int xm = R.get() % getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Palyginimas
	 * @param xx
	 */
	public void CR(int xx) {
		if (R.get() > getWord(xx)) {
			C.set(1);
		} else if(R.get() < getWord(xx)) {
			C.set(2);
		} else {
			C.set(0);
		}
		IC.set(IC.get()+1);
	}
	/**
	 * Valdymo perdavimas
	 * @param xx
	 */
	public void JP(int xx) {
		IC.set(xx);
	}
	/**
	 * Valdymo perdavimas jei lygu
	 * @param xx
	 */
	public void JE(int xx) {
		if (C.get() == 0) {
			IC.set(xx);
		} else {
			IC.set(IC.get()+1);
		}
	}
	/**
	 * Valdymo perdavimas jei daugiau
	 * @param xx
	 */
	public void JG(int xx) {
		if (C.get() == 1) {
			IC.set(xx);
		} else {
			IC.set(IC.get()+1);
		}
	}
	/**
	 * Valdymo perdavimas jei maþiau
	 * @param xx
	 */
	public void JL(int xx) {
		if (C.get() == 2) {
			IC.set(xx);
		} else {
			IC.set(IC.get()+1);
		}
	}
	/**
	 * INPUT
	 * @param xx
	 */
	static public void GD(int xx) {
		String input = UI.MainWindow.getConsole();
		input = UI.MainWindow.getConsole();
		while(!input.equals("")) {
			input = UI.MainWindow.getConsole();
			memory.set(xx, input);
			input = "";
			UI.MainWindow.setConsole();
		}
		IC.set(IC.get()+1);
		SI.set(0);
		CH.set(0);
		PI.set(0);
		MODE.set(0);
	}
	/**
	 * OUTPUT
	 * @param xx
	 */
	static public void PD(int xx) {
		String text="";
		String lineEnd = "####";
		while(!lineEnd.equals(memory.getWord(xx))) {
			text = text + memory.getWord(xx);
			xx++;
		}
		IC.set(IC.get()+1);
		SI.set(0);
		CH.set(0);
		MODE.set(0);
		UI.MainWindow.updateConsole(text);
	}
	/**
	 * Perjungiama į supervizoriaus režimą
	 * @param xx Adresas atmintyje
	 */
	public void XM(int xx) {
		IC.set(IC.get()+1);
		MODE.set(1);
	}
	/**
	 * Perjungiama į vartotojo režimą.
	 * @param xx Adresas  atmintyje
	 */
	public void UM(int xx) {
		IC.set(IC.get()+1);
		MODE.set(0);
	}
	/**
	 * Programos vykdymo pabaiga
	 */
	public void HALT() {
		this.MODE.set(1);
		this.SI.set(3);
	}

	public static void Interrupt() {
		switch (PI.get()) {
		case 1: {
			System.out.println("Bandoma naudoti neteisingą operacijos kodą");
			break;
		}
		case 2: {
			System.out.println("Bandoma naudoti neteising1 adresą");
			break;
		}
		case 3: {
			System.out.println("Įvyko atminties perpildymas.");
			break;
		}
		case 4: {
			System.out.println("Bandoma dalyba iš nulio.");
			break;
		}
		case 5: {
			System.out.println("Kanalas užimtas");
			break;
		}
		default: {
			System.out.println("Pertraukimas neįvyko.");
			break;
		}
		}
		switch (SI.get()) {
		case 1: {
			System.out.println("Pertraukimą iššaukė komanda GD.");
			break;
		}
		case 2: {
			System.out.println("Pertraukimą iššaukė komanda PD.");
			break;
		}
		case 3: {
			System.out.println("Pertraukimą iššaukė komanda HALT.");
			break;
		}
		default: {
			System.out.println("Pertraukimas neįvyko.");
			break;
		}
		}
		switch (T.get()) {
		case 1: {
			System.out.println("Taimerio pertraukimas.");
			break;
		}
		default: {
			System.out.println("Pertraukimas neįvyko.");
		}
		}
	}
}
