import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;

import RM.RM;
import UI.MainWindow;
import VM.VM;


public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final RM os = new RM();
					final VM vm = new VM();
					MainWindow frame = new MainWindow(os, vm);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
