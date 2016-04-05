package gov.nist.sip.proxy;

public class TotalCostCalculator {
	private BillingProgram.Cookie[] programCookies;
	
	public TotalCostCalculator() {
		programCookies = new BillingProgram.Cookie[BillingProgram.numPrograms() + 1];
	}
	
	public long getTotalCost() {
		long total = 0;
		for (BillingProgram.Cookie cookie: programCookies)
			if (cookie != null)
				total += cookie.getTotalCost();
		return total;
	}
	
	public long add(CallEntry call) {
			
		if (programCookies[call.getBillingProgram().getID()] == null)
			programCookies[call.getBillingProgram().getID()]
					= call.getBillingProgram().startTotalCostCalculation();
		return call.getBillingProgram().calculate(
				programCookies[call.getBillingProgram().getID()],
				call);
	}
}
