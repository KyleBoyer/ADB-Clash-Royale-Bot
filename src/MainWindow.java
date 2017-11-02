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

	private JFrame frame;
	private JFormattedTextField textField;
	private JCheckBox chckbxOpenChestsWhen;
	private JCheckBox chckbxPlayvQuick;
	private JLabel lblStatus;
	private ClashCommands cr;
	private JFrame parent;
	private Marquee marquee;
	private JButton btnCancelTask;
	private JButton manual2v2Button;
	private JButton startButton;
	private BattleLauncher runningSwingWorker;
	private JLabel lblWaiting;
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
		lblWaiting.setText(text);
		if(text.length() > 40){
			marquee = new Marquee(lblWaiting, 32);
			marquee.start();
		}
	}
	
	public void setCancelable(boolean cancelable){
		btnCancelTask.setEnabled(cancelable);
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
		this.frame.setLocationRelativeTo(null);
		this.frame.setVisible(true);
		return "";
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 580, 165);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
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
		
		chckbxOpenChestsWhen = new JCheckBox("Open Chests When Available");
		chckbxOpenChestsWhen.setSelected(true);
		frame.getContentPane().add(chckbxOpenChestsWhen, "1, 2, 3, 1, fill, fill");
		
		chckbxPlayvQuick = new JCheckBox("Play 2v2 Quick Match");
		frame.getContentPane().add(chckbxPlayvQuick, "5, 2, fill, fill");
		
		JLabel lblHowManyBattles = new JLabel("How Many Battles: ");
		frame.getContentPane().add(lblHowManyBattles, "1, 3, 3, 1, right, default");
		
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
		frame.getContentPane().add(textField, "5, 3, fill, default");
		textField.setColumns(10);
		
		manual2v2Button = new JButton("Wait For Manual 2v2 Battle To Start");
		manual2v2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting manual 2v2 battle with selected options.");
						runningSwingWorker = new BattleLauncher(chckbxOpenChestsWhen.isSelected(),Integer.parseInt(textField.getText()),true, true);
						runningSwingWorker.execute();
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
		frame.getContentPane().add(manual2v2Button, "1, 4, 3, 1, fill, fill");
		
		startButton = new JButton("Start Running");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(cr.isAppOpen()){
						setStatus("Starting battle with selected options.");
						runningSwingWorker = new BattleLauncher(chckbxOpenChestsWhen.isSelected(),Integer.parseInt(textField.getText()),chckbxPlayvQuick.isSelected(), false);
						runningSwingWorker.execute();
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
		frame.getContentPane().add(startButton, "5, 4, fill, fill");
		
		btnCancelTask = new JButton("Cancel Task");
		btnCancelTask.setEnabled(false);
		btnCancelTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runningSwingWorker.cancel(true);
				setCancelable(false);
				setStatus("Connected to " + device.toString() + ".");
			}
		});
		
		lblStatus = new JLabel(" Status: ");
		frame.getContentPane().add(lblStatus, "1, 5, fill, fill");
		
		lblWaiting = new JLabel("Waiting...");
		frame.getContentPane().add(lblWaiting, "3, 5, fill, fill");
		frame.getContentPane().add(btnCancelTask, "5, 5, fill, fill");
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
