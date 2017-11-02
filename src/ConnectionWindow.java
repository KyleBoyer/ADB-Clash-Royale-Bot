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

public class ConnectionWindow {

	private JFrame frame;
	private JFormattedTextField textField;
	private JComboBox<JadbDevice> comboBox;
	private JadbConnection jadb;
	private JTextField textField_1;

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionWindow window = new ConnectionWindow();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 110);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
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
		
		JLabel lblConnectToTcp = new JLabel("Connect to TCP Device: ");
		frame.getContentPane().add(lblConnectToTcp, "1, 2, 2, 1, right, default");
		
		textField_1 = new JTextField();
		textField_1.setToolTipText("Enter a hostname or IP Address");
		frame.getContentPane().add(textField_1, "4, 2, fill, default");
		textField_1.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
		frame.getContentPane().add(lblPort, "6, 2, right, default");
		NumberFormat format = NumberFormat.getInstance();
	    format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    formatter.setCommitsOnValidEdit(true);
		textField = new JFormattedTextField(formatter);
		textField.setText("5555");
		frame.getContentPane().add(textField, "8, 2, fill, default");
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InetSocketAddress inetSocketAddress = new InetSocketAddress(textField_1.getText(),Integer.parseInt(textField.getText()));
				try{
					jadb.connectToTcpDevice(inetSocketAddress);
					System.out.println("Successfully connected to device at '" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "'. It has been added to the list if accessible.");
					refreshDeviceList();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Cannot connect to device at '" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "'.");
					System.out.println("Cannot connect to device at '" + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() + "'.");
				}
			}
		});
		frame.getContentPane().add(btnNewButton, "10, 2");
		
		JSeparator separator = new JSeparator();
		separator.setBackground(Color.GRAY);
		frame.getContentPane().add(separator, "1, 4, 10, 1");
		
		JLabel lblChooseDevice = new JLabel("Choose Device: ");
		lblChooseDevice.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblChooseDevice, "2, 6, left, center");
		
		comboBox = new JComboBox<JadbDevice>();
		comboBox.setToolTipText("If nothing is here, ensure that a device is connect via USB or by TCP(above) and that ADB is started.");
		comboBox.setMaximumRowCount(5);
		lblChooseDevice.setLabelFor(comboBox);
		frame.getContentPane().add(comboBox, "4, 6, left, center");
		
		JButton btnChoose = new JButton("Choose");
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comboBox.getSelectedItem() != null){
					MainWindow mw = new MainWindow(frame);
					try {
						String startMW = mw.start((JadbDevice) comboBox.getSelectedItem());
						if(startMW.isEmpty()){
							frame.setVisible(false);
						}else{
							JOptionPane.showMessageDialog(frame, startMW);
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(frame, "An error has occurred. The error is: " + e1.getMessage());
						e1.printStackTrace();
					}
					
				}else{
					JOptionPane.showMessageDialog(frame, "Please choose a device from the list!");
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Refresh List");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshDeviceList();
			}
		});
		frame.getContentPane().add(btnNewButton_1, "6, 6, 3, 1");
		frame.getContentPane().add(btnChoose, "10, 6, left, top");
		refreshDeviceList();
	}
	
	private void refreshDeviceList(){
		JadbDevice d = (JadbDevice) comboBox.getSelectedItem();
		try {
			comboBox.removeAllItems();
			for(JadbDevice addD : jadb.getDevices()){
				comboBox.addItem(addD);
				if(d != null && d.toString().equals(addD.toString())){
					comboBox.setSelectedItem(addD);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
