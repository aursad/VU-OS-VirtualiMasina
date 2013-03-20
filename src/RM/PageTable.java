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
			int xx = this.PageTableNumber*10+i;
			RM.memory.set(xx, ""+i);
		}
	}
	public String getRealBlockNumber(int VirtualBlock) {
		String RBN = RM.memory.getWord(this.PageTableNumber, VirtualBlock);
		return RBN;
	}
}
