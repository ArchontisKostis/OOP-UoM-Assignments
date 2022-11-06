import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;


public class SuspectFrame extends JFrame{
	private final static String NEW_LINE = "\n";
	
	private Suspect suspect;
	private Registry registry;
	private JList phones;
	private JTextArea partners;
	private JTextArea messages, possiblePartners;
	
	private ArrayList<String> suspectPhones, suspectPartners, possibleSuspectPartners;
	
	private JPanel suspectInfoPanel, messageInfoPanel, 
				   partnersPanel, centralPanel, possiblePartnersPanel,
				   buttonPanel;
	
	private JTextField suspectNameField, suspectCodeNameField, phoneTextField;
	private JButton findMessagesButton, backToSearchButton;
	
	private ImageIcon icon = new ImageIcon("logo.png");
	
	// Class Constructor
	public SuspectFrame(Suspect aSuspect, Registry aRegistry) {
		this.suspect = aSuspect;
		this.registry = aRegistry;
		this.suspectPhones = suspect.getPhoneNumbersList();
		this.suspectPartners = new ArrayList<String>();
		this.possibleSuspectPartners = new ArrayList<String>();
		
		// Add Partners names in suspectPartners List and sort them alphabetically
		for(Suspect suspect: suspect.getPartnersList())
			suspectPartners.add(suspect.getName() + ", " + suspect.getCodeName());
		
		Collections.sort(suspectPartners);
		
		/* SUSPECT INFO PANEL */
		// Create Suspect info panel
		this.suspectInfoPanel = new JPanel();
		this.suspectInfoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Create Graphic Components for Suspect Info Panel
		this.suspectNameField = new JTextField(suspect.getName(), 10);
		this.suspectNameField.setEditable(false);
		this.suspectNameField.setBackground(Color.WHITE);
		
		this.suspectCodeNameField = new JTextField(suspect.getCodeName(), 10);
		this.suspectCodeNameField.setEditable(false);
		this.suspectCodeNameField.setBackground(Color.WHITE);
		
		this.phones = new JList();
		
		// Create a ListModel for JList
		DefaultListModel phoneModel = new DefaultListModel();
		
		// add elements to list model
		for(String phoneNumber: suspectPhones) {
			phoneModel.addElement(phoneNumber);
		}
		
		// Add model to JList
		phones.setModel(phoneModel);
		
		// Add Graphic Componets to Suspect Info Panel
		this.suspectInfoPanel.add(this.suspectNameField);
		this.suspectInfoPanel.add(this.suspectCodeNameField);
		this.suspectInfoPanel.add(this.phones);
		
		/* MESSAGE INFO PANEL */
		// Create Panel
		this.messageInfoPanel = new JPanel();
		this.messageInfoPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Create Graphic Components
		this.phoneTextField = new JTextField("Phone Number", 10);
		this.messages = new JTextArea(10, 20);
		this.messages.setEditable(false);
		this.findMessagesButton = new JButton("Find SMS");
		
		
		// Add Graphic Componets to Message Info Panel
		messageInfoPanel.add(this.phoneTextField);
		messageInfoPanel.add(this.messages);
		messageInfoPanel.add(this.findMessagesButton);
		
		/* PARTNERS PANEL */
		// Create Partners Panel
		this.partnersPanel = new JPanel();
		this.partnersPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		// Create a new LayoutManager for this panel and add it to panel
		BoxLayout partnerBox = new BoxLayout(partnersPanel, BoxLayout.Y_AXIS);
		this.partnersPanel.setLayout(partnerBox);
		
		// Create Graphic Components
		JLabel partnersLabel = new JLabel("Partners\n");
		partnersLabel.setFont( new Font("Sans Serif", Font.BOLD, 36) );
		this.partners = new JTextArea(10, 20);
		this.partners.setEditable(false);
		
		// Write Partners to TextArea
		for(String partner: this.suspectPartners) {
			partners.append(partner + NEW_LINE);
		}
		
		// Add components to panel
		this.partnersPanel.add(partnersLabel);
		this.partnersPanel.add(this.partners);
		
		/* POSSIBLE PARTNERS PANEL */
		// Panel Creation
		this.possiblePartnersPanel = new JPanel();
		
		// Create Graphic Components
		JLabel possiblePartnersLabel = new JLabel("Possible Partners");
		possiblePartnersLabel.setFont( new Font("Sans Serif", Font.BOLD, 36) );
		
		this.possiblePartners = new JTextArea(10, 20);
		this.possiblePartners.setEditable(false);
		this.possiblePartners.setText(" ");
		
		// Set the panels layout manager
		BoxLayout PossiblePartnerBox = new BoxLayout(possiblePartnersPanel, BoxLayout.Y_AXIS);
		this.possiblePartnersPanel.setLayout(PossiblePartnerBox);
		
		// Find possible partners put them in a sorted list
		ArrayList<Suspect> suggestedSuspects = this.suspect.findSuggestedSuspects();
		
		for(Suspect possiblePartner: suggestedSuspects) {
			this.possibleSuspectPartners.add(possiblePartner.toString());
		}
		Collections.sort(this.possibleSuspectPartners);
		
		for(String possiblePartner: this.possibleSuspectPartners) {
			possiblePartners.append(possiblePartner+this.NEW_LINE);
		}
		
		// Put a message on TextArea if possiblePartners do not exist
		if(possiblePartners.getText().trim().equals(""))
			possiblePartners.append("NO POSSIBLE PARTNERS FOUND!");
		
		
		// Place Components on panel
		this.possiblePartnersPanel.add(possiblePartnersLabel);
		this.possiblePartnersPanel.add(this.possiblePartners);
		
		/* BUTTON PANEL */
		// Panel Creation
		this.buttonPanel = new JPanel();
		// Button Component Creation
		this.backToSearchButton = new JButton("Back to Search Screen");
		// Add component to panel
		this.buttonPanel.add(this.backToSearchButton);
		
		/* CENTRAL PANEL */
		// Create Central Panel
		this.centralPanel = new JPanel();
				
		// Set Central Panel's Layout Manager
		BoxLayout centralBox = new BoxLayout(centralPanel, BoxLayout.Y_AXIS);
		centralPanel.setLayout(centralBox);
		
		// Put all the panels to the central panel
		centralPanel.add(suspectInfoPanel);
		centralPanel.add(messageInfoPanel);
		centralPanel.add(partnersPanel);
		centralPanel.add(possiblePartnersPanel);
		centralPanel.add(buttonPanel);
				
		this.setContentPane(this.centralPanel);
		
		// Add Action Listener
		ButtonListener listener = new ButtonListener();
		this.findMessagesButton.addActionListener(listener);
		this.backToSearchButton.addActionListener(listener);
		
		// Create Window
		this.setResizable(false);
		this.setVisible(true);
		this.setSize(500, 800);
		this.setTitle("Suspect Page");
		this.setIconImage(icon.getImage());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	// EVENT LISTENER
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == findMessagesButton) {
				String phoneSearchText = phoneTextField.getText();
				messages.setText(""); 	// Delete all messages bc if message button is pressed multiple times all existing messages show again in the text area
				
				if(phoneSearchText.equals("Phone Number") || phoneSearchText.equals("")) {
					JOptionPane.showMessageDialog(centralPanel, "Please enter a Phone Number!!" , "Message", JOptionPane.WARNING_MESSAGE);
				}
				else {
					// Search for every suspicious messages between all phone numbers used by the suspect an the input phone number
					for(String phone: suspectPhones) {
						ArrayList<SMS> SuspiciousMessages = registry.getMessagesBetween(phone, phoneSearchText);
					
						for(SMS message: SuspiciousMessages)
							messages.append(message.getMessage() + NEW_LINE);
					}
					
					String data = messages.getText().trim();
					if(data.equals("")) {
						JOptionPane.showMessageDialog(centralPanel, "No Suspicious Messages Found!!" , "Message", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
			else {
				SuspectFrame.this.dispose();
			}
			
		}
		
	}
}
