package gov.nist.sip.proxy;

public class ErrorResponse extends Throwable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int m_responseEnum;
	public String m_reason;

	ErrorResponse(int responseEnum) {
		this(responseEnum, "");
	}
	
	ErrorResponse(int responseEnum, String reason) {
		m_responseEnum = responseEnum;
		m_reason = reason;
	}
}
