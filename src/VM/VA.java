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
	private int vartotojoAtmintis;
	LinkedList <String> atmintis;
	
	/**
	 * Konstruktoriai
	 */
	public VA(int vartotojoAtmintis) {
		this.atmintis = new LinkedList<String>();
		this.vartotojoAtmintis = vartotojoAtmintis;
		for(int i=0;i<vartotojoAtmintis;i++) {
				atmintis.add("");
		}
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
	public void set(int index, String element) {
		this.atmintis.set(index, element);
	}
	public String get(int a) {
		return this.atmintis.get(a);
	}
	public LinkedList<String> getList() {
		return atmintis;
	}
	public int getAllMemory() {
		return vartotojoAtmintis;
	}
}
