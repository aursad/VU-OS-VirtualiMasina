package VM;

import RM.RM;


/**
 * Puslapiø lentelë
 * @author Aurimas
 *
 */
public class PageTable {
	int PageTableNumber;
	
	public PageTable() {
		this.PageTableNumber = RM.PTR.getPageTable();
		for(int i=0;i<10;i++) {
			RM.memory.set(this.PageTableNumber, i, ""+3+i);
		}
		//RM.memory.set(this.PageTableNumber, 7, ""+0);
	}
	public int getRealBlockNumber(int VirtualBlock) {
		String RBN = RM.memory.getWord(RM.PTR.getPageTable(), VirtualBlock);
		return Integer.parseInt(RBN);
	}
}
