package gov.nist.sip.proxy;

import java.util.ArrayList;

import javax.sip.message.Response;

public class ForwardingServer {
	private static ForwardingServer forwardingServer = new ForwardingServer();

	private ForwardingServer(){}

	public static ForwardingServer getInstance() {
		return forwardingServer;
	}

	private boolean equals(int src, int dst){
		return (src == dst);
	}

	public int route(int src, int dst){
		System.out.println("1. route");
		ArrayList<Integer> fwd_chain = Database.getInstance().forwarding_chain(dst);
		if(fwd_chain.size()>0){
			if(BlockingServer.getInstance().blocking_check(src, fwd_chain))
				if(!is_self_call(src, fwd_chain.get(fwd_chain.size()-1)))
					return fwd_chain.get(fwd_chain.size()-1);
			System.out.println("Inform busy"); //not necessary
			return -1;
		} else
			return dst;
	}

	public void forwarding_registration(int src, String dst) throws ErrorResponse{
		System.out.println("2. forwarding_registration");
		int dest = Database.getInstance().search_user(dst);
		if (dest <= 0) throw new ErrorResponse(Response.NOT_FOUND, "forward:Forwardee unknown");

        // Confirm that the new forwarding
        // will not create a forwarding loop.
        ArrayList<Integer> dest_fwdees = Database.getInstance().forwarding_chain(dest);
        if (dest_fwdees == null) throw new ErrorResponse(Response.SERVER_INTERNAL_ERROR, "forward:Unexpected internal error");
        if (dest_fwdees.contains(src)) throw new ErrorResponse(Response.FORBIDDEN, "forward:Forwarding creates loop");

        Database.getInstance().set_forwarding(src, dest);
	}

	public void forwarding_removal(int username){
		System.out.println("3. forwarding_removal");
		if(Database.getInstance().search_forwarding(username)){
			Database.getInstance().remove_forwarding(username);
		}
	}

	private boolean blocking_check(int caller, ArrayList<Integer> fwd_chain){
		System.out.println("4. blocking_check");
		return BlockingServer.getInstance().blocking_check(caller, fwd_chain);
	}

	private boolean is_self_call(int src, int dst){
		System.out.println("5. is_self_call");
		return equals(src, dst);
	}

}
