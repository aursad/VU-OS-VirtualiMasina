package RM;

import java.util.HashMap;
import java.util.LinkedList;

public class RealMemory {
	HashMap<Integer, LinkedList<String>> memory;
    private int size=0;
    
    public RealMemory(int blocks) {
    	this.size = blocks;
    	this.memory = new HashMap<Integer, LinkedList<String>>();
    	for(int i=0;i<blocks;i++) {
    		LinkedList<String> value = new LinkedList<String>();
    		for(int n=0;n<10;n++) {
    			value.add(n, "");
    		}
    		this.memory.put(i, value);
    	}
    }
    public LinkedList<String> getBlock(int block) {
    	return this.memory.get(block);
    }
    public int getSize() {
    	return this.size;
    }
    public String getWord(int block, int index){
    	LinkedList<String> list = getBlock(block);
    	String Word = list.get(index);
    	return Word;
    }
}
