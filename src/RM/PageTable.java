package RM;
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
			RM.memory.set(this.PageTableNumber, i, ""+2+i);
		}
	}
	public int getRealBlockNumber(int VirtualBlock) {
		String RBN = RM.memory.getWord(RM.PTR.getPageTable(), VirtualBlock);
		return Integer.parseInt(RBN);
	}
}
