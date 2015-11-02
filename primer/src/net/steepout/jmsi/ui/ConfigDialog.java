package net.steepout.jmsi.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConfigDialog extends JDialog {
	private JTextField textField;
	private JTextField textField_1;
	JCheckBox base64;

	/**
	 * Create the dialog.
	 */
	public ConfigDialog() {
		setTitle("JMSI - remote service");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 402, 168);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textField.getText()!=null&&textField_1.getText()!=null){
							if(!textField.getText().equals("")&&!textField_1.getText().equals("")){
								ConfigDialog.this.setVisible(false);
								ConfigDialog.this.setModal(false);
								return;
							}
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			textField = new JTextField();
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if((!Character.isDigit(e.getKeyChar()))||textField.getText().length()>=5){
						e.consume();
					}
				}
			});
			textField.setText("7046");
			textField.setBounds(10, 29, 162, 21);
			panel.add(textField);
			textField.setColumns(10);
			
			JLabel lblNewLabel = new JLabel("Port:");
			lblNewLabel.setBounds(10, 10, 97, 15);
			panel.add(lblNewLabel);
			
			JLabel lblNewLabel_1 = new JLabel("Address:");
			lblNewLabel_1.setBounds(10, 55, 87, 15);
			panel.add(lblNewLabel_1);
			
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(10, 72, 162, 21);
			panel.add(textField_1);
			
			base64 = new JCheckBox("Use base64");
			base64.setBounds(238, 71, 135, 23);
			panel.add(base64);
		}
	}
	public String address(){
		System.out.println(textField_1.getText());
		return textField_1.getText();
	}
	public int port(){
		System.out.println(textField.getText());
		return Integer.parseInt(textField.getText());
	}
	public boolean base64(){
		return base64.isSelected();
	}
}
