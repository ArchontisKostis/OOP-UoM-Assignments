
public class PhoneCall extends Communication {
	int duration;
	
	// Constructor
	public PhoneCall(String caller, String receiver, int aDay, int aMonth, int aYear, int aDuration) {
		super(caller, receiver, aDay, aMonth, aYear);
		this.duration = aDuration;
	}
	
	public void printInfo() {
		System.out.println("The phone call has the following Info: ");
		System.out.println("Caller: " + this.callerNumber);
		System.out.println("Receiver: " + this.receiverNumber);
		System.out.println("Date: " + this.day + "/" + this.month + "/" + this.year);
		System.out.println("Duration: " + this.duration + "\n");
	}
	
	
}
