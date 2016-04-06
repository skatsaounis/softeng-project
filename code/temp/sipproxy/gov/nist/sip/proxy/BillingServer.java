package gov.nist.sip.proxy;

import java.util.ArrayList;

public class BillingServer {
	private static BillingServer billingServer = new BillingServer();

	private BillingServer(){}

	public static BillingServer getInstance() {
		return billingServer;
	}

	public void select_plan(int src, int my_plan) throws ErrorResponse{
		System.out.println("1. select_plan");
		if (Database.getInstance().get_plan(src) == my_plan) return;  // Desired plan already selected.
		Database.getInstance().set_plan(src, my_plan);
	}

	public void call_charge_start(int src, int dst){
		System.out.println("Billing call_charge_start");
		Database.getInstance().record_call_start(src, dst);
	}

	public void call_charge_end(int src, int dst){
		System.out.println("Billing call_charge_end");
		Database.getInstance().record_call_end(src, dst);
	}

	public long total_charge(int name){
		ArrayList<CallEntry> call_list;
		System.out.println("Billing total_charge");
		call_list = Database.getInstance().search_user_calls(name);
		return calculate_total_charge(name, call_list);
	}

	private long calculate_total_charge(int name, ArrayList<CallEntry> call_list){
		System.out.println("5. calculate_total_charge");
		TotalCostCalculator calc = new TotalCostCalculator();
		for (CallEntry call: call_list)
			Database.getInstance().record_call_cost(call.getID(), calc.add(call));
		return calc.getTotalCost();
	}

}
