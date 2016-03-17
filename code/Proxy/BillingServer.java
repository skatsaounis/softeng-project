import java.util.ArrayList;


public class BillingServer {
	Database database;
	
	public BillingServer(Database database) {
		this.database = database;
	}

	public void select_plan(String name, int my_plan){
		System.out.println("1. select_plan (\\/)");
		int user = database.search_user(name);
		if(database.get_plan(user)>0) /* must be replaced. If plan can be changed */
			database.set_plan(user, my_plan);
		else
			System.out.println("Inform failure");
	}
	
	public void call_charge_start(int src, String dst){
		System.out.println("2. call_charge_start (\\/)");
		int dest = database.search_user(dst);
		database.record_call_start(src, dest);
	}
	
	public void call_charge_end(int src, int dst){
		System.out.println("3. call_charge_end (\\/)");
		database.record_call_end(src, dst);
	}
	
	public long total_charge(int name){
		ArrayList<CallDuration> call_list;
		System.out.println("4. total_charge (\\/)");
		call_list = database.search_user_calls(name);
		return calculate_total_charge(name, call_list);
	}
	
	private long calculate_total_charge(int name, ArrayList<CallDuration> call_list){
		System.out.println("5. calculate_total_charge (\\/)");
		long call_cost;
		long total_charge = 0;
		long duration;
		int i;
		for(i=0; i<call_list.size(); i++){
			duration = (call_list.get(i).getEnd().getTime() - call_list.get(i).getStart().getTime())/1000;
			switch (call_list.get(i).getCall_prog()){
				case 1:
					System.out.println("User's plan was 1");
					call_cost = (long)(duration * 1.0);
					database.record_call_cost(call_list.get(i).getCall_id(), call_cost);
					total_charge += call_cost;
					break;
				case 2:
					System.out.println("User's plan was 2");
					call_cost = (long)(duration * 2.0);
					database.record_call_cost(call_list.get(i).getCall_id(), call_cost);
					total_charge += call_cost;
					break;
				case 3:
					System.out.println("User's plan was 3");
					call_cost = (long)(duration * 3.0);
					database.record_call_cost(call_list.get(i).getCall_id(), call_cost);
					total_charge += call_cost;
					break;
			}
		}
		
		return total_charge;
	}
	
}
