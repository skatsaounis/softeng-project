import java.sql.*;

public class Database {
	Connection connection;
	
	public Database() {
		connection = init_connection();
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
		return false;
	}
	
	public boolean search_is_blocking(int src, int dst){
		System.out.println("4. search_is_blocking");
		return false;
	}
	
	public boolean set_block(int src, int dst){
		System.out.println("5. set_block");
		String sql = "INSERT INTO block (blocker,blockee) " +
	            "VALUES (" + src + "," + dst + ");";
		do_update(connection, sql);
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
		int[] fwd_chain = {1};
		return fwd_chain;
	}
	
	public void set_forwarding(int src, int dst){
		System.out.println("9. set_forwarding");
		String sql = "UPDATE user SET forwardee=" +
				dst + " WHERE user_id=" + src + " ;";
		do_update(connection, sql);
	}
	
	public void remove_forwarding(int username){
		System.out.println("10. remove_forwarding");
		String sql = "UPDATE user SET forwardee=NULL " +
				"WHERE user_id=" + username + " ;";
		do_update(connection, sql);
	}
	
	public void get_plan(int name){
		System.out.println("11. get_plan");
	}
	
	public void set_plan(int name, int my_plan){
		System.out.println("12. set_plan");
		String sql = "UPDATE user SET program=" +
				my_plan + " WHERE user_id=" + name + " ;";
		do_update(connection, sql);
	}
	
	public void record_call_start(int src, int dst){
		System.out.println("13. record_call_start");
		String sql = "INSERT INTO call (caller,callee, cost) " +
	            "VALUES (" + src + "," + dst + ",-1.0);";
		do_update(connection, sql);
	}
	
	public void record_call_end(int src, int dst){
		System.out.println("14. record_call_end");
		String sql = "UPDATE call SET end=CURRENT_TIMESTAMP WHERE caller=" +
				src + " AND callee=" + dst + " AND cost=-1.0";
		do_update(connection, sql);
	}
	
	public int[] search_user_calls(int name){
		System.out.println("15. search_user_calls");
		String sql = "SELECT * FROM call WHERE caller = "+name+" ;";
		ResultSet rs;
		rs = do_query(connection, sql);
		
		try {
			while ( rs.next() ) {
			     int call_id= rs.getInt("call_id");
			     int caller  = rs.getInt("caller");
			     int callee  = rs.getInt("callee");
			     float cost = rs.getFloat("cost");
			     System.out.println( "CALL_ID = " + call_id );
			     System.out.println( "CALLER = " + caller );
			     System.out.println( "CALLEE = " + callee );
			     System.out.println( "COST = " + cost );
			     System.out.println();
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		close_resultset(rs);
		return null;
	}
	
	public Connection init_connection(){
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:softeng.db");
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
