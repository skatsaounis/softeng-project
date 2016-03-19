import java.util.ArrayList;
import java.util.Iterator;


public class Proxy {

	public Proxy() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// int[] fwd_chain = {1};

		
		Database database = new Database();
		BlockingServer blocking_server = new BlockingServer(database);
		ForwardingServer forwarding_server = new ForwardingServer(blocking_server, database);
		BillingServer billing_server = new BillingServer(database);
		ProxyServer proxy_server = new ProxyServer(blocking_server, forwarding_server, billing_server, database);
		
		System.out.println(proxy_server.total_charge_request(1));
		proxy_server.select_plan("stamatis",2);
		proxy_server.select_plan("stamatis",1);
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		proxy_server.select_plan("stamatis",1);
		proxy_server.call_start(1,"thanasis");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
		proxy_server.call_end(1, 5);
		System.out.println(proxy_server.total_charge_request(1));
	}

}
