public class Communication {
	public String callerNumber;
	public String receiverNumber;
	public int day;
	public int month;
	public int year;
	
	// Class Constructor
	public Communication(String caller, String receiver, int aDay, int aMonth, int aYear) {
		this.callerNumber = caller;
		this.receiverNumber = receiver;
		this.day = aDay;
		this.month = aMonth;
		this.year = aYear;
	}
	
	public void printInfo() {
		System.out.println("Caller: " + this.callerNumber);
		System.out.println("Receiver: " + this.receiverNumber);
		System.out.println(this.day + "/" + this.month + "/" + this.year);
	}
}
