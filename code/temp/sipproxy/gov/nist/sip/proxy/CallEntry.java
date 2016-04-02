package gov.nist.sip.proxy;

import java.sql.Timestamp;

public class CallEntry {
		private int call_id;
		private BillingProgram call_prog;
		private Timestamp start;
		private Timestamp end;
		
		public CallEntry(int call_id, BillingProgram call_prog, Timestamp start, Timestamp end) {
			this.call_id = call_id;
			this.call_prog = call_prog;
			this.start = start;
			this.end = end;
		}
		
		public Timestamp getStart() {
			return start;
		}

		public Timestamp getEnd() {
			return end;
		}

		public int getID() {
			return call_id;
		}

		public BillingProgram getBillingProgram() {
			return call_prog;
		}
}
