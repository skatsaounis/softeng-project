package gov.nist.sip.proxy;

import gov.nist.sip.proxy.BillingProgram;

public class BillingProgram1 extends BillingProgram {
	
	public class Cookie extends BillingProgram.Cookie {
				
		public Cookie () {
			totalCost = 0;
		}
		
		public long getTotalCost() {
			return totalCost;
		}
	}
	
	private static BillingProgram1 instance = null;

	public static BillingProgram1 getInstance() {
	  if(instance == null) {
		 instance = new BillingProgram1();
	  }
	  return instance;
	}

	public int getID() {
		return 1;
	}
	
	public BillingProgram.Cookie startTotalCostCalculation() {
		return new Cookie();
	}
	
	public long calculate(BillingProgram.Cookie cookie, CallEntry call) {
		long duration, callCost;
		
		duration = (call.getEnd().getTime() - call.getStart().getTime())/1000;
		callCost = (long)(duration * 1.0);
		cookie.totalCost += callCost;
		return callCost;
	}
}
