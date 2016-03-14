
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
	
	public int total_charge(int name){
		int [] call_list;
		System.out.println("4. total_charge (\\/)");
		call_list = database.search_user_calls(name);
		return calculate_total_charge(name, call_list);
	}
	
	private int calculate_total_charge(int name, int[] call_list){
		System.out.println("5. calculate_total_charge");
		return 1;
	}
	
}
