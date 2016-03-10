public class ForwardingServer {
	BlockingServer blocking;
	Database database;
	
	public ForwardingServer(BlockingServer blocking, Database database) {
		this.blocking = blocking;
		this.database = database;
	}

	private boolean equals(int src, int dst){
		return (src == dst);
	}
	
	public int route(int src, int dst){
		System.out.println("1. route (\\/)");
		int[] fwd_chain = database.forwarding_chain(dst);
		if(!blocking_check(src, fwd_chain))
			if(!is_self_call(src, fwd_chain[fwd_chain.length-1]))
				return fwd_chain[fwd_chain.length-1];
		System.out.println("Inform busy");
		return -1;
	}
	
	public boolean forwarding_registration(int src, int dst){
		System.out.println("2. forwarding_registration (\\/)");
		int[] list_of_usernames;
		if(database.search_user(dst)){
			list_of_usernames = database.forwarding_chain(dst);
			if(list_of_usernames != null){
				database.set_forwarding(src, dst);
				return true;
			}
		}
		return false;
	}
	
	public void forwarding_removal(int username){
		System.out.println("3. forwarding_removal (\\/)");
		if(database.search_forwarding(username)){
			database.remove_forwarding(username);
		}
	}
	
	private boolean blocking_check(int caller, int[] fwd_chain){
		System.out.println("4. blocking_check (\\/)");
		return blocking.blocking_check(caller, fwd_chain);
	}
	
	private boolean is_self_call(int src, int dst){
		System.out.println("5. is_self_call (\\/)");
		return equals(src, dst);	
	}
	
}
