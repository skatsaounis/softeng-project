package gov.nist.sip.proxy;

import gov.nist.sip.proxy.BillingProgram;

public class BillingProgram3 extends BillingProgram {
	
	public class Cookie extends BillingProgram.Cookie {
		
		public Cookie () {
			totalCost = 0;
		}
		
		public long getTotalCost() {
			return totalCost;
		}
	}

	private static BillingProgram3 instance = null;

	public static BillingProgram3 getInstance() {
	  if(instance == null) {
		 instance = new BillingProgram3();
	  }
	  return instance;
	}
	
	public int getID() {
		return 3;
	}
	
	public BillingProgram.Cookie startTotalCostCalculation() {
		return new Cookie();
	}

	public long calculate(BillingProgram.Cookie cookie, CallEntry call) {
		long duration, callCost;
		long fixed_charge = (long) 5.0;
		
		duration = (call.getEnd().getTime() - call.getStart().getTime())/1000;
		if (duration > 10.0)
			duration = (long) 10.0;
		callCost = fixed_charge +(long)(duration * 1.0);
		cookie.totalCost += callCost;
		return callCost;
		
	}

}
