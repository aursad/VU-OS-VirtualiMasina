package OS;

import registers.CRegister;
import registers.ChRegister;
import registers.DataRegister;
import registers.IcRegister;
import registers.IntRegister;
import registers.ModeRegister;
import registers.TimerRegister;

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
	public DataRegister R;
	/**
	 * Komandø skaitiklis
	 */
	public IcRegister IC;
	/**
	 * Poþymiø registras
	 */
	public CRegister C;
	/**
	 * Taimerio veiksmø registras
	 */
	public TimerRegister T;
	/**
	 * Supervizoriniø pertraukimø registras
	 */
	public IntRegister SI;
	/**
	 * Programinio pertraukimo registras
	 */
	public IntRegister PI;
	/**
	 * Taimerio pertraukimo registras
	 */
	public TimerRegister TI;
	/**
	 * Registras nusakantis procesoriaus darbo reþimà
	 */
	public ModeRegister MODE;
	/**
	 * Kanalø uþimtumo registras 
	 */
	public ChRegister CH;
	
	/**
	 * Konstruktorius
	 */
	public OS() {
		this(0000);
	}
	OS(int PTR) {
		this.PTR = PTR;
		R = new DataRegister();
        IC = new IcRegister();
        C = new CRegister();
        T = new TimerRegister(10); 
        MODE  = new ModeRegister();
        PI = new IntRegister();
        SI = new IntRegister();
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
	
}
