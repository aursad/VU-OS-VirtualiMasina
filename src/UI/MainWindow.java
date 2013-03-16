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
import OS.OS;
import VM.VA;
import VM.VM;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Konsolë
	 */
	private static JTextField console;
	private static DefaultListModel<String> listModel;
	/**
	 * Registras - R
	 */
	private static JTextField textRegisterR;
	/**
	 * Registras - C
	 */
	private static JTextField textRegisterC;
	/**
	 * Reigistras - IC
	 */
	private static JTextField textRegisterIC;
	/**
	 * FileChooser - failø pasirinkimas
	 */
	private JFileChooser fc;
	/**
	 * Bûsenos reikðmë
	 */
	private JTextField textState;
	static JTextPane textPanel;
	static JList<String> listas;
	static JScrollPane list;
	static String inputString;
	
	static JButton btnStart;
	static JButton btnStepByStep;
	
	static Input output;
	/**
	 * Create the frame.
	 */
	public MainWindow(final OS os, final VM vm) {
		setTitle("Virtuali Maðina - Emulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 400);
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
		
		
		/**
		 * Kurimas sàraðas elementø á JList
		 */
		listModel = new DefaultListModel<String>();
		for(int i=0;i<100;i++) {
			listModel.addElement(String.format("%02d", i)+": "+ vm.Atmintis.get(i));
		}
		listas = new JList<String>(listModel);
		listas.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		        if (evt.getValueIsAdjusting())
		          return;
		        int index = listas.getSelectedIndex();
		        if(vm.Atmintis.get(index) != "") { textState.setText("uþimtas"); } else { textState.setText("laisvas"); }
		        //textPanel.setText(textPanel.getText() + "\n> selected "+vm.Atmintis.get(index)+" command.");
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
								int foo = Integer.parseInt(value[0]);
									vm.Atmintis.set(foo, ""+value[1]+value[2]);
									listModel.set(foo, String.format("%02d", foo)+": "+vm.Atmintis.get(foo));
							} 
							fr.close(); 
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						vm.IC.set(0);
						//listas.setSelectedIndex(0);
						list.revalidate();
						list.repaint();
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
				OS.SI.set(0);
				
				textRegisterIC.setText(""+vm.IC.get());
				textRegisterR.setText(""+vm.R.get());
				textRegisterC.setText(""+vm.C.get());
				
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
		
		textRegisterR = new JTextField();
		textRegisterR.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterR.setText(""+os.R.get());
		textRegisterR.setEditable(false);
		textRegisterR.setColumns(10);
		
		textRegisterC = new JTextField();
		textRegisterC.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterC.setText(""+os.C.get());
		textRegisterC.setEditable(false);
		textRegisterC.setColumns(10);
		
		textRegisterIC = new JTextField();
		textRegisterIC.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterIC.setText(""+os.IC.get());
		textRegisterIC.setEditable(false);
		textRegisterIC.setColumns(10);
		
		
		textState = new JTextField();
		textState.setEditable(false);
		textState.setHorizontalAlignment(SwingConstants.CENTER);
		textState.setText("laisvas");
		textState.setColumns(10);
		
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
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
										.addComponent(console, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
									.addGap(55))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblR, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblIc, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblC, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textRegisterIC)
										.addComponent(textRegisterC)
										.addComponent(textRegisterR, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblBsena)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textState, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(145)))
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNuskaitytiProgram)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStart)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEnd)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStepByStep)
							.addPreferredGap(ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
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
								.addComponent(textState, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblC)
								.addComponent(textRegisterC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIc)
								.addComponent(textRegisterIC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(91)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(console, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(list, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
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
		textRegisterIC.setText(""+IC);
	}
	public static void updateR(int R) {
		textRegisterR.setText(""+R);
	}
	public static void updateC(int C) {
		textRegisterC.setText(""+C);
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
	public static void updateList(VA Atmintis) {
		for(int i=0;i<Atmintis.getAllMemory();i++) {
			listModel.set(i, String.format("%02d", i)+": "+ Atmintis.get(i));
		}
		listas.setSelectedIndex(0);
		list.revalidate();
		list.repaint();
	}
}
