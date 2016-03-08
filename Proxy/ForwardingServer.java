public class ForwardingServer {

	public ForwardingServer() {
		// empty
	}

	public int route(int src, int dst){
		System.out.println("1. route");
		return 42;
	}
	
	public void forwarding_registration(int src, int dst){
		System.out.println("2. forwarding_registration");
	}
	
	public void forwarding_removal(int src, int dst){
		System.out.println("3. forwarding_removal");
	}
	
	private boolean blocking_check(int caller, int[] fwd_chain){
		System.out.println("4. blocking_check");
		return true;
	}
	
	private boolean is_self_call(int src, int dst){
		System.out.println("5. is_self_call");
		return true;
	}
	
	public void private_fun(){
		int src = 1;
		int dst = 1;
		int caller = 1;
		int[] fwd_chain = {1};
		
		this.blocking_check(caller, fwd_chain);
		this.is_self_call(src, dst);
	}
}
