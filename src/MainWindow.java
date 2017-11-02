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
	    	setCancelable(true);
	        for(int i = 0; i < times; i++){
	        	if(this.isCancelled()) break;
	        	if(openChests) cr.openChests();
	        	if(this.isCancelled()) break;
	        	cr.startBattle(twoVtwo, manual);
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
		if(text.length() > 40){
			marquee = new Marquee(statusTextLabel, 32);
			marquee.start();
		}
	}
	
	public void setCancelable(boolean cancelable){
		cancelButton.setEnabled(cancelable);
		manual2v2Button.setEnabled(!cancelable);
		startButton.setEnabled(!cancelable);
	}
	
	public String start(JadbDevice d) throws IOException, JadbException{
		this.device = d;
		cr = new ClashCommands(d, this);
		setStatus("Connected to " + device.toString() + ".");
		if(!cr.checkClashRoyaleInstalled()){
			return "Clash Royale is not installed or cannot be found on this device.";
		}
		new ClashLauncher().execute();
		this.frmClashRoyaleBot.setLocationRelativeTo(null);
		this.frmClashRoyaleBot.setVisible(true);
		return "";
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClashRoyaleBot = new JFrame();
		frmClashRoyaleBot.setTitle("Clash Royale Bot");
		frmClashRoyaleBot.setResizable(false);
		frmClashRoyaleBot.setBounds(100, 100, 580, 165);
		frmClashRoyaleBot.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClashRoyaleBot.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("max(28dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(112dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("178px:grow"),},
			new RowSpec[] {
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("33px"),
				RowSpec.decode("33px"),
				RowSpec.decode("33px"),
				RowSpec.decode("33px"),}));
		
		openChestsCheckBox = new JCheckBox("Open Chests When Available");
		openChestsCheckBox.setSelected(true);
		frmClashRoyaleBot.getContentPane().add(openChestsCheckBox, "1, 2, 3, 1, fill, fill");
		
		play2v2CheckBox = new JCheckBox("Play 2v2 Quick Match");
		frmClashRoyaleBot.getContentPane().add(play2v2CheckBox, "5, 2, fill, fill");
		
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
		frmClashRoyaleBot.getContentPane().add(numTimesTextField, "5, 3, fill, default");
		numTimesTextField.setColumns(10);
		
		manual2v2Button = new JButton("Wait For Manual 2v2 Battle To Start");
		manual2v2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting manual 2v2 battle with selected options.");
						runningSwingWorker = new BattleLauncher(openChestsCheckBox.isSelected(),Integer.parseInt(numTimesTextField.getText()),true, true);
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
		frmClashRoyaleBot.getContentPane().add(manual2v2Button, "1, 4, 3, 1, fill, fill");
		
		startButton = new JButton("Start Running");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting battle with selected options.");
						runningSwingWorker = new BattleLauncher(openChestsCheckBox.isSelected(),Integer.parseInt(numTimesTextField.getText()),play2v2CheckBox.isSelected(), false);
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
		frmClashRoyaleBot.getContentPane().add(startButton, "5, 4, fill, fill");
		
		cancelButton = new JButton("Cancel Task");
		cancelButton.setEnabled(false);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runningSwingWorker.cancel(true);
				setCancelable(false);
				setStatus("Connected to " + device.toString() + ".");
			}
		});
		
		statusLabel = new JLabel(" Status: ");
		frmClashRoyaleBot.getContentPane().add(statusLabel, "1, 5, fill, fill");
		
		statusTextLabel = new JLabel("Waiting...");
		frmClashRoyaleBot.getContentPane().add(statusTextLabel, "3, 5, fill, fill");
		frmClashRoyaleBot.getContentPane().add(cancelButton, "5, 5, fill, fill");
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
