package RM;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	public static ExternalMemory externalMemory;
	static ExecutorService executorService = Executors.newCachedThreadPool();
	public static int inputKiekis;
	/**
	 * Konstruktorius
	 */
	public RM() {
        memory = new RealMemory(100);
        externalMemory = new ExternalMemory(100);
        PTR = new PTRRegister(0, 9, 1, 0);
		R = new DataRegister();
        IC = new IcRegister();
        C = new CRegister();
        T = new TimerRegister(0); 
        TI = new TimerMechRegister(10);
        MODE  = new ModeRegister();
        PI = new IntRegister();
        SI = new IntRegister();
        CH = new ChRegister();
        
        InterruptPrograms();
        
        externalMemory.save();
	}
	
	/**
	 * Ið atminties þodþio iðskiriamas OPK
	 * @param zodis Atminties þodis
	 * @return OPK Operacijos kodas
	 */
	private static String encodeBytes2(String zodis) {
		String[] value = zodis.split("(?<=\\G.{1})");
		String OPK = value[0]+value[1];
		return OPK;
	}
	/**
	 * Ið atminties þodþio iðskiriamas adresas
	 * @param zodis Atminties þodis
	 * @return XX Atminties adresas
	 */
	private static int XXencode(String zodis) {
		String[] value = zodis.split("(?<=\\G.{1})");
		try 
        {
			int key1 = Integer.parseInt(value[2]);
			int key2 = Integer.parseInt(value[3]);
			int key3 = Integer.parseInt(value[4]);
            return key1*100+key2*10+key3;
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
            MODE.set(0);
            VM.VM.updateGUI();
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
		HashMap<Integer, Integer> inputlist = new HashMap<Integer, Integer>();
		inputKiekis++;
		if (inputlist.isEmpty()) {
			inputlist.put(0, 0);
		}
		inputlist.put(inputlist.size(), xx);

		producer(inputlist);
		consumer(inputlist);
		IC.set(IC.get() + 1);
		MODE.set(0);
	}

	static void producer(final HashMap<Integer, Integer> inputlist) {
		executorService.execute(new Runnable() {
			public void run() {
				for (int i = 1; i < inputlist.size(); i++) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	static void consumer(final HashMap<Integer, Integer> inputlist) {
		executorService.execute(new Runnable() {
			public void run() {
				while (inputlist.get(0) != inputlist.size() - 1) {
					try {
						String s = UI.MainWindow.getConsole();
						if (!s.equals("")) {
							int key = inputlist.get(0) + 1;
							memory.set(inputlist.get(key), s);
							inputlist.put(0, inputlist.get(0) + 1);
							UI.MainWindow.setConsole();
							inputKiekis--;
						}
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
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
	static public void EG(int xx) {
		
	}
	static public void EP(int xx) {
		String text="";
		String lineEnd = "####";
		while(!lineEnd.equals(externalMemory.getWord(xx))) {
			text = text + externalMemory.getWord(xx);
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
				IntProgMove(120);
				break;
			}
			case 2: {
				UI.MainWindow.updateConsole("Bandoma naudoti neteisingą adresą");
				IntProgMove(130);
				break;
			}
			case 3: {
				UI.MainWindow.updateConsole("Įvyko atminties perpildymas.");
				IntProgMove(140);
				break;
			}
			case 4: {
				UI.MainWindow.updateConsole("Bandoma dalyba iš nulio.");
				IntProgMove(150);
				break;
			}
			default: {
				System.out.println("Pertraukimas PI neįvyko.");
				break;
			}
			}
			IC.set(IC.get()+1);
			//MODE.set(0);
			//PI.set(0);
		}
		if (SI.get() != 0) {
			switch (SI.get()) {
			case 1: {
				UI.MainWindow.updateConsole("Pertraukimą iššaukė komanda GD.");
				IntProgMove(160);
				break;
			}
			case 2: {
				UI.MainWindow.updateConsole("Pertraukimą iššaukė komanda PD.");
				IntProgMove(170);
				break;
			}
			case 3: {
				UI.MainWindow.updateConsole("Pertraukimą iššaukė komanda HALT.");
				IntProgMove(180);
				break;
			}
			case 4: {
				UI.MainWindow.updateConsole("Pertraukimą iššaukė komanda EG.");
				IntProgMove(190);
				break;
			}
			case 5: {
				UI.MainWindow.updateConsole("Pertraukimą iššaukė komanda EP.");
				IntProgMove(200);
				break;
			}
			default: {
				UI.MainWindow.updateConsole("Pertraukimas SI neįvyko.");
				break;
			}
			}
			IC.set(IC.get()+1);
			//MODE.set(0);
		}
		if (T.get() != 0) {
			switch (T.get()) {
			case 1: {
				UI.MainWindow.updateConsole("Taimerio pertraukimas.");
				IntProgMove(210);
				break;
			}
			default: {
				UI.MainWindow.updateConsole("Pertraukimas T neįvyko.");
			}
			}
			IC.set(IC.get()+1);
			//MODE.set(0);
			//T.set(0);
		}
	}
	@SuppressWarnings("static-access")
	public static void saveMemory() {
		externalMemory.save(memory);
	}
	/**
	 * Išsaugomos VM registrai supervizorinėje atmintyje 11 bloke
	 * @param C loginis trigeris
	 * @param R Bendro naudojimo registras
	 * @param IC Komandų skaitliukas
	 */
	public static void slave(int C, int R, int IC)
	{
		memory.set(11, 0, PTR.get());
		memory.set(11, 1, ""+C);
		memory.set(11, 2, ""+R);
		memory.set(11, 3, ""+IC);
	}
	private static void IntProgMove(int xx) {
		String command = memory.getWord(xx);
		doCommand(command);
	}
	private void InterruptPrograms() {
		// Neteisinga OPK kodas
		memory.set(12, 0, "JP000");
		// Neteisingas adresas
		memory.set(13, 0, "JP000");
		// Ivyko overflow
		memory.set(14, 0, "JP000");
		// Dalyba is nulio
		memory.set(15, 0, "JP000");
		// GD
		memory.set(16, 0, "JP000");
		// PD
		memory.set(17, 0, "JP000");
		// HALT
		memory.set(18, 0, "JP000");
		// EG
		memory.set(19, 0, "JP000");
		// EP
		memory.set(20, 0, "JP000");
		// Taimerio pertraukimas
		memory.set(21, 0, "JP000");
		
	}
}
