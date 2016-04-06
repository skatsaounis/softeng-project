package gov.nist.sip.proxy;

import javax.sip.message.Response;

//import java.sql.Connection;

public class ProxyServer {
	private static ProxyServer proxyServer = new ProxyServer();

	private ProxyServer(){}

	public static ProxyServer getInstance() {
		return proxyServer;
	}

	private boolean equals(int src, int dst){
		return (src == dst);
	}

	public int call_start(int src, int dest) throws ErrorResponse{
		int target;

		System.out.println("Proxy call_start");

		if(!this.is_self_call(src, dest)){
			if(!this.blocking_check(dest, src)){
				target = this.forwarding_analysis(src, dest);
				if (target < 0) {
					System.out.println("Inform busy");
					throw new ErrorResponse(Response.BUSY_HERE);
				}
				else if(!Database.getInstance().is_available(target)) {
					System.out.println("Inform busy");
					throw new ErrorResponse(Response.BUSY_HERE);
				}
				else{
					return target;
				}
			} else{
				System.out.println("Inform busy");
				throw new ErrorResponse(Response.BUSY_HERE);
			}
		}
		else {
			System.out.println("Inform busy");
			throw new ErrorResponse(Response.BUSY_HERE);
		}
	}

	public void call_end(int src, int dst){
		System.out.println("Proxy call_end");
		BillingServer.getInstance().call_charge_end(src, dst);
		Database.getInstance().set_available(src, 1);
		Database.getInstance().set_available(dst, 1);
	}

	public void request_block(int src, String dst) throws ErrorResponse{
		System.out.println("2. request_block");
		BlockingServer.getInstance().request_block(src, dst);
	}

	public void remove_block(int src, String dst) throws ErrorResponse{
		System.out.println("3. remove_block");
		BlockingServer.getInstance().remove_block(src, dst);
	}

	public void forwarding_registration(int src, String dst) throws ErrorResponse{
		System.out.println("4. forwarding_registration");
		ForwardingServer.getInstance().forwarding_registration(src, dst);
	}

	public void forwarding_removal(int src) {
		System.out.println("5. forwarding_removal");
		ForwardingServer.getInstance().forwarding_removal(src);
	}

	private boolean is_self_call(int src, int dst){
		System.out.println("Proxy is_self_call");
		if(dst >0)
			return equals(src, dst);
		else
			return false;
	}

	private boolean blocking_check(int src, int dst){
		System.out.println("Proxy blocking_check");
		return BlockingServer.getInstance().is_blocking(src, dst);
	}

	private int forwarding_analysis(int src, int dst){
		System.out.println("Proxy forwarding_analysis");
		return ForwardingServer.getInstance().route(src, dst);
	}

	public void select_plan(int src, int my_plan) throws ErrorResponse{
		System.out.println("9. select_plan");
		BillingServer.getInstance().select_plan(src, my_plan);
	}

	public long total_charge_request(int src){
		System.out.println("10. total_charge_request");
		return BillingServer.getInstance().total_charge(src);
	}

}
