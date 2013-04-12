package VM;

import registers.CRegister;
import registers.DataRegister;
import registers.IcRegister;

/**
 * Virtuali maðina
 * @author Aurimas
 *
 */
public class VM {
	/**
	 * Bendrojo naudojimo registras
	 */
	public static DataRegister R;
	/**
	 * Komandø skaitiklis
	 */
	public static IcRegister IC;
	/**
	 * Poþymiø registras
	 */
	public static CRegister C;
	/**
	 * Vartotojui iðskiriama atmintis
	 */
	static public PageTable PageTable;
	public static VA Atmintis;
	public String end = "HALT";
	
	public VM() {
		R = new DataRegister();
		IC = new IcRegister();
		C = new CRegister();
		PageTable = new PageTable();
		Atmintis = new VA(100);
	}

	public void startProgram() {
		do {
			step();
		} while(RM.RM.SI.get() != 3);
		step();
	}
	/**
	 * Vykdomos komandos po þingsná
	 */
	public void startProgramStepByStep() {
			step();
	}
	/**
	 * Komandos vykdymo þingsnis
	 */
	private void step() {
		//updateGUI();
		boolean modeRM = false;
		if(RM.RM.SI.get() != 3) {
			if(!test()) { updateGUI(); }
			if(RM.RM.MODE.get() == 1 || modeRM) {
				String command = RM.RM.memory.getWord(RM.RM.IC.get());
				RM.RM.doCommand(command);
			} else {
				String command = Atmintis.get(IC.get());
				doCommand(command);
			}
			//if(!test()) { updateGUI(); }
			//updateGUI();
				RM.RM.TI.set();
		} else {
			updateGUI();
			UI.MainWindow.updateConsole(">> Programa baigė darbą!");
			RM.RM.slave(C.get(), R.get(), IC.get());
			RM.RM.MODE.set(1);
		}
	}

