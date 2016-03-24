package gov.nist.sip.proxy;

import java.util.ArrayList;

import javax.sip.message.Response;

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
		int i, j;
		int dst;
		int source;
		for(i=1; i<fwd_chain.size(); i++){
			dst = fwd_chain.get(i);
			if(is_blocking(dst, caller)){
				System.out.println("Blocked by someone in fwd_chain!");
				return false;
			}
		}
		/* Check if anyone in chain is blocked by its previous */
		for(i=0; i<fwd_chain.size(); i++){
			dst = fwd_chain.get(i);
			for(j=0;j<i;j++){
				source = fwd_chain.get(j);
				if(is_blocking(dst, source)){
					System.out.println("Someone in fwd_chain is blocked by someone next!");
					return false;
				}
			}
		}
		System.out.println("Acceptable Call");
		return true;
	}
	
	public void request_block(int src, String dst) throws ErrorResponse{
		System.out.println("3. request_block (\\/)");
		int dest = database.search_user(dst);
		if(dest <= 0) throw new ErrorResponse(Response.NOT_FOUND, "Blockee unknown");
		if(!database.search_is_blocking(src, dest))
			database.set_block(src, dest);
	}
	
	public void remove_block(int src, String dst) throws ErrorResponse{
		System.out.println("4. remove_block (\\/)");
		int dest = database.search_user(dst);
		if(dest <= 0) throw new ErrorResponse(Response.NOT_FOUND, "Blockee unknown");
		if(database.search_is_blocking(src, dest))
			database.remove_block(src, dest);
	}
	
}
