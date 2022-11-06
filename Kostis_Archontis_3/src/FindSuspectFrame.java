import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindSuspectFrame extends JFrame{
	private JPanel mainPanel, searchPanel;
	private JButton findSuspectButton;
	private JLabel applicationText, mainText;
	private JTextField suspectSearchField;
	private ImageIcon icon = new ImageIcon("logo.png");
	private Registry registry;
	
	// Class Constructor
	public FindSuspectFrame(Registry aRegistry) {
		this.registry = aRegistry;
		
		// Create Search Panel
		this.searchPanel = new JPanel();
				
		// Create Main Panel
		this.mainPanel = new JPanel();
		
		// Graphic components for panels
		this.suspectSearchField = new JTextField("Please enter a Suspect's name");
		this.findSuspectButton = new JButton("Find");
		
		this.searchPanel.add(suspectSearchField);
		this.searchPanel.add(findSuspectButton);
		
		this.mainText = new JLabel("CRIME-NET");
		this.applicationText = new JLabel("Search Suspect Application");
		this.mainPanel.add(mainText);
		this.mainPanel.add(applicationText);
		this.mainPanel.add(searchPanel);

		// Set Main Panel Layout
		BoxLayout box = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
		this.mainPanel.setLayout(box);
		
		// Put panel on the window
		this.setContentPane(mainPanel);
		
		// Create an Event Listener Instance
		ButtonListener listener = new ButtonListener();
		this.findSuspectButton.addActionListener(listener);
		
		
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
			
			if(searchTerm.equals("Please enter a Suspect's name"))
				JOptionPane.showMessageDialog(mainPanel, "Please enter a Suspect's name" , "Message", JOptionPane.ERROR_MESSAGE);
			else {
				if(foundSuspect == null) {
					// Show Error Message Window
					JOptionPane.showMessageDialog(mainPanel, "Suspect: "+ searchTerm +" Not Found!!" , "Message", JOptionPane.WARNING_MESSAGE);
				}
				else
					new SuspectFrame(foundSuspect, registry);
			}
				
		}
		
	}

}
