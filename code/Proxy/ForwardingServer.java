import java.util.ArrayList;

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
		ArrayList<Integer> fwd_chain = database.forwarding_chain(dst);
		if(fwd_chain.size()>0){
			if(blocking_check(src, fwd_chain))
				if(!is_self_call(src, fwd_chain.get(fwd_chain.size()-1)))
					return fwd_chain.get(fwd_chain.size()-1);
			System.out.println("Inform busy"); //not necessary
			return -1;
		} else
			return dst;
	}

	public boolean forwarding_registration(int src, String dst){
		System.out.println("2. forwarding_registration (\\/)");
		int dest = database.search_user(dst);
		if (dest <= 0) return false;  // nonexistent user.

        // Confirm that the new forwarding
        // will not create a forwarding loop.
        ArrayList<Integer> dest_fwdees = database.forwarding_chain(dest);
        if (dest_fwdees == null) return false;  // unexpected internal error.
        if (dest_fwdees.contains(src)) return false;  // loop exists.

        database.set_forwarding(src, dest);
        return true;
	}

	public void forwarding_removal(int username){
		System.out.println("3. forwarding_removal (\\/)");
		if(database.search_forwarding(username)){
			database.remove_forwarding(username);
		}
	}

	private boolean blocking_check(int caller, ArrayList<Integer> fwd_chain){
		System.out.println("4. blocking_check (\\/)");
		return blocking.blocking_check(caller, fwd_chain);
	}

	private boolean is_self_call(int src, int dst){
		System.out.println("5. is_self_call (\\/)");
		return equals(src, dst);
	}

}
