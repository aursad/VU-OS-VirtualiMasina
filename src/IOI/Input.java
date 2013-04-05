package IOI;

import java.util.HashMap;

public class Input {
	private HashMap<String, String> input;
	
	public Input() {
		input = new HashMap<String, String>();
		input.put("adresas", "");
		input.put("text", "");
	}
	
	public void setAA(int AA) {
		input.put("adresas", ""+AA);
	}
	public void setText(String text) {
		input.put("text", text);
	}
}
