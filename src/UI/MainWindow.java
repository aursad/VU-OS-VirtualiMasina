package UI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import IOI.Input;
import RM.RM;
import RM.RealMemory;
import VM.VA;
import VM.VM;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextField console;
	private static DefaultListModel<String> listModel;
	private static DefaultListModel<String> listRModel;
	private static JTextField textRegisterR;
	private static JTextField textRegisterC;
	private static JTextField textRegisterIC;
	private static JTextField textRegisterT;
	private static JTextField textRegisterPTR;
	private static JTextField textRegisterSI;
	private static JTextField textRegisterPI;
	private static JTextField textRegisterTI;
	private static JTextField textRegisterMODE;
	private static JTextField textRegisterCH;
	private JFileChooser fc;
	/**
	 * Bûsenos reikðmë
	 */
	private JTextField textState;
	static JTextPane textPanel;
	static JList<String> listas;
	static JScrollPane list;
	static JList<String> listasRM;
	static JScrollPane listRM;
	static String inputString;
	
	static JButton btnStart;
	static JButton btnStepByStep;
	
	static Input output;
	
	/**
	 * Create the frame.
	 */
	public MainWindow(final RM rm, final VM vm) {
		setTitle("Virtuali Maðina - Emulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 866, 400);
		output = new Input();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		textPanel = new JTextPane();
		textPanel.setBackground(Color.WHITE);
		final JScrollPane textPane = new JScrollPane(textPanel);
		
		
		final JButton btnNuskaitytiProgram = new JButton("Nuskaityti program\u0105");
		final JButton btnStart = new JButton("Vykdyti");
		final JButton btnEnd = new JButton("Pabaigti");
		final JButton btnStepByStep = new JButton("Po \u017Eingsn\u012F");
		btnEnd.setEnabled(false);
		btnStart.setEnabled(false);
		btnStepByStep.setEnabled(false);
		listRModel = new DefaultListModel<String>();
		for(int i=0;i<rm.memory.getSize();i++) {
			for(int n=0;n<10;n++) {
				listRModel.addElement(rm.memory.getWord(i, n));
			}
		}
		listasRM = new JList<String>(listRModel);
		listasRM.setSelectedIndex(0); // ID nurodo kuris yra selected
		listasRM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listRM = new JScrollPane(listasRM);
		/**
		 * Kurimas sàraðas elementø á JList
		 */
		listModel = new DefaultListModel<String>();
		for(int i=0;i<vm.Atmintis.getAllMemory();i++) {
			listModel.addElement(String.format("%02d", i)+": "+ rm.memory.getWord(i));
		}
		listas = new JList<String>(listModel);
		listas.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		        if (evt.getValueIsAdjusting())
		          return;
		        int index = listas.getSelectedIndex();
		        if(vm.Atmintis.get(index) != "") { textState.setText("uþimtas"); } else { textState.setText("laisvas"); }
		      }
		    });
		//listas.setSelectedIndex(0); // ID nurodo kuris yra selected
		listas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list = new JScrollPane(listas);
		
		/**
		 * Nuskaityti failà kvietimas
		 */
		btnNuskaitytiProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileNameExtensionFilter("VM Failai", "vm"));
		            int returnVal = fc.showOpenDialog(MainWindow.this);
		 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
						try {
							FileReader fr = new FileReader(file); 
							BufferedReader br = new BufferedReader(fr); 
							String s; 
							while((s = br.readLine()) != null) { 
								String[] value = s.split("(?<=\\G.{2})");
								int key = Integer.parseInt(value[0]);
								String keyWord = String.format("%02d", key);
								String Word = value[1]+value[2];
									rm.memory.set(key, Word);
									listModel.set(key, keyWord+": "+rm.memory.getWord(key));
							} 
							fr.close(); 
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						vm.IC.set(0);
						//listas.setSelectedIndex(0);
						list.revalidate();
						list.repaint();
						updateListRM(rm.memory);
		            } else {
		                // Vartotojas atðaukia pasirinkimà
		            	textPanel.setText(textPanel.getText() + "\n> File Chooser closed.");
		            }
				}
		});
		

		
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textPanel.setText(">> Console restart");
				vm.C.set(0);
				vm.IC.set(0);
				vm.R.set(0);
				RM.SI.set(0);
				RM.PI.set(0);
				RM.T.set(0);
				RM.TI.update();
				
				textRegisterR.setText(Integer.toString(vm.R.get()));
				textRegisterC.setText(Integer.toString(vm.C.get()));
				textRegisterIC.setText(Integer.toString(vm.IC.get()));
				textRegisterT.setText(Integer.toString(RM.T.get()));
				textRegisterSI.setText(Integer.toString(RM.SI.get()));
				textRegisterPI.setText(Integer.toString(RM.PI.get()));
				textRegisterTI.setText(Integer.toString(RM.TI.get()));
				textRegisterMODE.setText(Integer.toString(RM.MODE.get()));
				textRegisterCH.setText(Integer.toString(RM.CH.get()));
				textRegisterPTR.setText(RM.PTR.get());
				
				btnEnd.setEnabled(false);
				btnStart.setEnabled(false);
				btnStepByStep.setEnabled(false);
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//startPRogram
				vm.startProgram();
				btnEnd.setEnabled(true);
			}
		});
		
		btnNuskaitytiProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStart.setEnabled(true);
				btnStepByStep.setEnabled(true);
			}
		});
		btnStepByStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnEnd.setEnabled(true);
				// Vykdyti programà po þingsná
				vm.startProgramStepByStep();
			}
		});
		
		console = new JTextField();
		console.setEnabled(true);
		console.setColumns(10);
		console.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	 textPanel.setText(textPanel.getText() + "\n> " +console.getText());
	        	 output.set(console.getText());
	        	 console.setText(null);
	        }
	    });
		
		JSeparator separator = new JSeparator();
		/*
		 * Pirmasis tekstas á konsolæ
		 */
		textPanel.setText(">>>  Console start");
		
		JSeparator separator_1 = new JSeparator();
		
		JLabel lblR = new JLabel("R:");
		JLabel lblC = new JLabel("C:");
		JLabel lblIc = new JLabel("IC:");
		JLabel lblBsena = new JLabel("B\u016Bsena:");
		JLabel lblT = new JLabel("T:");
		JLabel lblSi = new JLabel("SI:");
		JLabel lblMode = new JLabel("MODE:");
		JLabel lblCh = new JLabel("CH:");
		JLabel lblPi = new JLabel("PI:");
		JLabel lblTi = new JLabel("TI:");
		JLabel lblPtr = new JLabel("PTR:");
		
		textRegisterR = new JTextField();
		textRegisterR.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterR.setText(toString(vm.R.get()));
		textRegisterR.setEditable(false);
		textRegisterR.setColumns(10);
		
		textRegisterC = new JTextField();
		textRegisterC.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterC.setText(toString(vm.C.get()));
		textRegisterC.setEditable(false);
		textRegisterC.setColumns(10);
		
		textRegisterIC = new JTextField();
		textRegisterIC.setText(toString(vm.IC.get()));
		textRegisterIC.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterIC.setEditable(false);
		textRegisterIC.setColumns(10);
		
		
		textState = new JTextField();
		textState.setEditable(false);
		textState.setHorizontalAlignment(SwingConstants.CENTER);
		textState.setText("laisvas");
		textState.setColumns(10);
		
		textRegisterT = new JTextField();
		textRegisterT.setText(toString(rm.T.get()));
		textRegisterT.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterT.setEditable(false);
		textRegisterT.setColumns(10);
		
		textRegisterPTR = new JTextField();
		textRegisterPTR.setText("0");
		textRegisterPTR.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterPTR.setEditable(false);
		textRegisterPTR.setColumns(10);
			
		textRegisterSI = new JTextField();
		textRegisterSI.setText(toString(rm.SI.get()));
		textRegisterSI.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterSI.setEditable(false);
		textRegisterSI.setColumns(10);
		
		textRegisterPI = new JTextField();
		textRegisterPI.setText(toString(rm.PI.get()));
		textRegisterPI.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterPI.setEditable(false);
		textRegisterPI.setColumns(10);
		
		textRegisterTI = new JTextField();
		textRegisterTI.setText(toString(rm.TI.get()));
		textRegisterTI.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterTI.setEditable(false);
		textRegisterTI.setColumns(10);
		
		textRegisterMODE = new JTextField();
		textRegisterMODE.setText(toString(rm.MODE.get()));
		textRegisterMODE.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterMODE.setEditable(false);
		textRegisterMODE.setColumns(10);

		textRegisterCH = new JTextField();
		textRegisterCH.setText(toString(rm.CH.get()));
		textRegisterCH.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterCH.setEditable(false);
		textRegisterCH.setColumns(10);
		
		
		JButton btnVersijaV = new JButton("Versija: v1.0");
		btnVersijaV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(contentPane,
					    "Virtuali Maðina - OS II uþduotis\n\nKurëjas:\nAurimas Sadauskas     (kaimisx@gmail.com)\nVilimantas Bernotaitis  (vilevicius@gmail.com)\n\n" +
					    "https://github.com/kaimis/VirtualiMasina\n2013",
					    "Virtuali Maðina - Informacija",
					    JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblR, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblIc, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblC, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textRegisterIC, 0, 0, Short.MAX_VALUE)
										.addComponent(textRegisterC, 0, 0, Short.MAX_VALUE)
										.addComponent(textRegisterR, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
									.addGap(6)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblBsena)
										.addComponent(lblT, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPtr, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblMode, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
											.addComponent(textRegisterPTR, 0, 0, Short.MAX_VALUE)
											.addComponent(textRegisterT, 0, 0, Short.MAX_VALUE)
											.addComponent(textState, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
										.addComponent(textRegisterMODE, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblCh)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textRegisterCH, 0, 0, Short.MAX_VALUE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblSi, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(textRegisterSI, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblPi, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(textRegisterPI, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblTi, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(textRegisterTI, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED, 180, Short.MAX_VALUE))
								.addComponent(console, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
								.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(listRM, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNuskaitytiProgram)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStart)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEnd)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStepByStep)
							.addPreferredGap(ComponentPlacement.RELATED, 363, Short.MAX_VALUE)
							.addComponent(btnVersijaV)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblR)
								.addComponent(textRegisterR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblBsena)
								.addComponent(textState, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSi)
								.addComponent(textRegisterSI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblC)
										.addComponent(textRegisterC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(textRegisterT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblT)
										.addComponent(lblPi)
										.addComponent(textRegisterPI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblIc)
											.addComponent(textRegisterIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(textRegisterPTR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblPtr)
											.addComponent(lblTi))))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(26)
									.addComponent(textRegisterTI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCh)
								.addComponent(textRegisterMODE, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textRegisterCH, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblMode))
							.addGap(56)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(console, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(list, GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
								.addComponent(listRM, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE))
							.addGap(11)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNuskaitytiProgram)
							.addComponent(btnStart)
							.addComponent(btnEnd)
							.addComponent(btnStepByStep))
						.addComponent(btnVersijaV))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
	public static void updateIC(int IC) {
		textRegisterIC.setText(toString(IC));
	}
	public static void updateR(int R) {
		textRegisterR.setText(toString(R));
	}
	public static void updateC(int C) {
		textRegisterC.setText(toString(C));
	}
	public static void updatePTR(String string) {
		textRegisterPTR.setText(string);
	}
	public static void updateT(int T) {
		textRegisterT.setText(toString(T));
	}
	public static void updateSI(int SI) {
		textRegisterSI.setText(toString(SI));
	}
	public static void updatePI(int PI) {
		textRegisterPI.setText(toString(PI));
	}
	public static void updateTI(int TI) {
		textRegisterTI.setText(toString(TI));
	}
	public static void updateMODE(int MODE) {
		textRegisterMODE.setText(toString(MODE));
	}
	public static void updateCH(int CH) {
		textRegisterCH.setText(toString(CH));
	}
	public static void updateListSelected(int id) {
		listas.setSelectedIndex(id);
		list.revalidate();
		list.repaint();
	}
	public static void updateConsole(String text) {
		textPanel.setText(textPanel.getText() + "\n"+text);
	}
	public static String getConsole() {
		return output.get();
	}
	public static void setConsole() {
		output.set("");
	}
	private static String toString(int fromInt) {
		return Integer.toString(fromInt);
	}

	public static void updateList(RealMemory Atmintis) {
		for (int i = 0; i < Atmintis.getSize(); i++) {
			for(int n=0;n<10;n++) {
				listModel.set(i*10+n,String.format("%02d", i*10+n) + ": " + Atmintis.getWord(i, n));
			}
		}
		listas.setSelectedIndex(0);
		list.revalidate();
		list.repaint();
	}
	public static void updateListRM(RealMemory Atmintis) {
		for (int i = 0; i < Atmintis.getSize(); i++) {
			for(int n=0;n<10;n++) {
				listRModel.set(i*10+n,String.format("%02d", i*10+n) + ": " + Atmintis.getWord(i, n));
			}
		}
		listasRM.setSelectedIndex(0);
		listRM.revalidate();
		listRM.repaint();
	}
}
