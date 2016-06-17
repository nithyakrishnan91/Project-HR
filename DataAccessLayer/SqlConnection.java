import java.sql.*;

public class SqlConnection {
	
	static final String DB_URL ="jdbc:sqlserver://localhost;databaseName=ProjectHR;integratedSecurity=true";	
	
	public static Connection _connection = null;	

	private SqlConnection(){
	}
	
	public static Connection getConnection(){	
		try {
			if(_connection == null)
			{				
				connect();									
			}
						
		}
		catch(Exception e) {			
			e.printStackTrace();			
		}	
		return _connection;
	}
	
	public static void closeConnection(){
		try{
			if(_connection != null)
			{				
				_connection.close();
				_connection = null;
			}			
		}
		catch (SQLException e) {			
			e.printStackTrace();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}					
	}
	
	private static void connect(){
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			//Open a connection			
			_connection = DriverManager.getConnection(DB_URL);	
			
		} 
		catch (ClassNotFoundException e) {			
			e.printStackTrace();
			
		} 
		catch (SQLException e) {			
			e.printStackTrace();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}					
	}
	
}