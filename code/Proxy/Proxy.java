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
		blocking_server.remove_block(5, "stamatis");
		System.out.println(forwarding_server.route(5,1));
	}

}
