package OS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class readFile {
	private String text;
	
	public readFile(File file) throws IOException {
		FileReader fr = new FileReader(file); 
		BufferedReader br = new BufferedReader(fr); 
		String s; 
		while((s = br.readLine()) != null) { 
		text = text + s + "\n";
		} 
		fr.close(); 
	}
	public String get() {
		return text;
	}
}
