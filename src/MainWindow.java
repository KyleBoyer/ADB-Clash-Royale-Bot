import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import se.vidstige.jadb.JadbDevice;

import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.text.NumberFormatter;

public class MainWindow {

	private JFrame frmClashRoyaleBot;
	private JFormattedTextField numTimesTextField;
	private JCheckBox openChestsCheckBox;
	private JCheckBox play2v2CheckBox;
	private JLabel statusLabel;
	private ClashCommands cr;
	private JFrame parent;
	private Marquee marquee;
	private JButton cancelButton;
	private JButton manual2v2Button;
	private JButton startButton;
	private BattleLauncher runningSwingWorker;
	private JLabel statusTextLabel;
	private JadbDevice device;
	private JCheckBox autoJoinCheckBox;
	private JButton chestsOnlyButton;
	

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
		boolean autojoin = false;
		boolean useFormValues = true;
		public BattleLauncher(boolean o, int t, boolean tvt, boolean man, boolean aj){
			this.openChests = o;
			this.times = t;
			this.twoVtwo = tvt;
			this.manual = man;
			this.autojoin = aj;
			this.useFormValues = false;
		}
		
		public BattleLauncher(boolean man){
			this.useFormValues = true;
			this.manual = man;
		}
		
		private int getTimes(){
			if(useFormValues){
				return Integer.parseInt(numTimesTextField.getText());
			}else{
				return times;
			}
		}
		
		private boolean getOpenChests(){
			if(useFormValues){
				return openChestsCheckBox.isSelected();
			}else{
				return openChests;
			}
		}
		
		private boolean get2v2(){
			if(useFormValues){
				return play2v2CheckBox.isSelected();
			}else{
				return twoVtwo;
			}
		}
		
		private boolean getAutoJoin(){
			if(useFormValues){
				return autoJoinCheckBox.isSelected();
			}else{
				return autojoin;
			}
		}
		
	    protected Void doInBackground() throws Exception{
	    	setCancelable(true);
	    	//if(autoJoinCheckBox.isSelected())
	        for(int i = 0; i < getTimes(); i++){
	        	if(this.isCancelled()) break;
	        	if(getOpenChests()) cr.openChests();
	        	if(this.isCancelled()) break;
	        	cr.startBattle(get2v2(), manual, getAutoJoin());
	        }
	        if(getOpenChests() && getTimes() < 1){
	        	cr.openChests();
	        }
	        return null;
	    }
	    
	    protected void done(){
			setStatus("Connected to " + device.toString() + ".");
	    	setCancelable(false);
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
		if(marquee != null) marquee.stop();
		statusTextLabel.setText(text);
		FontMetrics metrics = statusLabel.getFontMetrics(statusLabel.getFont());
		if(metrics.stringWidth(text) >= statusTextLabel.getWidth()){
			String textSub = text;
			while(metrics.stringWidth(textSub) >= statusTextLabel.getWidth()){
				textSub = textSub.substring(0, textSub.length() - 1);
			}
			marquee = new Marquee(statusTextLabel, textSub.length() - 2);
			marquee.start();
		}
	}
	
	public void setCancelable(boolean cancelable){
		cancelButton.setEnabled(cancelable);
		manual2v2Button.setEnabled(!cancelable);
		startButton.setEnabled(!cancelable);
		chestsOnlyButton.setEnabled(!cancelable);
	}
	
