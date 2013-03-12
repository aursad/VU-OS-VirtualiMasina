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
	private DataRegister R;
	/**
	 * Komand� skaitiklis
	 */
	private IcRegister IC;
	/**
	 * Po�ymi� registras
	 */
	private CRegister C;
	/**
	 * Taimerio veiksm� registras
	 */
	private int T=10;
	/**
	 * Supervizorini� pertraukim� registras
	 */
	private IntRegister SI;
	/**
	 * Programinio pertraukimo registras
	 */
	private IntRegister PI;
	/**
	 * Taimerio pertraukimo registras
	 */
	private TimerRegister TI;
	/**
	 * Registras nusakantis procesoriaus darbo re�im�
	 */
	private ModeRegister MODE;
	/**
	 * Kanal� u�imtumo registras 
	 */
	private ChRegister CH;
	
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
	 * Load Register
	 * Registras R �gauna a reik�m�
	 * @param a 
	 * @return R Nauja bendrojo naudojimo registro reik�m�
	 */
	
}
