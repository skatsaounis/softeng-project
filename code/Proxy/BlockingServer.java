public class BlockingServer {
	
	
	public BlockingServer() {
		// empty
	}
	
	public boolean is_blocking(int src, int dst){
		System.out.println("1. is_blocking");
		return true;
	}
	
	public boolean blocking_check(int caller, int[] fwd_chain){
		System.out.println("2. blocking_check");
		return true;
	}
	
	public void request_block(int src, int dst){
		System.out.println("3. request_block");
	}
	
	public void remove_block(int src, int dst){
		System.out.println("4. remove_block");
	}
	
}
