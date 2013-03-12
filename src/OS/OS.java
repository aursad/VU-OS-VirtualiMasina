package OS;

import registers.CRegister;
import registers.ChRegister;
import registers.DataRegister;
import registers.IcRegister;
import registers.IntRegister;
import registers.ModeRegister;
import registers.TimerRegister;

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
	public TimerRegister T;
	/**
	 * Supervizorini� pertraukim� registras
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
	 * Registras nusakantis procesoriaus darbo re�im�
	 */
	public ModeRegister MODE;
	/**
	 * Kanal� u�imtumo registras 
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
