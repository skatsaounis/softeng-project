public class ProxyServer {
	
	public ProxyServer() {
		// empty
	}

	public void call_start(int src, int dst){
		System.out.println("1. call_start");
	}
	
	public void request_block(int src, int dst){
		System.out.println("2. request_block");
	}
	
	public void remove_block(int src, int dst){
		System.out.println("3. remove_block");
	}
	
	public void forwarding_registration(int src, int dst){
		System.out.println("4. forwarding_registration");
	}
	
	public void forwarding_removal(int username){
		System.out.println("5. forwarding_removal");
	}
	
	private boolean is_self_call(int src, int dst){
		System.out.println("6. is_self_call");
		return true;
	}
	
	private boolean blocking_check(int src, int dst){
		System.out.println("7. blocking_check");
		return true;
	}
	
	private int forwarding_analysis(int src, int dst){
		System.out.println("8. forwarding_analysis");
		return 1;
	}
	
	public void private_fun(){
		int src = 1;
		int dst = 1;
		
		this.is_self_call(src, dst);
		this.blocking_check(src, dst);
		this.forwarding_analysis(src, dst);
	}
	
	public static void main(String[] args) {
		int[] fwd_chain = {1};
		int src = 1;
		int dst = 1;
		int username = 1;
		int name = 1;
		
		ProxyServer proxy_server = new ProxyServer();
		BlockingServer blocking_server = new BlockingServer();
		ForwardingServer forwarding_server = new ForwardingServer();
		Database database = new Database();
		
		System.out.println("Proxy Server");
		proxy_server.call_start(src, dst);
		proxy_server.request_block(src, dst);
		proxy_server.remove_block(src, dst);
		proxy_server.forwarding_registration(src, dst);
		proxy_server.forwarding_removal(username);
		proxy_server.private_fun();
		System.out.println("---");
		
		System.out.println("Blocking Server");
		blocking_server.is_blocking(src, dst);
		blocking_server.blocking_check(1, fwd_chain);
		blocking_server.request_block(src, dst);
		blocking_server.remove_block(src, dst);
		System.out.println("---");
		
		System.out.println("Forwarding Server");
		forwarding_server.route(src, dst);
		forwarding_server.forwarding_registration(src, dst);
		forwarding_server.forwarding_removal(src, dst);
		forwarding_server.private_fun();
		System.out.println("---");
		
		System.out.println("Database");
		database.search_user(name);
		database.is_online(name);
		database.is_available(name);
		database.search_is_blocking(src, dst);
		database.set_block(src, dst);
		database.remove_block(src, dst);
		database.search_forwarding(username);
		database.forwarding_chain(username);
		database.set_forwarding(src, dst);
		database.remove_forwarding(src, dst);
		System.out.println("---");
		
	}

}
