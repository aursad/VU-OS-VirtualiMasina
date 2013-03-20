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
    			int ids = i*10+n;
    			value.add(n, ids+":----");
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
    public String getWord(int xx) {
    	int[] digits = getInts(xx);
    	LinkedList<String> list = getBlock(digits[0]);
    	String Word = list.get(digits[1]);
    	return Word;
    }
    public void set(int xx, String value) {
    	int[] digits = getInts(xx);
    	LinkedList<String> list = getBlock(digits[0]);
    	list.set(digits[1], value);
    }
    private int[] getInts(int xx) {
    	String string = Integer.toString(xx);
    	int[] digits = new int[string.length()];
    	  for(int i = 0; i<string.length(); ++i){
    	    digits[i] = Integer.parseInt(string.substring(i, i+1));
    	  }
    	  return digits;
    }
}
