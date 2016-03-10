
public class BillingServer {
	Database database;
	
	public BillingServer(Database database) {
		this.database = database;
	}

	public void select_plan(int name, int my_plan){
		System.out.println("1. select_plan (\\/)");
		database.search_user(name);
		database.get_plan(name);
		if(name==1) /* must be replaced. If plan can be changed */
			database.set_plan(name, my_plan);
		else
			System.out.println("Inform failure");
	}
	
	public void call_charge_start(int src, int dst, int timestamp){
		System.out.println("2. call_charge_start (\\/)");
		database.search_user(dst);
		database.record_call_start(src, timestamp);
	}
	
	public void call_charge_end(int src, int dst, int timestamp){
		System.out.println("3. call_charge_end (\\/)");
		database.record_call_end(src, timestamp);
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
