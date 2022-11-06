import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindSuspectFrame extends JFrame{
	private JPanel mainPanel, searchPanel, networkButtonPanel;
	private JButton findSuspectButton, visualizeNetworkButton;
	private JLabel applicationText, mainText;
	private JTextField suspectSearchField;
	private ImageIcon icon = new ImageIcon("logo.png");
	private Registry registry;
	
	private JFrame netFrame;
	
	// Class Constructor
	public FindSuspectFrame(Registry aRegistry) {
		this.registry = aRegistry;
		this.netFrame = null;
		
		// Create Search Panel
		this.searchPanel = new JPanel();
				
		// Create Main Panel
		this.mainPanel = new JPanel();
		
		// Create Network Button Panel
		this.networkButtonPanel = new JPanel();
		
		// Graphic components for panels
		this.suspectSearchField = new JTextField("Please enter a Suspect's name");
		this.findSuspectButton = new JButton("Find");
		this.visualizeNetworkButton = new JButton("Visualize Network");
		
		this.searchPanel.add(suspectSearchField);
		this.searchPanel.add(findSuspectButton);
		
		this.networkButtonPanel.add(visualizeNetworkButton, BorderLayout.CENTER);
		
		this.mainText = new JLabel("CRIME-NET");
		this.applicationText = new JLabel("Search Suspect Application (Version 3.0)");
		this.mainPanel.add(mainText);
		this.mainPanel.add(applicationText);
		this.mainPanel.add(searchPanel);
		this.mainPanel.add(networkButtonPanel);

		// Set Main Panel Layout
		BoxLayout box = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		this.mainPanel.setLayout(box);
		
		// Put panel on the window
		this.setContentPane(mainPanel);
		
		// Create an Event Listener Instance
		ButtonListener listener = new ButtonListener();
		this.findSuspectButton.addActionListener(listener);
		this.visualizeNetworkButton.addActionListener(listener);

		// Create Window
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(400, 150);
		this.setTitle("Find Suspect");
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// Event Listener
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String searchTerm = suspectSearchField.getText();
			Suspect foundSuspect = registry.findSuspect(searchTerm);
			
			if(e.getSource() == findSuspectButton) {
				
				if(searchTerm.equals("Please enter a Suspect's name"))
					JOptionPane.showMessageDialog(mainPanel, "Please enter a Suspect's name" , "Message", JOptionPane.ERROR_MESSAGE);
				else {
					if(foundSuspect == null)
						JOptionPane.showMessageDialog(mainPanel, "Suspect: "+ searchTerm +" Not Found!!" , "Message", JOptionPane.WARNING_MESSAGE);
					else
						new SuspectFrame(foundSuspect, registry);
				}
			}
			else {
				// Create network window if it has not been created
				if(netFrame == null)
					netFrame = new NetworkFrame(registry);
				
				// Check if the window has opened and either open the window or show a message to the user that the window has opened
				if(!netFrame.isVisible())
					netFrame.setVisible(true);
				else
					JOptionPane.showMessageDialog(mainPanel, "The Window: \"Suspects Network\" is already open! " , "Message", JOptionPane.WARNING_MESSAGE);
			}	
		}
	}
	
}
