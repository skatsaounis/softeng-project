
public class Proxy {

	public Proxy() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		int[] fwd_chain = {1};
		int src = 1;
		int dst = 1;
		int username = 1;
		int name = 1;
		int my_plan = 1;
		int timestamp = 1;
		
		/*String db = "test.db";
		Connection c = null;*/
		Database database = new Database();
		BlockingServer blocking_server = new BlockingServer(database);
		ForwardingServer forwarding_server = new ForwardingServer(blocking_server, database);
		BillingServer billing_server = new BillingServer(database);
		ProxyServer proxy_server = new ProxyServer(blocking_server, forwarding_server, billing_server, database);
		
		
		/*System.out.println("Proxy Server");
		proxy_server.call_start(src, dst);
		proxy_server.request_block(src, dst);
		proxy_server.remove_block(src, dst);
		proxy_server.forwarding_registration(src, dst);
		proxy_server.forwarding_removal(username);
		proxy_server.select_plan(name, my_plan);
		proxy_server.total_charge_request(name);
		proxy_server.call_end(src, dst);
		System.out.println("---");
		
		System.out.println("Blocking Server");
		blocking_server.is_blocking(src, dst);
		blocking_server.blocking_check(1, fwd_chain);
		blocking_server.request_block(3, 1);
		blocking_server.remove_block(src, dst);
		System.out.println("---");
		
		System.out.println("Forwarding Server");
		forwarding_server.route(src, dst);
		forwarding_server.forwarding_registration(src, dst);
		forwarding_server.forwarding_removal(username);
		System.out.println("---");
		
		/*System.out.println("---");
		c = database.init_connection(db);
		database.close_connection(c);
		
		System.out.println("Billing Server");
		billing_server.select_plan(username, my_plan);
		billing_server.call_charge_start(src, dst, timestamp);
		billing_server.call_charge_end(src, dst, timestamp);
		billing_server.total_charge(name);
		System.out.println("---");*/
		/*database.record_call_start(5, 4);
		database.record_call_end(5, 4);
		database.search_user_calls(5);
		database.set_block(3, 5);
		database.set_forwarding(4, 5);
		database.remove_forwarding(4);
		database.set_plan(4, 43);
		database.close_connection(database.connection);*/
		database.set_forwarding(1, 2);
		database.set_forwarding(2, 3);
		database.set_forwarding(3, 4);
		database.set_forwarding(4, 5);
		System.out.println(database.forwarding_chain(1).toString());
	}

}
