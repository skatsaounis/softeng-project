package gov.nist.sip.proxy;

import gov.nist.sip.proxy.BillingProgram;

public class BillingProgram2 extends BillingProgram {
	
	public class Cookie extends BillingProgram.Cookie {
		
		// TODO (ilias)
		
		public long getTotalCost() {
			// TODO (ilias)
			return 0;
		}
	}
	
	private static BillingProgram2 instance = null;

	public static BillingProgram2 getInstance() {
	  if(instance == null) {
		 instance = new BillingProgram2();
	  }
	  return instance;
	}
	
	public int getID() {
		return 2;
	}

	public BillingProgram.Cookie startTotalCostCalculation() {
		return new Cookie();
	}

	public long calculate(BillingProgram.Cookie cookie, CallEntry call) {
		// TODO (ilias)
		return 1;
	}

}
