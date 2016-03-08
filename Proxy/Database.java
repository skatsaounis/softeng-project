import java.sql.*;

public class Database {
	Connection c;
	
	public Database() {
		this.c = null;
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
	
	public void get_plan(int name){
		System.out.println("11. get_plan");
	}
	
	public void set_plan(int name, int my_plan){
		System.out.println("12. set_plan");
	}
	
	public void record_call_start(int src, int timestamp){
		System.out.println("13. record_call_start");
	}
	
	public void record_call_end(int src, int timestamp){
		System.out.println("14. record_call_end");
	}
	
	public int[] search_user_calls(int name){
		System.out.println("15. search_user_calls");
		return null;
	}
	
	public Connection init_connection(String db){
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+db);
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    return c;
	}
	
	public void close_connection(Connection c){
		try {
	      c.close();
	      System.out.println("Connection terminated successfully");
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
	public void do_update(Connection c, String sql){
		Statement stmt = null;
	    try {
	    	stmt = c.createStatement();
	        stmt.executeUpdate(sql);
	        stmt.close();
	        c.commit();
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	      System.out.println("Update was successfully made");
	}
	
	public ResultSet do_query(Connection c, String sql){
		Statement stmt = null;
		ResultSet rs = null;
	    try {
	    	stmt = c.createStatement();
	    	rs = stmt.executeQuery(sql);
	      } catch ( Exception e ) {
	        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	        System.exit(0);
	      }
	      System.out.println("Query was successfully made");
	      return rs;
	}
	
	public void close_resultset(ResultSet rs){
		try {
	      rs.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
}
