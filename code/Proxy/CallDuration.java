package gov.nist.sip.proxy;

import java.sql.*;

public class CallDuration {
		private int call_id;
		private int call_prog;
		private Timestamp start;
		private Timestamp end;
		
		public CallDuration(int call_id, int call_prog, Timestamp start, Timestamp end) {
			super();
			this.call_id = call_id;
			this.call_prog = call_prog;
			this.start = start;
			this.end = end;
		}
		
		public Timestamp getStart() {
			return start;
		}
		public void setStart(Timestamp start) {
			this.start = start;
		}
		public Timestamp getEnd() {
			return end;
		}
		public void setEnd(Timestamp end) {
			this.end = end;
		}

		public int getCall_id() {
			return call_id;
		}

		public void setCall_id(int call_id) {
			this.call_id = call_id;
		}
		
		public int getCall_prog() {
			return call_prog;
		}

		public void setCall_prog(int call_prog) {
			this.call_prog = call_prog;
		}

}
