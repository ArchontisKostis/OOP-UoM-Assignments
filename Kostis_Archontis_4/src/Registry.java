import java.util.ArrayList;

public class Registry {
	public ArrayList<Suspect> suspects;
	public ArrayList<Communication> communications;
	
	// Class constructor
	public Registry() {
		this.suspects = new ArrayList<Suspect>();
		this.communications = new ArrayList<Communication>();
	}
	
	/* Input: a Suspect
	 * Operation: Adds the Suspect to the suspects list */
	public void addSuspect(Suspect aSuspect) {
		suspects.add(aSuspect);
	}
	
	/* Input: a Communication
	 * Operation: Adds the Communication to the communication list & updates the suspects partners if needed */
	public void addCommunication(Communication aCommunication) {
		communications.add(aCommunication);
		
		// Update Suspect Partners
		String num1 = aCommunication.callerNumber;
		String num2 = aCommunication.receiverNumber;
		
		Suspect caller = getPhoneNumberUser(num1);
		Suspect receiver = getPhoneNumberUser(num2);
		
		caller.addPartner(receiver);
		receiver.addPartner(caller);
	}
	
	// Returns the Suspect with most partners
	public Suspect getSuspectWithMostPartners() {
		Suspect max = suspects.get(0);
		
		for(Suspect suspect: suspects) {
			if( suspect.getNumberOfPartners() >= max.getNumberOfPartners() )
				max = suspect;
		}
		return max;
	}
	
	/* Input: 2 Phone Numbers
	 * Operation: Finds the longest call between the two numbers
	 * Returns: The Longest Phone Call */
	public PhoneCall  getLongestPhoneCallBetween(String number1, String number2) {
		ArrayList<PhoneCall> phoneCalls =  getOnlyPhoneCalls();
		PhoneCall max = phoneCalls.get(0);
		
		for(PhoneCall call: phoneCalls) {
			if(call.callerNumber.equals(number1) && call.receiverNumber.equals(number2)) {
				if(call.duration >= max.duration)
					max = call;
			}
		}
		return max;
	}
	
	/* Input: 2 Phone Numbers
	 * Operation: Finds all the suspicious messages (messages including the words “Bomb”, “Attack”, “Explosives”, “Gun”) 
	 * Returns: All the suspicious messages */
	public ArrayList<SMS> getMessagesBetween(String number1, String number2) {
		ArrayList<SMS> smsList = getOnlySMS();
		ArrayList<SMS> suspiciousMessages = new ArrayList<SMS>();
		
		for(SMS sms: smsList) {
			if(sms.callerNumber.equals(number1) && sms.receiverNumber.equals(number2)) {
				String[] words = sms.message.split(" ");
				
				for(String word: words) {
					if(word.equals("Bomb") || word.equals("Attack") || word.equals("Explosives") || word.equals("Gun"))
						suspiciousMessages.add(sms);
				}
			}
		}
		return suspiciousMessages;
	}
	
	/* Input: 1 Phone Number
	 * Operation: Finds the user of the phone number
	 * Output: The User of the number */
	public Suspect getPhoneNumberUser(String aPhoneNumber) {
		for(Suspect suspect: suspects) {
			Suspect currSuspect = suspect;
			
			for(int i=0; i<currSuspect.getPhoneListSize(); i++) {
				String flagNumber = currSuspect.getPhoneNumber(i);
				if(flagNumber.equals(aPhoneNumber))
					return currSuspect;
			}
		}
		return null;	// Lets hope that this will not mess everything up! If everything is ok it will never run.
	}
	
	// Takes the communication list and returns a copy of the list with only the PHONE CALLS
	public ArrayList<PhoneCall> getOnlyPhoneCalls(){
		ArrayList<PhoneCall> phoneCallsList = new ArrayList<PhoneCall>();
		
		for(Communication communication: communications) {
			if(communication instanceof PhoneCall)
				phoneCallsList.add((PhoneCall) communication);
		}
		return phoneCallsList;
	}
	
	// Takes The communication List and returns a copy of the list with only the SMS
	public ArrayList<SMS> getOnlySMS(){
		ArrayList<SMS> smsList = new ArrayList<SMS>();
		
		for(Communication communication: communications) {
			if(communication instanceof SMS)
				smsList.add((SMS) communication);
		}
		return smsList;
	}
	
	public Suspect findSuspect(String aSuspectName) {
		for(Suspect suspect: suspects) {
			String currSuspectName = suspect.getName() ;
			if(aSuspectName.equalsIgnoreCase(currSuspectName)) {
				return suspect;
			}
		}
		return null;	// This line will execute only if the suspect is not found
	}
}
