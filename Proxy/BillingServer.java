
public class BillingServer {

	public BillingServer() {
		// empty
	}

	public void select_plan(int name, int my_plan){
		System.out.println("1. select_plan");
	}
	
	public void call_charge_start(int src, int dst, int timestamp){
		System.out.println("2. call_charge_start");
	}
	
	public void call_charge_end(int src, int dst, int timestamp){
		System.out.println("3. call_charge_end");
	}
	
	public int total_charge(int name){
		System.out.println("4. total_charge");
		return 1;
	}
	
	private int calculate_total_charge(int name, int[] call_list){
		System.out.println("5. calculate_total_charge");
		return 1;
	}
	
	public void private_fun(){
		int name = 1;
		int[] call_list = {1};
		
		this.calculate_total_charge(name, call_list);
	}
}
