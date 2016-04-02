package gov.nist.sip.proxy;

public abstract class BillingProgram {

	// This class holds any temporary data
	// needed for the calculation of a bill.
	public abstract class Cookie {
		public abstract long getTotalCost();
	}

	public static int numPrograms() {
		return 3;
	}
	
	public static BillingProgram factory(int program_number){
		switch(program_number){
			case 1:
				return BillingProgram1.getInstance();
			case 2:
				return BillingProgram2.getInstance();
			case 3:
				return BillingProgram3.getInstance();
			default:
				System.out.println("Invalid billing program ID");
				return null;
		}
	}
	
	public abstract int getID();
	
	public abstract Cookie startTotalCostCalculation();
	
	public abstract long calculate(Cookie cookie, CallEntry call);
}
