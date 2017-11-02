import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingWorker;
import javax.swing.text.NumberFormatter;

public class MainWindow {

	private JFrame frame;
	private JFormattedTextField textField;
	private JCheckBox chckbxOpenChestsWhen;
	private JCheckBox chckbxPlayvQuick;
	private JLabel lblStatus;
	private ClashCommands cr;
	private JFrame parent;
	

	class ClashLauncher extends SwingWorker<Void, Void>{
	    protected Void doInBackground() throws Exception{
	        cr.launchClashRoyale();
	        return null;
	    }
	}
	
	class BattleLauncher extends SwingWorker<Void, Void>{
		boolean openChests = true;
		int times = 1;
		boolean twoVtwo = false;
		boolean manual = false;
		public BattleLauncher(boolean o, int t, boolean tvt, boolean man){
			this.openChests = o;
			this.times = t;
			this.twoVtwo = tvt;
			this.manual = man;
		}
		
	    protected Void doInBackground() throws Exception{
	        for(int i = 0; i < times; i++){
	        	if(openChests) cr.openChests();
	        	cr.startBattle(twoVtwo, manual);
	        }
	        return null;
	    }
	    
	    protected void done(){
	    	setStatus("Waiting...");
	    }
	}
	
	/**
	 * Create the application.
	 */
	public MainWindow(JFrame parent){
		this.parent = parent;
		initialize();
	}
	
	public void setStatus(String text){
		lblStatus.setText(" Status: " + text);
	}
	
	public String start(JadbDevice d) throws IOException, JadbException{
		cr = new ClashCommands(d, this);
		setStatus("Connected to " + d.toString());
		if(!cr.checkClashRoyaleInstalled()){
			return "Clash Royale is not installed or cannot be found on this device.";
		}
		new ClashLauncher().execute();
		this.frame.setVisible(true);
		return "";
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 580, 155);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(148dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("178px:grow"),},
			new RowSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("33px"),
				RowSpec.decode("33px"),
				RowSpec.decode("33px"),
				RowSpec.decode("33px"),}));
		
		chckbxOpenChestsWhen = new JCheckBox("Open Chests When Available");
		chckbxOpenChestsWhen.setSelected(true);
		frame.getContentPane().add(chckbxOpenChestsWhen, "1, 2, fill, fill");
		
		chckbxPlayvQuick = new JCheckBox("Play 2v2 Quick Match");
		frame.getContentPane().add(chckbxPlayvQuick, "3, 2, fill, fill");
		
		JLabel lblHowManyBattles = new JLabel("How Many Battles: ");
		frame.getContentPane().add(lblHowManyBattles, "1, 3, right, default");
		
		NumberFormat format = NumberFormat.getInstance();
	    format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(true);
	    formatter.setCommitsOnValidEdit(true);
		textField = new JFormattedTextField(formatter);
		textField.setText("1");
		lblHowManyBattles.setLabelFor(textField);
		frame.getContentPane().add(textField, "3, 3, fill, default");
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Wait For Manual 2v2 Battle To Start");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting manual 2v2 battle with selected options.");
						new BattleLauncher(chckbxOpenChestsWhen.isSelected(),Integer.parseInt(textField.getText()),true, true).execute();
					}else{
						JOptionPane.showMessageDialog(frame, "Clash Royale app is not open. Please check device.");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "An error has occurred. The error is: " + e1.getMessage());
					parent.setVisible(true);
					frame.setVisible(false);
					e1.printStackTrace();
					
				}
			}
		});
		frame.getContentPane().add(btnNewButton_1, "1, 4, fill, fill");
		
		JButton btnNewButton = new JButton("Start Running");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting battle with selected options.");
						new BattleLauncher(chckbxOpenChestsWhen.isSelected(),Integer.parseInt(textField.getText()),chckbxPlayvQuick.isSelected(), false).execute();
					}else{
						JOptionPane.showMessageDialog(frame, "Clash Royale app is not open. Please check device.");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frame, "An error has occurred. The error is: " + e1.getMessage());
					parent.setVisible(true);
					frame.setVisible(false);
					e1.printStackTrace();
					
				}
			}
		});
		frame.getContentPane().add(btnNewButton, "3, 4, fill, fill");
		
		lblStatus = new JLabel(" Status: Waiting...");
		frame.getContentPane().add(lblStatus, "1, 5, 3, 1, fill, fill");
	}

}
