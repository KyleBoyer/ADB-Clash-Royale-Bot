import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JSeparator;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;
import javax.swing.JFormattedTextField;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConnectionWindow {

	private JFrame frmConnectToAdb;
	private JFormattedTextField portTextField;
	private JComboBox<JadbDevice> deviceComboBox;
	private JadbConnection jadb;
	private JTextField ipAddrTextField;
	private JButton connectButton;

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionWindow window = new ConnectionWindow();
					window.frmConnectToAdb.setLocationRelativeTo(null);
					window.frmConnectToAdb.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ConnectionWindow() {
		initialize();
	}

	private void initialize() {
		Utils.run("adb start-server");
		jadb = new JadbConnection();
		frmConnectToAdb = new JFrame();
		frmConnectToAdb.setTitle("Connect to ADB Device - Clash Royale Bot");
		frmConnectToAdb.setResizable(false);
		frmConnectToAdb.setBounds(100, 100, 500, 110);
		frmConnectToAdb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConnectToAdb.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("40px"),
				ColumnSpec.decode("127px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("115px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(30dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("91px"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("29px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel connectLabel = new JLabel("Connect to TCP Device: ");
		frmConnectToAdb.getContentPane().add(connectLabel, "1, 2, 2, 1, right, default");
		
		ipAddrTextField = new JTextField();
		connectLabel.setLabelFor(ipAddrTextField);
		ipAddrTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					connectButton.doClick();
				}
			}
		});
		ipAddrTextField.setToolTipText("Enter a hostname or IP Address");
		frmConnectToAdb.getContentPane().add(ipAddrTextField, "4, 2, fill, default");
		ipAddrTextField.setColumns(10);
		
		JLabel portLabel = new JLabel("Port:");
		frmConnectToAdb.getContentPane().add(portLabel, "6, 2, right, default");
		NumberFormat format = NumberFormat.getInstance();
	    format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
		portTextField = new JFormattedTextField(formatter);
		portLabel.setLabelFor(portTextField);
		portTextField.setText("5555");
		frmConnectToAdb.getContentPane().add(portTextField, "8, 2, fill, default");
		portTextField.setColumns(10);
		
		connectButton = new JButton("Connect");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InetSocketAddress inetSocketAddress = new InetSocketAddress(ipAddrTextField.getText(),Integer.parseInt(portTextField.getText()));
				try{
					jadb.connectToTcpDevice(inetSocketAddress);
					System.out.println("Successfully connected to device at '" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "'. It has been added to the list if accessible.");
					ipAddrTextField.setText("");
					refreshDeviceList();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frmConnectToAdb, "Cannot connect to device at '" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "'.");
					System.out.println("Cannot connect to device at '" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "'.");
				}
			}
		});
		frmConnectToAdb.getContentPane().add(connectButton, "10, 2");
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		frmConnectToAdb.getContentPane().add(separator, "1, 4, 10, 1");
		
		JLabel chooseLabel = new JLabel("Choose Device: ");
		chooseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frmConnectToAdb.getContentPane().add(chooseLabel, "2, 6, left, center");
		
		deviceComboBox = new JComboBox<JadbDevice>();
		deviceComboBox.setToolTipText("If nothing is here, ensure that a device is connect via USB or by TCP(above) and that ADB is started.");
		deviceComboBox.setMaximumRowCount(5);
		chooseLabel.setLabelFor(deviceComboBox);
		frmConnectToAdb.getContentPane().add(deviceComboBox, "4, 6, left, center");
		
		JButton chooseButton = new JButton("Choose");
		chooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(deviceComboBox.getSelectedItem() != null){
					MainWindow mw = new MainWindow(frmConnectToAdb);
					try {
						String startMW = mw.start((JadbDevice) deviceComboBox.getSelectedItem());
						if(startMW.isEmpty()){
							frmConnectToAdb.setVisible(false);
						}else{
							JOptionPane.showMessageDialog(frmConnectToAdb, startMW);
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(frmConnectToAdb, "An error has occurred. The error is: " + e1.getMessage());
						e1.printStackTrace();
					}
					
				}else{
					JOptionPane.showMessageDialog(frmConnectToAdb, "Please choose a device from the list!");
				}
			}
		});
		
		JButton refreshButton = new JButton("Refresh List");
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshDeviceList();
			}
		});
		frmConnectToAdb.getContentPane().add(refreshButton, "6, 6, 3, 1");
		frmConnectToAdb.getContentPane().add(chooseButton, "10, 6, left, top");
		refreshDeviceList();
	}
	
	private void refreshDeviceList(){
		JadbDevice d = (JadbDevice) deviceComboBox.getSelectedItem();
		try {
			deviceComboBox.removeAllItems();
			for(JadbDevice addD : jadb.getDevices()){
				deviceComboBox.addItem(addD);
				if(d != null && d.toString().equals(addD.toString())){
					deviceComboBox.setSelectedItem(addD);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
