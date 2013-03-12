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
	private DataRegister R;
	/**
	 * Komandø skaitiklis
	 */
	private IcRegister IC;
	/**
	 * Poþymiø registras
	 */
	private CRegister C;
	/**
	 * Taimerio veiksmø registras
	 */
	private int T=10;
	/**
	 * Supervizoriniø pertraukimø registras
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
	 * Registras nusakantis procesoriaus darbo reþimà
	 */
	private ModeRegister MODE;
	/**
	 * Kanalø uþimtumo registras 
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
	 * Load Register
	 * Registras R ágauna a reikðmæ
	 * @param a 
	 * @return R Nauja bendrojo naudojimo registro reikðmë
	 */
	
}
