package VM;

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
	public VA Atmintis;
	public String end = "HALT";
	
	public VM() {
		R = new DataRegister();
		IC = new IcRegister();
		C = new CRegister();
		Atmintis = new VA(100);
	}

	public void startProgram() {
		while(!end.equals(Atmintis.get(IC.get()))) {
			String s = Atmintis.get(IC.get());
			String[] value = s.split("(?<=\\G.{2})"); // value[0] => opk, value[1] => adresas
			String OPK = value[0];
			int xx = Integer.parseInt(value[1]);
			switch (OPK) 
			{
	            case "PD": 
	            {
	                PD(xx);
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
	            default: 
	            {
	                System.out.println ("unknown\t" + OPK);
	                break;
	            }
			}
			IC.set(IC.get()+1);
			UI.MainWindow.updateIC(IC.get());
			UI.MainWindow.updateList(Atmintis);
		} 
	}
	
	// Is atminties xx adresu paimti reiksme
	public int getWord(int xx) {
		String Word = Atmintis.get(xx);
		Word = Word.replaceAll("\\s", "");
		int XY = Integer.parseInt(Word);
		return XY;
	}
	// Atminciai xx adresu nustatyti reiksme
	public void setWord(int xx, int R) {
		Atmintis.set(xx, Integer.toString(R));
	}

	/**
	 * Komandos
	 */
	public void LR(int xx) {
		R.set(getWord(xx));
		UI.MainWindow.updateR(R.get());
	}
	public void SR(int xx) {
		setWord(xx, R.get());
	}
	public void NL() {
		R.set(0);
	}
	public void AD(int xx) {
		int xm = R.get() + getWord(xx);
		R.set(xm);
	}
	public void SU(int xx) {
		int xm = R.get() - getWord(xx);
		R.set(xm);
	}
	public void MU(int xx) {
		int xm = R.get() * getWord(xx);
		R.set(xm);
	}
	public void DI(int xx) {
		int xm = R.get() / getWord(xx);
		R.set(xm);
	}
	public void MO(int xx) {
		int xm = R.get() % getWord(xx);
		R.set(xm);
	}
	public void CR(int xx) {
		if (R.get() > getWord(xx)) {
			C.set(1);
		} else if(R.get() < getWord(xx)) {
			C.set(2);
		} else {
			C.set(0);
		}
	}
	public void JP(int xx) {
		IC.set(getWord(xx));
	}
	public void JE(int xx) {
		if (C.get() == 0) {
			IC.set(getWord(xx));
		}
	}
	public void JG(int xx) {
		if (C.get() == 1) {
			IC.set(getWord(xx));
		}
	}
	public void JL(int xx) {
		if (C.get() == 2) {
			IC.set(getWord(xx));
		}
	}
	public void GD(int xx) {
		
	}
	public void PD(int xx) {
		String text="";
		String lineEnd = "####";
		while(!lineEnd.equals(Atmintis.get(xx))) {
			text = text + Atmintis.get(xx);
			xx++;
		}
		UI.MainWindow.updateConsole(text);
	}
}
