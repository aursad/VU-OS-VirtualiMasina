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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import OS.OS;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Konsol�
	 */
	private JTextField console;
	private DefaultListModel<String> listModel;
	/**
	 * Registras - R
	 */
	private JTextField textRegisterR;
	/**
	 * Registras - C
	 */
	private JTextField textRegisterC;
	/**
	 * Reigistras - IC
	 */
	private JTextField textRegisterIC;
	/**
	 * FileChooser - fail� pasirinkimas
	 */
	private JFileChooser fc;
	/**
	 * B�senos reik�m�
	 */
	private JTextField textState;

	/**
	 * Create the frame.
	 */
	public MainWindow(final OS os) {
		setTitle("Virtuali Ma�ina - Emulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final JTextPane textPanel = new JTextPane();
		textPanel.setBackground(Color.WHITE);
		final JScrollPane textPane = new JScrollPane(textPanel);
		
		
		final JButton btnNuskaitytiProgram = new JButton("Nuskaityti program\u0105");
		final JButton btnStart = new JButton("Vykdyti");
		final JButton btnEnd = new JButton("Pabaigti");
		btnEnd.setEnabled(false);
		btnStart.setEnabled(false);
		
		/**
		 * Nuskaityti fail� kvietimas
		 */
		btnNuskaitytiProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileNameExtensionFilter("VM Failai", "vm"));
		            int returnVal = fc.showOpenDialog(MainWindow.this);
		 
		            if (returnVal == JFileChooser.APPROVE_OPTION) {
		                File file = fc.getSelectedFile();
		                textPanel.setText(textPanel.getText() + "\n> Opened: " +file.getName());
		            } else {
		                // Vartotojas at�aukia pasirinkim�
		            	textPanel.setText(textPanel.getText() + "\n> File Chooser closed.");
		            }
				}
		});
		
		/**
		 * Kurimas s�ra�as element� � JList
		 */
		listModel = new DefaultListModel<String>();
		for(int i=0;i<100;i++) {
			listModel.addElement(String.format("%02d", i)+":");
		}
		final JList<String> listas = new JList<String>(listModel);
		listas.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		        if (evt.getValueIsAdjusting())
		          return;
		        int index = listas.getSelectedIndex();
		        textPanel.setText(textPanel.getText() + "\n> selected #"+index+" row");
		      }
		    });
		listas.setSelectedIndex(4); // ID nurodo kuris yra selected
		listas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		final JScrollPane list = new JScrollPane(listas);
		
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnEnd.setEnabled(false);
				btnStart.setEnabled(false);
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnEnd.setEnabled(true);
				/* atnaujinimas JList elemento norimu tekstu
				 * listModel.set(22, "22: FAKE");
				listas.setSelectedIndex(22);
				list.revalidate();
				list.repaint();*/
			}
		});
		
		btnNuskaitytiProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStart.setEnabled(true);
			}
		});
		
		console = new JTextField();
		console.setColumns(10);
		console.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	 textPanel.setText(textPanel.getText() + "\n> " +console.getText());
	        	 console.setText(null);
	        }
	    });
		
		
		JSeparator separator = new JSeparator();
		/*
		 * Pirmasis tekstas � konsol�
		 */
		textPanel.setText(">>>  Console start");
		
		JSeparator separator_1 = new JSeparator();
		
		JButton btnPerkrauti = new JButton("Perkrauti");
		btnPerkrauti.setEnabled(false);
		
		JLabel lblR = new JLabel("R:");
		JLabel lblC = new JLabel("C:");
		JLabel lblIc = new JLabel("IC:");
		JLabel lblBsena = new JLabel("B\u016Bsena:");
		
		textRegisterR = new JTextField();
		textRegisterR.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterR.setText(""+os.getR());
		textRegisterR.setEditable(false);
		textRegisterR.setColumns(10);
		
		textRegisterC = new JTextField();
		textRegisterC.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterC.setText(""+os.getC());
		textRegisterC.setEditable(false);
		textRegisterC.setColumns(10);
		
		textRegisterIC = new JTextField();
		textRegisterIC.setHorizontalAlignment(SwingConstants.CENTER);
		textRegisterIC.setText(""+os.getIC());
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
					    "Virtuali Ma�ina - OS II u�duotis\n\nKur�jas:\nAurimas Sadauskas     (kaimisx@gmail.com)\nVilimantas Bernotaitis  (vilevicius@gmail.com)\n\n" +
					    "https://github.com/kaimis/VirtualiMasina\n2013",
					    "Virtuali Ma�ina - Informacija",
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
							.addComponent(btnPerkrauti)
							.addPreferredGap(ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
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
							.addComponent(btnPerkrauti))
						.addComponent(btnVersijaV))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
