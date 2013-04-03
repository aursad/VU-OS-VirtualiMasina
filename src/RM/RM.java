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
	public static DataRegister R;
	/**
	 * Komandų skaitiklis
	 */
	public static IcRegister IC;
	/**
	 * Požymių registras
	 */
	public static CRegister C;
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
	 * Ið atminties þodþio iðskiriamas OPK
	 * @param zodis Atminties þodis
	 * @return OPK Operacijos kodas
	 */
	private static String encodeBytes2(String zodis) {
		String[] value = zodis.split("(?<=\\G.{2})");
		String OPK = value[0];
		return OPK;
	}
	/**
	 * Ið atminties þodþio iðskiriamas adresas
	 * @param zodis Atminties þodis
	 * @return XX Atminties adresas
	 */
	private static int XXencode(String zodis) {
		String[] value = zodis.split("(?<=\\G.{2})");
		try 
        {
            return Integer.parseInt(value[1]);
        } 
        catch (NumberFormatException e)
        {
        	//PI.set(2);
            return 0;
        }
	}
	/**
	 * Ið atminties þodþio iðskiriamas tekstinis adresas
	 * @param zodis Atminties þodis
	 * @return XX Atminties adreso tekstas
	 */
	private static String YYencode(String zodis) {
		String[] value = zodis.split("(?<=\\G.{2})");
		return value[1];
	}
	/**
	 * Komandos atpaþinimas
	 * @param command Atminties þodis
	 */
	public static void doCommand(String command) {
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
            case "XM":
            {
            	XM(xx);
            	break;
            }
            case "UM":
            {
            	UM(xx);
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
                        PI.set(1); // Neteisingas OPK
                        MODE.set(1);
                        break;
                    }
                }
            }
		}
	}
	/**
	 * Iš atminties adresu XX paimama reikšmę, bei pašalinami tarpai
	 * @param xx Atminties adresas
	 * @return XY Reikšmė adresu XX
	 */
	public static int getWord(int xx) {
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
	public static void setWord(int xx, int R) {
		memory.set(xx, Integer.toString(R));
	}
	/**
	 * Load Register
	 * @param xx
	 */
	public static void LR(int xx) {
		R.set(getWord(xx));
		IC.set(IC.get()+1);
	}
	/**
	 * Save Register
	 * @param xx
	 */
	public static void SR(int xx) {
		setWord(xx, R.get());
		IC.set(IC.get()+1);
	}
	/**
	 * Null
	 */
	public static void NL() {
		R.set(0);
		IC.set(IC.get()+1);
	}
	/**
	 * Sudëtis
	 * @param xx
	 */
	public static void AD(int xx) {
		int xm = R.get() + getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Atimtis
	 * @param xx
	 */
	public static void SU(int xx) {
		int xm = R.get() - getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Daugyba
	 * @param xx
	 */
	public static void MU(int xx) {
		int xm = R.get() * getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Dalyba
	 * @param xx
	 */
	public static void DI(int xx) {
		if(getWord(xx) != 0) {
			int xm = R.get() / getWord(xx);
			R.set(xm);
			IC.set(IC.get()+1); 
		} else {
			PI.set(4);
		}
	}
	/**
	 * Liekana
	 * @param xx
	 */
	public static void MO(int xx) {
		int xm = R.get() % getWord(xx);
		R.set(xm);
		IC.set(IC.get()+1);
	}
	/**
	 * Palyginimas
	 * @param xx
	 */
	public static void CR(int xx) {
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
	public static void JP(int xx) {
		IC.set(xx);
	}
	/**
	 * Valdymo perdavimas jei lygu
	 * @param xx
	 */
	public static void JE(int xx) {
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
	public static void JG(int xx) {
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
	public static void JL(int xx) {
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
		MODE.set(0);
		UI.MainWindow.updateConsole(text);
	}
	/**
	 * Perjungiama į supervizoriaus režimą
	 * @param xx Adresas atmintyje
	 */
	public static void XM(int xx) {
		IC.set(IC.get()+1);
		MODE.set(1);
	}
	/**
	 * Perjungiama į vartotojo režimą.
	 * @param xx Adresas  atmintyje
	 */
	public static void UM(int xx) {
		IC.set(IC.get()+1);
		MODE.set(0);
	}
	/**
	 * Programos vykdymo pabaiga
	 */
	public static void HALT() {
		MODE.set(1);
		SI.set(3);
	}

	public static void Interrupt() {
		if (PI.get() != 0) {
			switch (PI.get()) {
			case 1: {
				UI.MainWindow.updateConsole("Bandoma naudoti neteisingą operacijos kodą");
				break;
			}
			case 2: {
				UI.MainWindow.updateConsole("Bandoma naudoti neteisingą adresą");
				break;
			}
			case 3: {
				UI.MainWindow.updateConsole("Įvyko atminties perpildymas.");
				break;
			}
			case 4: {
				UI.MainWindow.updateConsole("Bandoma dalyba iš nulio.");
				break;
			}
			case 5: {
				UI.MainWindow.updateConsole("Kanalas užimtas");
				break;
			}
			default: {
				System.out.println("Pertraukimas PI neįvyko.");
				break;
			}
			}
			IC.set(IC.get()+1);
			MODE.set(0);
			PI.set(0);
		}
		if (SI.get() != 0) {
			switch (SI.get()) {
			case 1: {
				UI.MainWindow.updateConsole("Pertraukimą iššaukė komanda GD.");
				SI.set(0);
				break;
			}
			case 2: {
				UI.MainWindow.updateConsole("Pertraukimą iššaukė komanda PD.");
				SI.set(0);
				break;
			}
			case 3: {
				UI.MainWindow.updateConsole("Pertraukimą iššaukė komanda HALT.");
				break;
			}
			default: {
				UI.MainWindow.updateConsole("Pertraukimas SI neįvyko.");
				break;
			}
			}
			IC.set(IC.get()+1);
			MODE.set(0);
		}
		if (T.get() != 0) {
			switch (T.get()) {
			case 1: {
				UI.MainWindow.updateConsole("Taimerio pertraukimas.");
				break;
			}
			default: {
				UI.MainWindow.updateConsole("Pertraukimas T neįvyko.");
			}
			}
			IC.set(IC.get()+1);
			MODE.set(0);
			T.set(0);
		}
	}
}
