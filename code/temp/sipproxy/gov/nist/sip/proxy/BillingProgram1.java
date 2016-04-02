package gov.nist.sip.proxy;

import gov.nist.sip.proxy.BillingProgram;

public class BillingProgram1 extends BillingProgram {
	private static BillingProgram1 instance = null;

	public static BillingProgram1 getInstance() {
	  if(instance == null) {
		 instance = new BillingProgram1();
	  }
	  return instance;
	}

	public long calculate_cost(String begin, String end) {
		// TODO (ilias)
		return 1;
	}

}
