package gov.nist.sip.proxy;

import gov.nist.sip.proxy.BillingProgram;

public class BillingProgram2 extends BillingProgram {
	private static BillingProgram2 instance = null;

	public static BillingProgram2 getInstance() {
	  if(instance == null) {
		 instance = new BillingProgram2();
	  }
	  return instance;
	}

	public long calculate_cost(String begin, String end) {
		// TODO (ilias)
		return 1;
	}

}
