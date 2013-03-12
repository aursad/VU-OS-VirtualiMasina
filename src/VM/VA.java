package VM;

import java.util.LinkedList;

/**
 * Vartotojo atmintis
 * @author Aurimas
 *
 */
public class VA {
	/**
	 * Vartotojui iðskiriamos atminties kiekis
	 */
	private int vartotojoAtmintis=0;
	LinkedList <String> atmintis;
	
	/**
	 * Konstruktoriai
	 */
	public VA() {
		this(100);
	}
	VA(int vartotojoAtmintis) {
		this.atmintis = new LinkedList<String>();
		this.vartotojoAtmintis = vartotojoAtmintis;
	}
	/**
	 * Pridedam elementa á Vartotojo atmintá
	 * @param Record Norimas áraðas
	 */
	public void add(String Record) {
		this.atmintis.add(Record);
	}
	/**
	 * Norimu adresu gauname elementà ið vartotoji atminties
	 * @param a Atminties adresas
	 * @return
	 */
	public String get(int a) {
		return this.atmintis.get(a);
	}
}
