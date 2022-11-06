
public class SMS extends Communication{
	public String message;

	// Class Constructor
	public SMS(String caller, String receiver, int aDay, int aMonth, int aYear, String message) {
		super(caller, receiver, aDay, aMonth, aYear);
		this.message = message;
	}
	
	public void printInfo() {
		System.out.println("The SMS has the following Info:");
		System.out.println("Caller: " + this.callerNumber);
		System.out.println("Receiver: " + this.receiverNumber);
		System.out.println("Date: " + this.day + "/" + this.month + "/" + this.year);
		System.out.println("Message: " + this.message + "\n");
	}
}
