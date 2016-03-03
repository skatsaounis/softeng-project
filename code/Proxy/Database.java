
public class Database {

	public Database() {
		// empty
	}

	public boolean search_user(int name){
		System.out.println("1. search_user");
		return true;
	}
	
	public boolean is_online(int name){
		System.out.println("2. is_online");
		return true;
	}
	
	public boolean is_available(int name){
		System.out.println("3. is_available");
		return true;
	}
	
	public boolean search_is_blocking(int src, int dst){
		System.out.println("4. search_is_blocking");
		return true;
	}
	
	public boolean set_block(int src, int dst){
		System.out.println("5. set_block");
		return true;
	}
	
	public void remove_block(int src, int dst){
		System.out.println("6. remove_block");
	}
	
	public boolean search_forwarding(int username){
		System.out.println("7. search_forwarding");
		return true;
	}
	
	public int[] forwarding_chain(int username){
		System.out.println("8. forwarding_chain");
		return null;
	}
	
	public void set_forwarding(int src, int dst){
		System.out.println("9. set_forwarding");
	}
	
	public void remove_forwarding(int src, int dst){
		System.out.println("10. remove_forwarding");
	}
}
