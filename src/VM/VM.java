package VM;

import IOI.Input;
import UI.MainWindow;
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
	 * Vartotojui iðskiriama atmintis
	 */
	public VA Atmintis;
	public String end = "HALT";
	
	public VM() {
		R = new DataRegister();
		IC = new IcRegister();
		C = new CRegister();
		Atmintis = new VA(100);
	}
	/**
	 * Vykdomos visos komandos ið eilës
	 */
	public void startProgram() {
		do {
			step();
		} while(RM.RM.SI.get() != 3 && RM.RM.PI.get() == 0);
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
		updateGUI();
		if(RM.RM.SI.get() != 3 && RM.RM.PI.get() == 0) {
			String command = Atmintis.get(IC.get());
				doCommand(command);
				updateGUI();
				RM.RM.TI.set();
		} else {
			UI.MainWindow.updateConsole(">> Programa baigë darbà!");
		}
	}
	/**
	 * Atnaujinami UI pagrindiniai registrø laukai
	 * bei vartotojo atmintis
	 */
	private void updateGUI() {
		UI.MainWindow.updateIC(IC.get());
		UI.MainWindow.updateR(R.get());
		UI.MainWindow.updateC(C.get());
		
		UI.MainWindow.updatePTR(0);
		UI.MainWindow.updateT(RM.RM.T.get());
		UI.MainWindow.updateSI(RM.RM.SI.get());
		UI.MainWindow.updatePI(RM.RM.PI.get());
		UI.MainWindow.updateTI(RM.RM.TI.get());
		UI.MainWindow.updateMODE(RM.RM.MODE.get());
		UI.MainWindow.updateCH(RM.RM.CH.get());
		UI.MainWindow.updateList(Atmintis);
	}
	/**
	 * Ið atminties þodþio iðskiriamas OPK
	 * @param zodis Atminties þodis
	 * @return OPK Operacijos kodas
	 */
	private String encodeBytes2(String zodis) {
		String[] value = zodis.split("(?<=\\G.{2})");
		String OPK = value[0];
		return OPK;
	}
	/**
	 * Ið atminties þodþio iðskiriamas adresas
	 * @param zodis Atminties þodis
	 * @return XX Atminties adresas
	 */
	private int XXencode(String zodis) {
		String[] value = zodis.split("(?<=\\G.{2})");
		try 
        {
            return Integer.parseInt(value[1]);
        } 
        catch (NumberFormatException e)
        {
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
                    	UI.MainWindow.updateConsole("Komanda '"+OPK+"' neegzistuoja!");
                        IC.set(IC.get()+1);
                        RM.RM.PI.set(1); // Neteisingas OPK
                        RM.RM.MODE.set(1);
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
			RM.RM.PI.set(4);
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
	public void GD(int xx) {
		RM.RM.MODE.set(1);
		RM.RM.SI.set(2);
		RM.RM.CH.set(4);
		RM.RM.PI.set(5);
		
		String input = UI.MainWindow.getConsole();
		//Atmintis.set(xx, getText);
		input = UI.MainWindow.getConsole();
		while(!input.equals("")) {
			input = UI.MainWindow.getConsole();
			Atmintis.set(xx, input);
			input = "";
			UI.MainWindow.setConsole();
		}
		IC.set(IC.get()+1);
		
		RM.RM.SI.set(0);
		RM.RM.CH.set(0);
		RM.RM.PI.set(0);
		RM.RM.MODE.set(0);
	}
	/**
	 * OUTPUT
	 * @param xx
	 */
	public void PD(int xx) {
		RM.RM.MODE.set(1);
		RM.RM.SI.set(2);
		RM.RM.CH.set(5);
		
		String text="";
		String lineEnd = "####";
		while(!lineEnd.equals(Atmintis.get(xx))) {
			text = text + Atmintis.get(xx);
			xx++;
		}
		IC.set(IC.get()+1);
/*		OS.OS.SI.set(0);
		OS.OS.CH.set(0);
		OS.OS.MODE.set(0);*/
		UI.MainWindow.updateConsole(text);
	}
	/**
	 * Programos vykdymo pabaiga
	 */
	public void HALT() {
		RM.RM.MODE.set(1);
		RM.RM.SI.set(3);
	}
}
