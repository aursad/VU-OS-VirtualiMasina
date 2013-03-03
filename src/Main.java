import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;

import OS.OS;
import UI.MainWindow;


public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final OS os = new OS();
					MainWindow frame = new MainWindow(os);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
