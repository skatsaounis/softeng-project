package gov.nist.sip.proxy;

import gov.nist.sip.proxy.BillingProgram;
import java.util.Calendar;

public class BillingProgram2 extends BillingProgram {
	
	public class Cookie extends BillingProgram.Cookie {
		
		public Cookie () {
			totalCost = 0;
		}
		
		public long getTotalCost() {
			return totalCost;
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
		long duration, callCost;
		Calendar cal;
				
		long timestamp = call.getStart().getTime();
		cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);		
		
		if ((cal.get(Calendar.HOUR_OF_DAY) >= 0) && (cal.get(Calendar.HOUR_OF_DAY) <= 6))
			callCost = 0;
		else if ((cal.get(Calendar.DAY_OF_WEEK) == 0) || (cal.get(Calendar.DAY_OF_WEEK) == 4) 
				|| (cal.get(Calendar.DAY_OF_WEEK) == 5 || (cal.get(Calendar.DAY_OF_WEEK) == 6)))
			callCost = 0;
		else {
			duration = (call.getEnd().getTime() - call.getStart().getTime())/1000;
			callCost = (long)(duration * 1.0);
		}
		cookie.totalCost += callCost;
		return callCost;
	}

}
