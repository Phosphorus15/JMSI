package net.steepout.jmsi.ui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;
import javax.swing.JCheckBox;

public class CommandFrame extends JFrame {
	
	EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();

	private JPanel contentPane;
	private JTextField textField;
	JEditorPane textpane;
	String old="";
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmDisconnect;
	private JMenuItem mntmFromScript;
	private JMenuItem mntmRedirect;
	private JMenuItem mntmCleanScreen;

	/**
	 * Create the frame.
	 */
	public CommandFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 558, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.SOUTH);
		
		textField = new JTextField();
		textField.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		toolBar.add(textField);
		textField.setColumns(10);
		
		final JButton btnNewButton = new JButton("   Execute   ");
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==e.VK_ENTER){
					if(textField.getText()==null){
						return;
					}else if(textField.getText().equals("")){
						return;
					}
					UIMain.callCommandExecute(textField.getText());
					textField.setText("");
				}
				textField.requestFocus();
			}
		});
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//System.out.println("invoked!");
				if(textField.getText()==null){
					return;
				}else if(textField.getText().equals("")){
					return;
				}
				//System.out.println("calling!");
				UIMain.callCommandExecute(textField.getText());
				textField.setText("");
			}
		});
		toolBar.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textpane = new JEditorPane();
		textpane.setContentType("text/html");
		textpane.setFont(new Font("ºÚÌå", Font.PLAIN, 16));
		textpane.setEditable(false);
		scrollPane.setViewportView(textpane);
		
		menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		
		mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIMain.transfer.println("DISCONNECT");
			}
		});
		mntmDisconnect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnNewMenu.add(mntmDisconnect);
		
		mntmCleanScreen = new JMenuItem("Clean Screen");
		mntmCleanScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clean();
			}
		});
		mntmCleanScreen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnNewMenu.add(mntmCleanScreen);
		
		mntmFromScript = new JMenuItem("From Script");
		mntmFromScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIMain.callScriptExecute();
			}
		});
		mntmFromScript.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnNewMenu.add(mntmFromScript);
		
		mntmRedirect = new JMenuItem("Redirect");
		mntmRedirect.setEnabled(false);
		mnNewMenu.add(mntmRedirect);
	}
	
	public void append(String str){
		append(str,"color");
	}
	
	public void append(String str,String color){
		old += "<font color=\""+color+"\">"+str+"</font><br/>";
		final String gen = "<html><head></head><body>"+old+"</body></html>";
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				CommandFrame.this.textpane.setText(gen);
			}
			
		});
		repaint();
	}
	
	public void clean(){
		old = "";
		final String gen = "<html><head></head><body>"+old+"</body></html>";
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				CommandFrame.this.textpane.setText(gen);
			}
			
		});
		repaint();
	}
	
	public void cleanInput(){
		textField.setText("");
		repaint();
	}
	
	public void shutdown(){
		
	}
	
	/*protected void processEvent(AWTEvent event){
		if(event instanceof StringUpdateEvent){
			StringUpdateEvent e = (StringUpdateEvent) event;
			old += "<font color=\""+e.getColor()+"\">"+e.getString()+"</font><br/>";
			String gen = "<html><head></head><body>"+old+"</body></html>";
			textpane.setText(gen);
			repaint();
		}else{
			super.processEvent(event);
		}
	}*/

}
