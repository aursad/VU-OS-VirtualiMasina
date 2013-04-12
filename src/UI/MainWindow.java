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

import RM.ExternalMemory;
import RM.RM;
import RM.RealMemory;
import VM.VA;
import VM.VM;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextField console;
	private static DefaultListModel<String> listModel;
	private static DefaultListModel<String> listEModel;
	private static DefaultListModel<String> listRModel;
	private static JTextField textRegisterR;
	private static JTextField textRegisterC;
	private static JTextField textRegisterIC;
	private static JTextField textRegisterRv;
	private static JTextField textRegisterCv;
	private static JTextField textRegisterICv;
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
	static JList<String> listasEX;
	static JScrollPane listEX;
	static String inputString;
	
	static JButton btnStart;
	static JButton btnStepByStep;
	
	static String output = "";
	
	/**
	 * Create the frame.
	 */
	@SuppressWarnings("static-access")
	public MainWindow(final RM rm, final VM vm) {
		setTitle("Virtuali Maðina - Emulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 400);
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
		
		listEModel = new DefaultListModel<String>();
		for(int i=0;i<rm.externalMemory.getSize();i++) {
			for(int n=0;n<10;n++) {
				listEModel.addElement(String.format("%02d", i*10+n)+": "+rm.externalMemory.getWord(i, n));
			}
		}
		listasEX = new JList<String>(listEModel);
		listasEX.setSelectedIndex(0); // ID nurodo kuris yra selected
		listasEX.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listEX = new JScrollPane(listasEX);
		
		listRModel = new DefaultListModel<String>();
		for(int i=0;i<rm.memory.getSize();i++) {
			for(int n=0;n<10;n++) {
				listRModel.addElement(String.format("%02d", i*10+n)+": "+rm.memory.getWord(i, n));
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
		for(int i=0;i<rm.PTR.getBlock()*10+10;i++) {
			listModel.addElement(String.format("%02d", i)+": _____");
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
								String[] value = s.split("(?<=\\G.{1})");
								String Word = "";
								int key1 = Integer.parseInt(value[0]);
								int key2 = Integer.parseInt(value[1]);
								int key = key1*10+key2;
								String keyWord = String.format("%02d", key);
								if(value.length == 7) {
									Word = value[2]+value[3]+value[4]+value[5]+value[6];
								} else {
									Word = value[2]+value[3]+value[4]+value[5];
								}
									vm.Atmintis.set(key, Word);
									listModel.set(key, keyWord+": "+vm.Atmintis.get(key));
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
				// Iðsaugom duomenis iðorinë atmintyje
				rm.saveMemory();
				
				textPanel.setText(">> Console restart");
				rm.C.set(0);
				rm.IC.set(0);
				rm.R.set(0);
				vm.C.set(0);
				vm.IC.set(0);
				vm.R.set(0);
				RM.SI.set(0);
				RM.PI.set(0);
				RM.T.set(0);
				RM.TI.update();
				RM.MODE.set(0);
				
				textRegisterR.setText(Integer.toString(rm.R.get()));
				textRegisterC.setText(Integer.toString(rm.C.get()));
				textRegisterIC.setText(Integer.toString(rm.IC.get()));
				textRegisterRv.setText(Integer.toString(vm.R.get()));
				textRegisterCv.setText(Integer.toString(vm.C.get()));
				textRegisterICv.setText(Integer.toString(vm.IC.get()));
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
	        	 output = console.getText();
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
		JLabel lblRm = new JLabel("RM");
		JLabel lblVm = new JLabel("VM");
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
		textRegisterR.setText(toString(rm.R.get()));
		textRegisterR.setEditable(false);
		textRegisterR.setColumns(10);
		
		textRegisterC = new JTextField();
		textRegisterC.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterC.setText(toString(rm.C.get()));
		textRegisterC.setEditable(false);
		textRegisterC.setColumns(10);
		
		textRegisterIC = new JTextField();
		textRegisterIC.setText("000");
		textRegisterIC.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterIC.setEditable(false);
		textRegisterIC.setColumns(10);
		
		textRegisterRv = new JTextField();
		textRegisterRv.setText(toString(vm.R.get()));
		textRegisterRv.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterRv.setEditable(false);
		textRegisterRv.setColumns(10);
		
		textRegisterCv = new JTextField();
		textRegisterCv.setText(toString(vm.C.get()));
		textRegisterCv.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterCv.setEditable(false);
		textRegisterCv.setColumns(10);
		
		textRegisterICv = new JTextField();
		textRegisterICv.setText("000");
		textRegisterICv.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterICv.setEditable(false);
		textRegisterICv.setColumns(10);
		
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
		textRegisterPTR.setText(rm.PTR.get());
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
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(406))
								.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblR, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblIc, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblC, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_contentPane.createSequentialGroup()
												.addGap(22)
												.addComponent(lblRm, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_contentPane.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
													.addComponent(textRegisterC, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
													.addComponent(textRegisterR, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(textRegisterIC, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblVm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(lblMode, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
										.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(textRegisterRv, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
												.addComponent(textRegisterCv, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
												.addComponent(textRegisterICv, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
											.addPreferredGap(ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(40)
													.addComponent(lblBsena))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(40)
													.addComponent(lblT, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_contentPane.createSequentialGroup()
													.addGap(40)
													.addComponent(lblPtr, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))))
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
									.addGap(58))
								.addComponent(console, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE)
									.addGap(13)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(listEX, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
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
							.addPreferredGap(ComponentPlacement.RELATED, 502, Short.MAX_VALUE)
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
								.addComponent(lblBsena)
								.addComponent(textState, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSi)
								.addComponent(textRegisterSI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textRegisterR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textRegisterRv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblC)
										.addComponent(textRegisterT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblT)
										.addComponent(lblPi)
										.addComponent(textRegisterPI, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(textRegisterC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(textRegisterCv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblIc)
											.addComponent(textRegisterIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(textRegisterICv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblMode)
									.addComponent(lblVm))
								.addComponent(lblRm))
							.addGap(56)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(console, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(list, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
								.addComponent(listRM, GroupLayout.PREFERRED_SIZE, 298, GroupLayout.PREFERRED_SIZE)
								.addComponent(listEX, GroupLayout.PREFERRED_SIZE, 307, GroupLayout.PREFERRED_SIZE))
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
	public static void updateICv(int IC) {
		textRegisterICv.setText(toString(IC));
	}
	public static void updateRv(int R) {
		textRegisterRv.setText(toString(R));
	}
	public static void updateCv(int C) {
		textRegisterCv.setText(toString(C));
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
		return output;
	}
	public static void setConsole() {
		output="";
	}
	private static String toString(int fromInt) {
		return Integer.toString(fromInt);
	}

	public static void updateList(VA Atmintis) {
		for (int i = 0; i < RM.PTR.getBlock(); i++) {
			for(int n=0;n<10;n++) {
				listModel.set(i*10+n,String.format("%02d", i*10+n) + ": " + Atmintis.get(i*10+n));
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
	public static void updateListEM(ExternalMemory Atmintis) {
		for (int i = 0; i < Atmintis.getSize(); i++) {
			for(int n=0;n<10;n++) {
				listEModel.set(i*10+n,String.format("%02d", i*10+n) + ": " + ExternalMemory.getWord(i, n));
			}
		}
		listasEX.setSelectedIndex(0);
		listEX.revalidate();
		listEX.repaint();
	}
}
