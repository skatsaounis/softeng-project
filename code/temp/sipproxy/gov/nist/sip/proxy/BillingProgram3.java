package gov.nist.sip.proxy;

import gov.nist.sip.proxy.BillingProgram;

public class BillingProgram3 extends BillingProgram {
	private static BillingProgram3 instance = null;

	public static BillingProgram3 getInstance() {
	  if(instance == null) {
		 instance = new BillingProgram3();
	  }
	  return instance;
	}

	public long calculate_cost(String begin, String end) {
		// TODO (ilias)
		return 1;
	}

}