	private boolean test() {
		if (RM.RM.SI.get() + RM.RM.PI.get() + RM.RM.T.get() != 0) {
			RM.RM.slave(C.get(), R.get(), IC.get());
			RM.RM.MODE.set(1);
			RM.RM.Interrupt();
			updateGUI();
			if (RM.RM.SI.get() != 3) {
				updateReg();
			}
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Atnaujinami UI pagrindiniai registrø laukai
	 * bei vartotojo atmintis
	 */
	public static void updateGUI() {
		UI.MainWindow.updateIC(RM.RM.IC.get());
		UI.MainWindow.updateR(RM.RM.R.get());
		UI.MainWindow.updateC(RM.RM.C.get());
		UI.MainWindow.updateICv(IC.get());
		UI.MainWindow.updateRv(R.get());
		UI.MainWindow.updateCv(C.get());
		UI.MainWindow.updatePTR(RM.RM.PTR.get());
		UI.MainWindow.updateT(RM.RM.T.get());
		UI.MainWindow.updateSI(RM.RM.SI.get());
		UI.MainWindow.updatePI(RM.RM.PI.get());
		UI.MainWindow.updateTI(RM.RM.TI.get());
		UI.MainWindow.updateMODE(RM.RM.MODE.get());
		UI.MainWindow.updateCH(RM.RM.CH.get());
		UI.MainWindow.updateList(Atmintis);
		UI.MainWindow.updateListRM(RM.RM.memory);
		UI.MainWindow.updateListEM(RM.RM.externalMemory);
	}
	/**
	 * Ið atminties þodþio iðskiriamas OPK
	 * @param zodis Atminties þodis
	 * @return OPK Operacijos kodas
	 */
	private String encodeBytes2(String zodis) {
		String[] value = zodis.split("(?<=\\G.{1})");
		String OPK = value[0]+value[1];
		return OPK;
	}
	/**
	 * Ið atminties þodþio iðskiriamas adresas
	 * @param zodis Atminties þodis
	 * @return XX Atminties adresas
	 */
	private int XXencode(String zodis) {
		String[] value = zodis.split("(?<=\\G.{1})");
		try {
			int key1 = Integer.parseInt(value[2]);
			int key2 = Integer.parseInt(value[3]);
			int key3 = Integer.parseInt(value[4]);
			int key = key1 * 100 + key2 * 10 + key3;
			if (key > 99) {
				RM.RM.PI.set(2);
				test();
				return 0;
			} else {
				return key;
			}
		} catch (NumberFormatException e) {
			RM.RM.PI.set(2);
			return 0;
		}
	}
	/**
	 * Ið atminties þodþio iðskiriamas tekstinis adresas
	 * @param zodis Atminties þodis
	 * @return XX Atminties adreso tekstas
	 */
	private String YYencode(String zodis) {
		String[] value = zodis.split("(?<=\\G.{2})");
		return value[1];
	}
	/**
	 * Komandos atpaþinimas
	 * @param command Atminties þodis
	 */
	private void doCommand(String command) {
		String OPK="";
		int xx;
		
		OPK = encodeBytes2(command);
		xx = XXencode(command);
		
		switch (OPK) 
		{
            case "PD": 
            {
                PD(xx);
                break;
            }
            case "GD": 
            {
                GD(xx);
                break;
            }
            case "LR": 
            {
                LR(xx);
                break;
            }
            case "SR":
            {
            	SR(xx);
            	break;
            }
            case "AD":
            {
            	AD(xx);
            	break;
            }
            case "SU":
            {
            	SU(xx);
            	break;
            }
            case "MU":
            {
            	MU(xx);
            	break;
            }
            case "DI":
            {
            	DI(xx);
            	break;
            }
            case "MO":
            {
            	MO(xx);
            	break;
            }
            case "CR":
            {
            	CR(xx);
            	break;
            }
            case "JP":
            {
            	JP(xx);
            	break;
            }
            case "JL":
            {
            	JL(xx);
            	break;
            }
            case "JE":
            {
            	JE(xx);
            	break;
            }
            case "JG":
            {
            	JG(xx);
            	break;
            }
            default: 
            {
            	OPK += YYencode(command);
                switch(OPK)
                {
                    case "HALT":
                    {
                        HALT();
                        break;
                    }
                    default:
                    {
                    	//UI.MainWindow.updateConsole("Komanda '"+OPK+"' neegzistuoja!");
                        IC.set(IC.get()+1);
                        RM.RM.PI.set(1); // Neteisingas OPK
                        test();
                        break;
                    }
                }
            }
		}
	}

	/**
	 * Ið atminties adresu XX paimama reikðmë, bei paðalinami tarpai
	 * @param xx Atminties adresas
	 * @return XY Reikðmë adresu XX
	 */
	public int getWord(int xx) {
		String Word = Atmintis.get(xx);
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
		Atmintis.set(xx, Integer.toString(R));
	}
	/**
	 * Load Register
	 * @param xx
	 */
	public void updateReg() {
		RM.RM.MODE.set(0);
		RM.RM.SI.set(0);
		RM.RM.CH.set(0);
		RM.RM.PI.set(0);
		RM.RM.T.set(0);
	}
	public void LR(int xx) {
		updateReg();
		R.set(getWord(xx));
		IC.set(IC.get()+1);
	}
	/**
	 * Save Register
	 * @param xx
	 */
	public void SR(int xx) {
		updateReg();
		setWord(xx, R.get());
		IC.set(IC.get()+1);
	}
	/**
	 * Null
	 */
	public void NL() {
		updateReg();
		R.set(0);
		IC.set(IC.get()+1);
	}
	/**
	 * Sudëtis
	 * @param xx
	 */
	public void AD(int xx) {
		updateReg();
		int xm = R.get() + getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Atimtis
	 * @param xx
	 */
	public void SU(int xx) {
		updateReg();
		int xm = R.get() - getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Daugyba
	 * @param xx
	 */
	public void MU(int xx) {
		updateReg();
		int xm = R.get() * getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Dalyba
	 * @param xx
	 */
	public void DI(int xx) {
		updateReg();
		if(getWord(xx) != 0) {
			int xm = R.get() / getWord(xx);
			R.set(xm);
			IC.set(IC.get()+1); 
		} else {
			RM.RM.PI.set(4);
			IC.set(IC.get()+1); 
		}
	}
	/**
	 * Liekana
	 * @param xx
	 */
	public void MO(int xx) {
		updateReg();
		int xm = R.get() % getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Palyginimas
	 * @param xx
	 */
	public void CR(int xx) {
		updateReg();
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
		updateReg();
		IC.set(xx);
	}
	/**
	 * Valdymo perdavimas jei lygu
	 * @param xx
	 */
	public void JE(int xx) {
		updateReg();
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
		updateReg();
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
		updateReg();
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
	public void GD(int xx) {
		updateReg();
		RM.RM.SI.set(1);
		RM.RM.CH.set(1);
		
		test();
		RM.RM.GD(Atmintis.getAA(xx));
		IC.set(IC.get()+1);
	}
	/**
	 * OUTPUT
	 * @param xx
	 */
	public void PD(int xx) {
		updateReg();
		RM.RM.SI.set(2);
		RM.RM.CH.set(2);
		
		test();
		RM.RM.PD(Atmintis.getAA(xx));
		
		IC.set(IC.get()+1);
	}

	/**
	 * Programos vykdymo pabaiga
	 */
	public void HALT() {
		updateReg();
		RM.RM.MODE.set(1);
		RM.RM.SI.set(3);
	}
}