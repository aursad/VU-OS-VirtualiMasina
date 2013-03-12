package VM;

import java.util.LinkedList;

/**
 * Vartotojo atmintis
 * @author Aurimas
 *
 */
public class VA {
	/**
	 * Vartotojui i�skiriamos atminties kiekis
	 */
	private int vartotojoAtmintis=0;
	LinkedList <String> atmintis;
	
	/**
	 * Konstruktoriai
	 */
	public VA(int vartotojoAtmintis) {
		this.atmintis = new LinkedList<String>();
		this.vartotojoAtmintis = vartotojoAtmintis;
		for(int i=0;i<vartotojoAtmintis;i++) {
			atmintis.add("XXYY");
		}
	}
	/**
	 * Pridedam elementa � Vartotojo atmint�
	 * @param Record Norimas �ra�as
	 */
	public void add(String Record) {
		this.atmintis.add(Record);
	}
	/**
	 * Norimu adresu gauname element� i� vartotoji atminties
	 * @param a Atminties adresas
	 * @return
	 */
	public String get(int a) {
		return this.atmintis.get(a);
	}
	public LinkedList<String> getList() {
		return atmintis;
	}
}
