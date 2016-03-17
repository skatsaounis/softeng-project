//import java.sql.Connection;

public class ProxyServer {
	BlockingServer blocking_server;
	ForwardingServer forwarding_server;
	BillingServer billing_server;
	Database database;
	
	public ProxyServer(BlockingServer blocking_server,
			ForwardingServer forwarding_server, BillingServer billing_server, Database database) {
		this.blocking_server = blocking_server;
		this.forwarding_server = forwarding_server;
		this.billing_server = billing_server;
		this.database = database;
	}

	private boolean equals(int src, int dst){
		return (src == dst);
	}
	
	public void call_start(int src, String dst){
		int target;
		
		System.out.println("1. call_start (\\/)");
		int dest = database.search_user(dst);
		if(!this.is_self_call(src, dst)){
			if(dest == 0)
				System.out.println("Inform no such user");
			else
				if(!this.blocking_check(src, dest)){
					target = this.forwarding_analysis(src, dest);
					if(!database.is_online(target))
						System.out.println("Inform offline");
					else if(!database.is_available(target))
						System.out.println("Inform busy");
					else{
						System.out.println("Init call_from");
						billing_server.call_charge_start(src, dst);
					}
				}
		}
		System.out.println("Inform busy");
	}
	
	public void call_end(int src, int dst){
		System.out.println("11. call_end (\\/)");
		billing_server.call_charge_end(src, dst);
	}
	
	public boolean request_block(int src, String dst){
		System.out.println("2. request_block (\\/)");
		return blocking_server.request_block(src, dst);
	}
	
	public boolean remove_block(int src, String dst){
		System.out.println("3. remove_block (\\/)");
		return blocking_server.remove_block(src, dst);
	}
	
	public boolean forwarding_registration(int src, String dst){
		System.out.println("4. forwarding_registration (\\/)");
		return forwarding_server.forwarding_registration(src, dst);
	}
	
	public void forwarding_removal(int username){
		System.out.println("5. forwarding_removal (\\/)");
		forwarding_server.forwarding_removal(username);
	}
	
	private boolean is_self_call(int src, String dst){
		System.out.println("6. is_self_call (\\/)");
		int dest= database.search_user(dst);
		if(dest >0)
			return equals(src, dest);
		else
			return false;
	}
	
	private boolean blocking_check(int src, int dst){
		System.out.println("7. blocking_check (\\/)");
		return database.search_is_blocking(src, dst);
	}
	
	private int forwarding_analysis(int src, int dst){
		System.out.println("8. forwarding_analysis (\\/)");
		return forwarding_server.route(src, dst);
	}
	
	public void select_plan(String name, int my_plan){
		System.out.println("9. select_plan (\\/)");
		billing_server.select_plan(name, my_plan);
	}

	public long total_charge_request(int name){
		System.out.println("10. total_charge_request  (\\/)");
		return billing_server.total_charge(name);
	}

}
