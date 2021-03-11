import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class UI {

	private String name;
	private DefaultTableModel tableModel;
	private JComboBox<String> recipientPicker;
	
	public UI(String name, ChatClient client) {
		
		this.name = name;
		
		JFrame frame = new JFrame(name);
		JPanel panel = new JPanel(new BorderLayout());
		
		tableModel = new DefaultTableModel();
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		
		JPanel controlsPanel = new JPanel();
		JTextField textField = new JTextField(30);
		JCheckBox encryptionCheckBox = new JCheckBox("Encrypt", true);
		recipientPicker = new JComboBox<>();
		JButton sendButton = new JButton("Send");
		
		tableModel.addColumn("Time");
		tableModel.addColumn("Sender");
		tableModel.addColumn("Recipient");
		tableModel.addColumn("Body");
		
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(3).setPreferredWidth(500);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String recipient = recipientPicker.getSelectedItem().toString();
				String body = textField.getText();
				Boolean encrypted = encryptionCheckBox.isSelected();
				client.sendMessage(recipient, body, encrypted);
				textField.setText("");
			}
		});
		
		controlsPanel.add(textField);
		controlsPanel.add(recipientPicker);
		controlsPanel.add(encryptionCheckBox);
		controlsPanel.add(sendButton);
		
		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(controlsPanel, BorderLayout.SOUTH);
		
		frame.getContentPane().add(panel);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.setSize(900, 500);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();
		
	}
	
	public void refresh(ArrayList<String> clients, TreeMap<String, String[]> messages) {
		
		if (messages != null) {
	        
	        tableModel.setRowCount(0);
	        
		    for (Entry<String, String[]> messageEntry: messages.entrySet()) {

		        if (messageEntry.getKey().equals("null")) continue;

		        long milis = Long.valueOf(messageEntry.getKey().split("_")[0]) * 1000;

		        String time = (new SimpleDateFormat("HH:mm:ss")).format(new Date(milis));
		        String sender = messageEntry.getValue()[0];
		        String recipient = messageEntry.getValue()[1];
		        String body = messageEntry.getValue()[2];

		        boolean isSender = messageEntry.getValue()[0].equals(name);
		        boolean isRecipient = messageEntry.getValue()[1].equals(name);

		        if (!isSender && !isRecipient) {
		            time = "<html><body style='color: gray;'>" + time + "</body></html>";
		            sender = "<html><body style='color: gray;'>" + sender + "</body></html>";
		            recipient = "<html><body style='color: gray;'>" + recipient + "</body></html>";
		            body = "<html><body style='color: gray;'>" + body + "</body></html>";
		        }

		        tableModel.addRow(new String[]{time, sender, recipient, body});

		    }
		    
		}
		
		recipientPicker.removeAllItems();
		
		for (String client: clients) {
			recipientPicker.addItem(client);
		}
		
	}
	
}
