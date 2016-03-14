import java.util.ArrayList;

public class BlockingServer {
	Database database;
	
	public BlockingServer(Database database) {
		this.database = database;
	}
	
	public boolean is_blocking(int src, int dst){
		System.out.println("1. is_blocking (\\/)");
		return database.search_is_blocking(src, dst);
	}
	
	public boolean blocking_check(int caller, ArrayList<Integer> fwd_chain){
		System.out.println("2. blocking_check (\\/)");
		int i;
		int dst;
		for(i=0; i<(fwd_chain.size()-1); i++){
			dst = fwd_chain.get(i);
			if(database.search_is_blocking(caller, dst)){
				System.out.println("Blocked by someone in fwd_chain");
				return false;
			}
		}
		/* To be added -- Check if anyone in chain is blocked by its previous */
		System.out.println("Acceptable Call");
		return true;
	}
	
	public boolean request_block(int src, String dst){
		System.out.println("3. request_block (\\/)");
		int dest = database.search_user(dst);
		if(dest>0){
			if(!database.search_is_blocking(src, dest))
				database.set_block(src, dest);
			return true;
		}
		else
			return false;
	}
	
	public boolean remove_block(int src, String dst){
		System.out.println("4. remove_block (\\/)");
		int dest = database.search_user(dst);
		if(dest>0){
			if(database.search_is_blocking(src, dest))
				database.remove_block(src, dest);
			return true;
		}
		else
			return false;
	}
	
}
