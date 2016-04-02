package gov.nist.sip.proxy;

public abstract class BillingProgram {
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
	
	public abstract long calculate_cost(String begin, String end);
}
