import java.util.ArrayList;

public class Suspect {
	private String fullName;
	private String userName;
	private String city;
	private ArrayList<String> phoneNumbers;
	private ArrayList<Suspect> possiblePartners;
	
	// Class Constructor
	public Suspect(String aName, String aUsername, String aCity) {
		this.fullName = aName;
		this.userName = aUsername;
		this.city = aCity;
		this.phoneNumbers = new ArrayList<String>();
		this.possiblePartners = new ArrayList<Suspect>();
	}
	
	// Getters
	public String getName() {
		return this.fullName;
	}
	
	public String getCodeName() {
		return this.userName;
	}
	
	public String getPhoneNumber(int index) {
		return this.phoneNumbers.get(index);
	}
	
	/* Input: a Phone Number
	 * Operation: Adds the phone number to the phone number List of the Suspect */
	public void addNumber(String aPhoneNumber) {
		this.phoneNumbers.add(aPhoneNumber); 
	}
	
	/* Input: a Suspect
	 * Operation: Adds the Suspect to the possible partners List of the Suspect */
	public void addPartner(Suspect aSuspect) {
		if(!isConnectedTo(aSuspect))
			this.possiblePartners.add(aSuspect);
	}
	
	/* Input: a Suspect
	 * Operation: Checks if the Suspect exists in the possiblePartners List
	 * Output: True if the Suspects exists | False if does not exist */
	public boolean isConnectedTo(Suspect aSuspect) {
		for(Suspect partner: possiblePartners) {
			if(partner.userName.equals(aSuspect.userName))
				return true;
		}
		return false;
	}
	
	/* Input: a Suspect
	 * Operation: Gets all the common partners between the current suspect and the parameter suspect
	 * Output: a List with the common Parters */
	public ArrayList<Suspect> getCommonPartners(Suspect aSuspect) {
		ArrayList<Suspect> commonPartners = new ArrayList<Suspect>();
		
		for( int i=0; i<aSuspect.possiblePartners.size(); i++ ) {
			String thisSuspectPartner = this.possiblePartners.get(i).userName;			// Current Suspect Partner
			String otherSuspectPartner = aSuspect.possiblePartners.get(i).userName;		// The other Suspect Partner (aSuspect)
			
			if( thisSuspectPartner.equals(otherSuspectPartner)) {
				commonPartners.add(this.possiblePartners.get(i));
			}
		}
		return commonPartners;
	}
	
	// Prints The possible partners
	public void printPartners() {
		System.out.println("---------Possible Partners of " + this.userName + "---------");
		for(Suspect partner: possiblePartners) {
			System.out.println(partner.fullName + ", " + partner.userName);
		}
		System.out.println("--------------------------------------------------\n");
	}
	
	public int getNumberOfPartners() {
		return this.possiblePartners.size();
	}
	
	// Returns the size of the phoneNumbers list
	public int getPhoneListSize() {
		return this.phoneNumbers.size();
	}
}
