package RM;

import registers.CRegister;
import registers.ChRegister;
import registers.DataRegister;
import registers.IcRegister;
import registers.IntRegister;
import registers.ModeRegister;
import registers.TimerMechRegister;
import registers.TimerRegister;

/**
 * Operacin� sistema
 * @author Aurimas Sadauskas
 * @version 1.0
 * @since 2013.03.02
 */
public class RM {
	/**
	 * Puslapiavimo registras
	 */
	static public int PTR;
	/**
	 * Bendrojo naudojimo registras
	 */
	public DataRegister R;
	/**
	 * Komand� skaitiklis
	 */
	public IcRegister IC;
	/**
	 * Po�ymi� registras
	 */
	public CRegister C;
	/**
	 * Taimerio veiksm� registras
	 */
	static public TimerRegister T;
	/**
	 * Supervizorini� pertraukim� registras
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
	 * Registras nusakantis procesoriaus darbo re�im�
	 */
	static public ModeRegister MODE;
	/**
	 * Kanal� u�imtumo registras 
	 */
	static public ChRegister CH;
	
	/**
	 * Konstruktorius
	 */
	public RM() {
		this(0000);
	}
	RM(int PTR) {
		this.PTR = PTR;
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
	
}
