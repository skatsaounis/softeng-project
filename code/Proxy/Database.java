import java.sql.*;
import java.util.ArrayList;

public class Database {
	Connection connection;
	
	public Database() {
		connection = init_connection();
	}

	public int search_user(String name){
		System.out.println("1. search_user");
		String sql = "SELECT * FROM user WHERE name=\"" + name + "\" ;";
		ResultSet rs;
		rs = do_query(connection, sql);
		int username = 0;
		try {
			while ( rs.next() ) {
				username= rs.getInt("user_id");
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close_resultset(rs);
		if (username != 0){
			System.out.println("User "+ name +" has been found!");
			return username;
		}
		else{
			System.out.println("User "+ name +" not found!");
			return 0;
		}
	}
	
	public boolean is_online(int name){
		System.out.println("2. is_online");
		String sql = "SELECT * FROM user WHERE user_id= " + name + " ;";
		ResultSet rs;
		rs = do_query(connection, sql);
		int is_online = 0;
		try {
			while ( rs.next() ) {
			     is_online= rs.getInt("online");
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close_resultset(rs);
		if (is_online != 0){
			System.out.println("User "+ name +" is online!");
			return true;
		}
		else{
			System.out.println("User "+ name +" is offline!");
			return false;
		}
	}
	
	public boolean is_available(int name){
		System.out.println("3. is_available");
		String sql = "SELECT * FROM user WHERE user_id= "+name+" ;";
		ResultSet rs;
		rs = do_query(connection, sql);
		int is_available = 0;
		try {
			while ( rs.next() ) {
			     is_available= rs.getInt("available");
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close_resultset(rs);
		if (is_available != 0){
			System.out.println("User "+ name +" is available!");
			return true;
		}
		else{
			System.out.println("User "+ name +" is not available!");
			return false;
		}
	}
	
	public boolean search_is_blocking(int src, int dst){
		System.out.println("4. search_is_blocking");
		String sql = "SELECT * FROM block WHERE blocker= " +
				src +" AND blockee=" + dst + ";";
		ResultSet rs;
		rs = do_query(connection, sql);
		int is_blocking = 0;
		try {
			while ( rs.next() ) {
			     is_blocking= 1;
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		close_resultset(rs);
		if (is_blocking != 0){
			System.out.println("A blocking has been found!");
			return true;
		}
		else{
			System.out.println("No blocking found!");
			return false;
		}
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
	
	public ArrayList<Integer> forwarding_chain(int username){
		System.out.println("8. forwarding_chain");
		ArrayList<Integer> fwd_chain = new ArrayList<Integer>();
		String sql = "SELECT * FROM user WHERE user_id = "+username+" ;";
		ResultSet rs;
		int forwardee;
		
		while(true){
			rs = do_query(connection, sql);
			try {
				while ( rs.next() ) {
					forwardee = rs.getInt("forwardee");
					if(forwardee > 0){
						fwd_chain.add(forwardee);
						sql = "SELECT * FROM user WHERE user_id = "+forwardee+" ;";
					}
					else{
						close_resultset(rs);
						return fwd_chain;
					}
				  }
			} catch (SQLException e) {
				e.printStackTrace();
				close_resultset(rs);
				return fwd_chain;
			}
		}
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
	
	public int get_plan(int name){
		System.out.println("11. get_plan");
		return 1;
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