	public String start(JadbDevice d) throws Exception{
		this.device = d;
		cr = new ClashCommands(d, this);
		if(!cr.checkClashRoyaleInstalled()){
			return "Clash Royale is not installed or cannot be found on this device.";
		}
		new ClashLauncher().execute();
		this.frmClashRoyaleBot.setLocationRelativeTo(null);
		this.frmClashRoyaleBot.setVisible(true);
		setStatus("Connected to " + device.toString() + ".");
		return "";
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClashRoyaleBot = new JFrame();
		frmClashRoyaleBot.setTitle("Clash Royale Bot");
		frmClashRoyaleBot.setResizable(false);
		frmClashRoyaleBot.setBounds(100, 100, 620, 161);
		frmClashRoyaleBot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClashRoyaleBot.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(28dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(81dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("178px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(82dlu;default)"),},
			new RowSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("33px"),
				RowSpec.decode("33px"),
				RowSpec.decode("33px"),
				RowSpec.decode("max(33px;default)"),}));
		
		openChestsCheckBox = new JCheckBox("Open Chests Between Battles");
		openChestsCheckBox.setSelected(true);
		frmClashRoyaleBot.getContentPane().add(openChestsCheckBox, "1, 2, 3, 1, fill, fill");
		
		play2v2CheckBox = new JCheckBox("Play 2v2 Quick Match");
		frmClashRoyaleBot.getContentPane().add(play2v2CheckBox, "5, 2, fill, fill");
		
		autoJoinCheckBox = new JCheckBox("Auto-Join Battle Requests");
		frmClashRoyaleBot.getContentPane().add(autoJoinCheckBox, "7, 2");
		
		JLabel numBattlesLabel = new JLabel("How Many Battles: ");
		frmClashRoyaleBot.getContentPane().add(numBattlesLabel, "1, 3, 3, 1, right, default");
		
		NumberFormat format = NumberFormat.getInstance();
	    format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(true);
	    formatter.setCommitsOnValidEdit(true);
		numTimesTextField = new JFormattedTextField(formatter);
		numTimesTextField.setText("1");
		numBattlesLabel.setLabelFor(numTimesTextField);
		frmClashRoyaleBot.getContentPane().add(numTimesTextField, "5, 3, 3, 1, fill, default");
		numTimesTextField.setColumns(10);
		
		manual2v2Button = new JButton("Wait For Manual 2v2 Battle");
		manual2v2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting manual 2v2 battle with selected options.");
						runningSwingWorker = new BattleLauncher(true);
						runningSwingWorker.execute();
					}else{
						JOptionPane.showMessageDialog(frmClashRoyaleBot, "Clash Royale app is not open. Please check device.");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmClashRoyaleBot, "An error has occurred. The error is: " + e1.getMessage());
					parent.setVisible(true);
					frmClashRoyaleBot.setVisible(false);
					e1.printStackTrace();
					
				}
			}
		});
		frmClashRoyaleBot.getContentPane().add(manual2v2Button, "5, 4, 2, 1, fill, fill");
		
		startButton = new JButton("Start Running");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting battle with selected options.");
						runningSwingWorker = new BattleLauncher(false);
						runningSwingWorker.execute();
					}else{
						JOptionPane.showMessageDialog(frmClashRoyaleBot, "Clash Royale app is not open. Please check device.");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmClashRoyaleBot, "An error has occurred. The error is: " + e1.getMessage());
					parent.setVisible(true);
					frmClashRoyaleBot.setVisible(false);
					e1.printStackTrace();
				}
			}
		});
		frmClashRoyaleBot.getContentPane().add(startButton, "7, 4, fill, fill");
		
		chestsOnlyButton = new JButton("Open Chests Only");
		chestsOnlyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting chest check.");
						runningSwingWorker = new BattleLauncher(true, 0, false, false, false);
						runningSwingWorker.execute();
					}else{
						JOptionPane.showMessageDialog(frmClashRoyaleBot, "Clash Royale app is not open. Please check device.");
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(frmClashRoyaleBot, "An error has occurred. The error is: " + e1.getMessage());
					parent.setVisible(true);
					frmClashRoyaleBot.setVisible(false);
				}
			}
		});
		frmClashRoyaleBot.getContentPane().add(chestsOnlyButton, "1, 4, 3, 1, fill, fill");
		
		statusLabel = new JLabel(" Status: ");
		frmClashRoyaleBot.getContentPane().add(statusLabel, "1, 5, fill, fill");
		
		statusTextLabel = new JLabel("Waiting...");
		frmClashRoyaleBot.getContentPane().add(statusTextLabel, "3, 5, 3, 1, fill, fill");
		
		cancelButton = new JButton("Cancel Task");
		cancelButton.setEnabled(false);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runningSwingWorker.cancel(true);
				setCancelable(false);
				setStatus("Connected to " + device.toString() + ".");
			}
		});
		frmClashRoyaleBot.getContentPane().add(cancelButton, "7, 5, fill, fill");
	}

}
class Marquee implements ActionListener {

    private static final int RATE = 12;
    private final Timer timer = new Timer(1000 / RATE, this);
    private final JLabel label;
    private final String s;
    private final int n;
    private int index;

    public Marquee(JLabel label, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append(' ');
        }
        this.label = label;
        this.s = sb + label.getText() + sb;
        this.n = n;
        label.setText(sb.toString());
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        index++;
        if (index > s.length() - n) {
            index = 0;
        }
        label.setText(s.substring(index, index + n));
    }
}
