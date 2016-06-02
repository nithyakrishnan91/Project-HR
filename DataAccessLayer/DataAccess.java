import java.sql.*;

public class DataAccess {
	
	static final String DB_URL ="jdbc:sqlserver://localhost;databaseName=ProjectHR;integratedSecurity=true";
	
	public static void main(String[] args) {
		   Connection conn = null;
		   Statement stmt = null;
		   try{
		      //Register JDBC driver
			   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		      //Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL);

		      //Execute a query
		      System.out.println("Creating statement...");
		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT [EmpID],[EmpName],[DeptID],[RoleID]FROM [ProjectHR].[dbo].[Employee]";
		      ResultSet rs = stmt.executeQuery(sql);
		      //Extract data from result set
		      while(rs.next()){
		         //Retrieve by column name
		         int id  = rs.getInt("EmpID");
		        
		         String first = rs.getString("EmpName");
		         
		         //Display values
		         System.out.print("ID: " + id);
		        
		         System.out.print(", Name: " + first);
		         
		      }
		      //Clean-up environment and close connections
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("\nGoodbye!");
		}//end main

}
