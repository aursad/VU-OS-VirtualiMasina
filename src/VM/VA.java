package VM;

import java.util.LinkedList;

import RM.RM;

/**
 * Vartotojo atmintis
 * 
 * @author Aurimas
 * 
 */
public class VA {

	int memory;
	public VA(int memory) {
		this.memory = memory;
	}
	/**
	 * Norimu adresu gauname elementà ið vartotoji atminties
	 * 
	 * @param a
	 *            Atminties adresas
	 * @return
	 */
	public void set(int index, String element) {
		RM.memory.set(index, element);
	}

	public String get(int a) {
		return RM.memory.getWord(a);
	}

	public LinkedList<String> getList() {
		LinkedList<String> list = new LinkedList<String>();
		for (int i = 0; i < RM.memory.getSize(); i++) {
			for (int n = 0; n < 10; n++) {
				list.add(RM.memory.getWord(i, n));
			}
		}
		return list;
	}

	public int getAllMemory() {
		return this.memory;
	}
}