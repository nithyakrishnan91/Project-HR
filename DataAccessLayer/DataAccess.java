import java.sql.*;

public class DataAccess {
	
	static Connection connection = SqlConnection.getConnection();
	
	public static void main(String[] args) {
		  		
		   try{
		     
			   System.out.println("Connecting to database...");
		     
		       System.out.println("Creating statement...");
		       
		       PreparedStatement statement = connection.prepareStatement("SELECT [EmpID],[EmpName],[DeptID],[RoleID]FROM [ProjectHR].[dbo].[Employee]");
			   
		      
		      ResultSet rs = statement.executeQuery();
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
		      statement.close();		      
		   }
		   catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }
		   catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
		   finally{
		      //finally block used to close resources		      
		      try{
		         if(connection!=null)
		            connection.close();
		      }
		      catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("\nGoodbye!");
		}//end main

}
