package UI;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JList;
import javax.swing.JTextPane;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import OS.OS;


public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private DefaultListModel<String> listModel;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField txtVersijaV;

	/**
	 * Create the frame.
	 */
	public MainWindow(OS os) {
		setTitle("Virtuali Maðina - Emulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 668, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final JTextPane textPanel = new JTextPane();
		textPanel.setBackground(Color.WHITE);
		final JScrollPane textPane = new JScrollPane(textPanel);
		
		
		JButton btnNuskaitytiProgram = new JButton("Nuskaityti program\u0105");
		final JButton btnStart = new JButton("Vykdyti");
		final JButton btnEnd = new JButton("Pabaigti");
		btnEnd.setEnabled(false);
		btnStart.setEnabled(false);
		
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnEnd.setEnabled(false);
				btnStart.setEnabled(false);
			}
		});
		
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnEnd.setEnabled(true);
			}
		});
		
		btnNuskaitytiProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnStart.setEnabled(true);
			}
		});
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	 textPanel.setText(textPanel.getText() + "\n> " +textField.getText());
	        	 textField.setText(null);
	        }
	    });
		
		
		JSeparator separator = new JSeparator();

		listModel = new DefaultListModel<String>();
		for(int i=00;i<100;i++) {
			listModel.addElement(i+" text");
		}
		JList<String> listas = new JList<String>(listModel);
		listas.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent evt) {
		        if (evt.getValueIsAdjusting())
		          return;
		        textPanel.setText(textPanel.getText() + "\n> text");
		      }
		    });
		listas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane list = new JScrollPane(listas);
		

		textPanel.setText(">  program start");
		
		JSeparator separator_1 = new JSeparator();
		
		JButton btnPerkrauti = new JButton("Perkrauti");
		btnPerkrauti.setEnabled(false);
		
		JLabel lblR = new JLabel("R:");
		
		JLabel lblC = new JLabel("C:");
		
		JLabel lblIc = new JLabel("IC:");
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setText(""+os.getR());
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setText(""+os.getC());
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setText(""+os.getIC());
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		
		txtVersijaV = new JTextField();
		txtVersijaV.setEditable(false);
		txtVersijaV.setHorizontalAlignment(SwingConstants.CENTER);
		txtVersijaV.setText("Versija: v1.0");
		txtVersijaV.setColumns(10);
		
		
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
										.addComponent(separator_1, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
										.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
										.addComponent(textField, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
									.addGap(55))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(lblR, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblIc, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblC, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField_3)
										.addComponent(textField_2)
										.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
									.addGap(284)))
							.addComponent(list, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNuskaitytiProgram)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnStart)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEnd)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPerkrauti)
							.addPreferredGap(ComponentPlacement.RELATED, 176, Short.MAX_VALUE)
							.addComponent(txtVersijaV, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
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
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblC)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIc)
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(91)
							.addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(list, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
							.addGap(11)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuskaitytiProgram)
						.addComponent(btnStart)
						.addComponent(btnEnd)
						.addComponent(btnPerkrauti)
						.addComponent(txtVersijaV, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
