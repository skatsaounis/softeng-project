package gov.nist.sip.proxy;

import java.sql.*;
import java.util.ArrayList;

import javax.sip.message.Response;

public class Database {
	Connection connection;

	private static Database database = new Database();

	private Database(){
		connection = init_connection();
	}

	public static Database getInstance() {
	 	return database;
	}

	public int search_user(String name){
		System.out.println("Database: search_user");
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
			System.out.println("Database: User "+ name +" has been found!");
			return username;
		}
		else{
			System.out.println("Database: User "+ name +" not found!");
			return 0;
		}
	}

	public boolean is_online(int name){
		System.out.println("Database is_online");
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
			System.out.println("Database: User "+ name +" is online!");
			return true;
		}
		else{
			System.out.println("Database: User "+ name +" is offline!");
			return false;
		}
	}

	public boolean is_available(int name){
		System.out.println("Database: is_available");
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
			System.out.println("Database: User "+ name +" is available!");
			return true;
		}
		else{
			System.out.println("Database: User "+ name +" is not available!");
			return false;
		}
	}

	public boolean search_is_blocking(int src, int dst){
		System.out.println("Database search_is_blocking");
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
			System.out.println("Database: A blocking has been found!");
			return true;
		}
		else{
			System.out.println("Database: No blocking found!");
			return false;
		}
	}

	public boolean set_block(int src, int dst){
		System.out.println("5. set_block (\\/)");
		String sql = "INSERT INTO block (blocker,blockee) " +
	            "VALUES (" + src + "," + dst + ");";
		do_update(connection, sql);
		return true;
	}

	public void remove_block(int src, int dst){
		System.out.println("6. remove_block (\\/)");
		String sql = "DELETE FROM block WHERE blocker=" + src + " AND blockee=" + dst + " ;";
		do_update(connection, sql);
	}

	public boolean search_forwarding(int username){
		System.out.println("Database search_forwarding");
		String sql = "SELECT * FROM user WHERE user_id= " + username +";";
		ResultSet rs;
		rs = do_query(connection, sql);
		int is_forwarding=0;
		try {
			while ( rs.next() ) {
				is_forwarding= rs.getInt("forwardee");
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		close_resultset(rs);
		if (is_forwarding != 0){
			System.out.println("Database: Forwading has been found!");
			return true;
		}
		else{
			System.out.println("Database: No forwarding found!");
			return false;
		}
	}

	public ArrayList<Integer> forwarding_chain(int username){
		System.out.println("Database forwarding_chain");
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
						if (fwd_chain.size()==0)
							fwd_chain.add(username);
						fwd_chain.add(forwardee);
						sql = "SELECT * FROM user WHERE user_id = "+forwardee+" ;";
						if (forwardee == username){
							close_resultset(rs);
							return fwd_chain;
						}
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
		String sql = "UPDATE user SET forwardee=0 " +
				"WHERE user_id=" + username + " ;";
		do_update(connection, sql);
	}

	public boolean can_change_plan(int src){
		return true;  // TODO
	}

	public int get_plan(int name){
		System.out.println("11. get_plan");
		String sql = "SELECT * FROM user WHERE user_id= "+name+" ;";
		ResultSet rs;
		rs = do_query(connection, sql);
		int program=1;
		try {
			while ( rs.next() ) {
			     program = rs.getInt("program");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close_resultset(rs);
		System.out.println("User's program is " + program);
		return program;
	}

	public String get_name(int user_id){
		System.out.println("Database get_name");
		String sql = "SELECT * FROM user WHERE user_id= " + user_id + " ;";
		ResultSet rs;
		rs = do_query(connection, sql);
		String name=null;
		try {
			while ( rs.next() ) {
			     name = rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close_resultset(rs);
		System.out.println("User's name is " + name);
		return name;
	}

	public void set_plan(int name, int my_plan) throws ErrorResponse{
		System.out.println("Database  set_plan");
		Date date= new Date(System.currentTimeMillis()-3*60*60*1000);
		Timestamp time = new Timestamp(date.getTime());
		Timestamp p_registration = time;

		String sql = "SELECT * FROM user WHERE user_id = "+name+" ;";
		ResultSet rs;
		rs = do_query(connection, sql);
		try {
			while ( rs.next() ) {
				 p_registration = Timestamp.valueOf((rs.getString("p_registration")));
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		close_resultset(rs);
		long temp = (time.getTime() - p_registration.getTime())/1000;
		if(temp > 10){
			sql = "UPDATE user SET program=" +
				my_plan + " WHERE user_id=" + name + " ;";
			do_update(connection, sql);
			sql = "UPDATE user SET p_registration=CURRENT_TIMESTAMP " +
				"WHERE user_id=" + name + " ;";
			do_update(connection, sql);
			System.out.println("Database: Program changed to program " + my_plan);
		}
		else{
			System.out.println("Database: Program cannot be changed");
			throw new ErrorResponse(
					Response.TEMPORARILY_UNAVAILABLE, "plan:"+my_plan);
			}
		}

	public void record_call_start(int src, int dst){
		System.out.println("Database record_call_start");
		String sql = "INSERT INTO call (caller,callee,callers_prog,cost) " +
	            "VALUES (" + src + "," + dst + "," + get_plan(src) + ",-1.0);";
		do_update(connection, sql);
	}

	public void record_call_end(int src, int dst){
		System.out.println("Database record_call_end");
		String sql = "SELECT * FROM call WHERE caller = " + src +
				" AND callee = " + dst + " AND end='0000-00-00 00:00:00' AND cost=-1.0";
		boolean flag1=false, flag2=false;
		ResultSet rs = do_query(connection, sql);
		try {
			while ( rs.next() ) {
				flag1 = true;
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		close_resultset(rs);

		sql = "SELECT * FROM call WHERE caller = " + dst +
				" AND callee = " + src + " AND end='0000-00-00 00:00:00' AND cost=-1.0";
		rs = do_query(connection, sql);
		try {
			while ( rs.next() ) {
				flag2 = true;
			  }
		} catch (SQLException e) {
			e.printStackTrace();
		}

		close_resultset(rs);

		if (flag1){
			sql = "UPDATE call SET end=CURRENT_TIMESTAMP WHERE caller=" +
				src + " AND callee=" + dst + " AND end='0000-00-00 00:00:00' AND cost=-1.0";
			System.out.println("Database: swtch1 caller is "+get_name(src) + " and callee is "+get_name(dst));
			do_update(connection, sql);
		}
		else if (flag2){
			sql = "UPDATE call SET end=CURRENT_TIMESTAMP WHERE caller=" +
				dst + " AND callee=" + src + " AND end='0000-00-00 00:00:00' AND cost=-1.0";
			System.out.println("Database: swtch2 caller is "+get_name(dst) + " and callee is "+get_name(src));
			do_update(connection, sql);
		}
		else {
			System.out.println("Error: Extra INFO:end_call message received.");
		}

	}

	public ArrayList<CallEntry> search_user_calls(int name){
		System.out.println("Database search_user_calls");
		String sql = "SELECT * FROM call WHERE caller = "+name+" ;";
		ArrayList<CallEntry> call_list = new ArrayList<CallEntry>();
		ResultSet rs;
		rs = do_query(connection, sql);

		try {
			while ( rs.next() ) {
			     CallEntry new_call = new CallEntry(
			    		 rs.getInt("call_id"),
			    		 BillingProgram.factory(rs.getInt("callers_prog")),
			    		 Timestamp.valueOf(rs.getString("start")),
			    		 Timestamp.valueOf(rs.getString("end")));
			     call_list.add(new_call);
			  }
			close_resultset(rs);
			return call_list;
		} catch (SQLException e) {
			e.printStackTrace();
			close_resultset(rs);
			return call_list;
		}
	}

	public void record_call_cost(int call_id,long cost){
		System.out.println("16. record_call_cost");
		String sql = "UPDATE call SET cost=" + cost + " WHERE call_id=" +
				call_id + " AND cost=-1.0";
		do_update(connection, sql);
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
	      //System.out.println("Update was successfully made");
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
	      //System.out.println("Query was successfully made");
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

	public void add_user(String name) {
		System.out.println("add_user");
		String sql = "INSERT INTO user (name,online) " +
	            "VALUES ('" + name + "',1);";
		do_update(connection, sql);
	}

	public void set_online(int name, int value) {
		String sql = "UPDATE user SET online="+value+" WHERE user_id=" + name + " ;";
		do_update(connection, sql);
	}

	public void set_available(int name, int value) {
		String sql = "UPDATE user SET available="+value+" WHERE user_id=" + name + " ;";
		do_update(connection, sql);
	}

}
