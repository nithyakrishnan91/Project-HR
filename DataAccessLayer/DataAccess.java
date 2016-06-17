import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.*;
import java.util.Date;

public class DataAccess {
	
	Connection sqlConnection; 
	PreparedStatement statement;
	Scanner input = new Scanner(System.in);

	public HashMap<String,Object> getTrainingsToApprove()
	{
		HashMap<String,Object> dataset = new HashMap<String, Object>();
	
		try {
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   (" SELECT [ID]"
					   		+ ",[TrainerID]"
					   		+ ",[TraineeID]"
					   		+ ",(SELECT s.[Desc] FROM [ProjectHR].[dbo].Skills s WHERE S.ID = t.SkillID) AS Skill"
					   		+ ",[StartDate]"
					   		+ ",[EndDate]"
					   		+ ",[Status]"
					   		+ "FROM [ProjectHR].[dbo].[Training] t "
					   		+ "WHERE t.Status = 'Not-Approved'");	
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()){
				dataset.put("ID", result.getString("ID"));
				dataset.put("TrainerID", result.getString("TrainerID"));
				dataset.put("TraineeID", result.getString("TraineeID"));
				dataset.put("Skill", result.getString("Skill"));
				dataset.put("StartDate", result.getString("StartDate"));
				dataset.put("EndDate", result.getString("EndDate"));	
				dataset.put("Status", result.getString("Status"));
			}
			result.close();
			statement.close();
		}
			
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}	
		finally{						
			SqlConnection.closeConnection();
		}
	return dataset;
	}
	
	public HashMap<String,Object> getApprovedTrainings()
	{
		HashMap<String,Object> dataset = new HashMap<String, Object>();
	
		try {
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   (" SELECT [ID]"
					   		+ ",[TrainerID]"
					   		+ ",[TraineeID]"
					   		+ ",[SkillID] "
					   		+ ",(SELECT s.[Desc] FROM [ProjectHR].[dbo].Skills s WHERE S.ID = t.SkillID) AS Skill"
					   		+ ",[StartDate]"
					   		+ ",[EndDate]"
					   		+ ",[Status]"
					   		+ "FROM [ProjectHR].[dbo].[Training] t "
					   		+ "WHERE t.Status != 'Not-Approved'");	
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()){
				dataset.put("ID", result.getString("ID"));
				dataset.put("TrainerID", result.getString("TrainerID"));
				dataset.put("TraineeID", result.getString("TraineeID"));
				dataset.put("SkillID", result.getString("SkillID"));
				dataset.put("Skill", result.getString("Skill"));
				dataset.put("StartDate", result.getString("StartDate"));
				dataset.put("EndDate", result.getString("EndDate"));
				dataset.put("Status", result.getString("Status"));				
			}
			result.close();
			statement.close();
		}
			
		catch (SQLException e) {			
			e.printStackTrace();			
		}	
		finally{						
			SqlConnection.closeConnection();
		}
	return dataset;
	}
	
	public HashMap<String,Object> getAppraisalsOfDepartment(int deptID)
	{
		HashMap<String,Object> dataset = new HashMap<String, Object>();
	
		try {
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   (" SELECT [EmpID]"
					   		+ ",[ManagerID]"
					   		+ ",[Rating]"
					   		+ "[ProjectHR].[dbo].[Appraisal]"
					   		+ "WHERE [DeptID] = "+deptID);					  
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()){
				dataset.put("EmpID", result.getString("EmpID"));
				dataset.put("ManagerID", result.getString("ManagerID"));
				dataset.put("Rating", result.getInt("Rating"));							
			}
			result.close();
			statement.close();
		}
			
		catch (SQLException e) {			
			e.printStackTrace();			
		}	
		finally{						
			SqlConnection.closeConnection();
		}
	return dataset;
	}
	
	public String getEmailId(int Id){
		
		String emailId = "";
		try{
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   ("SELECT [Email] "
					   		+ "FROM [ProjectHR].[dbo].[Employee] "
					   		+ "WHERE ID = "+Id);
			
			ResultSet result = statement.executeQuery();
			while(result.next()){
				emailId = result.getString("Email");
			}			
		}
		catch (SQLException e) {			
			e.printStackTrace();			
		}	
		finally{						
			SqlConnection.closeConnection();
		}
		return emailId;
	}	
	
	public boolean updateEmpSkill(int ID, int SkillId){
		try{
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   ("IF EXISTS(SELECT 1 FROM [ProjectHR].[dbo].[EmployeeSkill] WHERE EmpID="+ID+" AND SkillID = "+SkillId+") "
					   		+ "RETURN "
					   		+ "ELSE "
					   		+ "INSERT INTO [ProjectHR].[dbo].[EmployeeSkill] VALUES ( "+ID+","+SkillId+")");
			
		}
		catch (SQLException e) {			
			e.printStackTrace();
			return false;
		}	
		finally{						
			SqlConnection.closeConnection();
		}
		return true;
	}
	
	public boolean approveTrainingStatus(int Id){
		try{
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   ("UPDATE [ProjectHR].[dbo].[Training] "
					   		+ "SET STATUS = 'Pending' "
					   		+ "WHERE ID = "+Id);
			
		}
		catch (SQLException e) {			
			e.printStackTrace();	
			return false;
		}	
		finally{						
			SqlConnection.closeConnection();
		}
		return true;
	}
	
	public boolean rejectTraining(int Id){
		try{
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   ("UPDATE [ProjectHR].[dbo].[Training] "
					   		+ "SET STATUS = 'Rejected' "
					   		+ "WHERE ID = "+Id);			
		}
		catch (SQLException e) {			
			e.printStackTrace();	
			return false;
		}	
		finally{						
			SqlConnection.closeConnection();
		}
		return true;
	}
	
	public HashMap<String,Object> getResignedEmployeesDetails()
	{
		HashMap<String,Object> dataset = new HashMap<String, Object>();
	
		try {
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   (" SELECT [ID],"
					   		+ "[FirstName],"
					   		+ "[LastName],"					   		
					   		+ "[PhoneNumber],"
					   		+ "[Email],"
					   		+ "[ManagerId]"					   		
					   		+ "FROM ProjectHR].[dbo].[Employee]  "
					   		+ "WHERE Status = 'Resigned'");	
			
			ResultSet result = statement.executeQuery();
			
			while(result.next()){
				dataset.put("ID", result.getString("ID"));
				dataset.put("FirstName", result.getString("FirstName"));
				dataset.put("LastName", result.getString("LastName"));
				dataset.put("PhoneNumber", result.getString("PhoneNumber"));
				dataset.put("Email", result.getString("Email"));
				dataset.put("ManagerId", result.getString("ManagerId"));				
			}
			result.close();
			statement.close();
		}
			
		catch (SQLException e) {
			e.printStackTrace();			
		}	
		finally{						
			SqlConnection.closeConnection();
		}
	return dataset;
	}
	
	public boolean approveResignation(int Id){
		try{
			sqlConnection = SqlConnection.getConnection();
			
			statement = sqlConnection.prepareStatement
					   ("UPDATE [ProjectHR].[dbo].[Employee] "
					   		+ "SET Status = 'Inactive' "
					   		+ "WHERE ID="+Id);			
		}
		catch (SQLException e) {			
			e.printStackTrace();	
			return false;
		}	
		finally{						
			SqlConnection.closeConnection();
		}
		return true;
	}
	
	// Method to get User Login Details - Starts here
		public String[] getLoginDetails(String userName, String password) {
			String[] userDetails = new String[2];
			PreparedStatement preprdStmt = null;
			String selectquery = "SELECT [UserName],[Password] FROM Login WHERE UserName = ? and Password = ?";

			try {
				sqlConnection = SqlConnection.getConnection();
				
				if (userName != null && password != null) {
					preprdStmt = sqlConnection.prepareStatement(selectquery);
					preprdStmt.setString(1, userName);
					preprdStmt.setString(2, password);

					ResultSet rs = preprdStmt.executeQuery();

					if (rs.next()) {
						userDetails[0] = rs.getString("UserName");
						userDetails[1] = rs.getString("Password");
						// just to check
						// System.out.println(userDetails[0] + " " +
						// userDetails[1]);
					} else {
						userDetails = null;
					}
				}
			} catch (SQLException err) {
				err.printStackTrace();
			}
			finally{						
				SqlConnection.closeConnection();
			}
			return userDetails;
		}
		// Method to get Login Details - Ends here

		// Method to get employee ID Details - Starts here
		public int getEmployeeID(int employeeID) {
			int emploIDOutput = -1;
			PreparedStatement preprdStmt = null;
			String selectquery = "SELECT [empID] FROM Employee WHERE empID = ?";

			try {
				sqlConnection = SqlConnection.getConnection();
				preprdStmt = sqlConnection.prepareStatement(selectquery);
				preprdStmt.setInt(1, employeeID);

				ResultSet rs = preprdStmt.executeQuery();

				if (rs.next()) {
					emploIDOutput = rs.getInt("empID");

					// just to check
					// System.out.println("Employee ID from DB is " +
					// emploIDOutput);
				}
			} catch (SQLException err) {
				err.printStackTrace();
			}
			finally{						
				SqlConnection.closeConnection();
			}
			return emploIDOutput;
		}
		// Method to get employee ID Details - Ends here

		// Method to get all employee Details - Starts here
		public void getEmployeeDetails(int employee_id) {

			PreparedStatement preprdStmt = null;
			String selectquery = "SELECT * FROM Employee WHERE empID = ?";

			try {
				sqlConnection = SqlConnection.getConnection();
				preprdStmt = sqlConnection.prepareStatement(selectquery);
				preprdStmt.setInt(1, employee_id);

				ResultSet rs = preprdStmt.executeQuery();

				if (rs.next()) {

					System.out.println("Employee ID		: " + rs.getInt("empID"));
					System.out.println("First name		: " + rs.getString("Firstname"));
					System.out.println("Last name		: " + rs.getString("Lastname"));
					System.out.println("Date of birth		: " + rs.getDate("DOB"));
					System.out.println("Phone Number		: " + rs.getInt("PhoneNumber"));
					System.out.println("Email			: " + rs.getString("Email"));
					System.out.println("Address			: " + rs.getString("Address"));
					System.out.println("Contact number		: " + rs.getInt("EmergencyContactNumber"));
					System.out.println("Marital status		: " + rs.getString("FamilyStatus"));
					System.out.println("Dept. ID		: " + rs.getInt("Deptid"));
					System.out.println("Designation		: " + rs.getString("Designation"));
					System.out.println("Manager ID		: " + rs.getInt("ManagerId"));
					// System.out.println("Role ID : " + rs.getString("Role"));
					System.out.println("Status			: " + rs.getString("Status"));
				}
			} catch (SQLException err) {
				err.printStackTrace();
			}
			finally{						
				SqlConnection.closeConnection();
			}
		}
		// Method to get all employee Details - Starts here

		// Method to edit employee Details - Starts here
		public void editEmployeeDetails(int employee_id) {

			PreparedStatement preprdStmt = null;
			String selectquery = "select * from employee";

			try {
				sqlConnection = SqlConnection.getConnection();
				preprdStmt = sqlConnection.prepareStatement(selectquery);

				ResultSet rs = preprdStmt.executeQuery();
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				String empTableNames[] = new String[colCount + 1];

				// The column count starts from 1
				for (int i = 1; i <= colCount; i++) {
					empTableNames[i - 1] = rsmd.getColumnName(i);
					// System.out.println(empTableNames[i-1]);

					if ((i != 1) && (i < colCount)) {
						String stringPlaceHolder = null;
						int intPlaceHolder = 0;
						Date datePlaceHolder = null;

						System.out.println("Enter the new " + empTableNames[i - 1]);

						PreparedStatement preprdStmt2 = null;
						String selectquery2 = "update employee set " + empTableNames[i - 1] + " = ? where empID = ?";
						preprdStmt2 = sqlConnection.prepareStatement(selectquery2);

						if (i == 5 || i == 8 || i == 10 || i == 12) {
							intPlaceHolder = input.nextInt();
							preprdStmt2.setInt(1, intPlaceHolder);
						} else if (i == 4) {
							DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
							String tempString = input.next();

							try {
								datePlaceHolder = format.parse(tempString);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							preprdStmt2.setDate(1, (java.sql.Date) datePlaceHolder);
						} else {
							stringPlaceHolder = input.next();
							preprdStmt2.setString(1, stringPlaceHolder);
						}

						preprdStmt2.setInt(2, employee_id);
						preprdStmt2.executeUpdate();
					}
				}
			} catch (SQLException err) {
				err.printStackTrace();
			}
			finally{						
				SqlConnection.closeConnection();
			}
		}
		// Method to edit employee Details - Ends here

		// Method to insert employee Details - Starts here

		public void insertEmployeeDetails() {
			PreparedStatement preprdStmt = null;
			String selectquery = "insert into Employee (empID, Firstname, Lastname, " + "DOB, PhoneNumber, Email, "
					+ "Address, EmergencyContactNumber, " + "FamilyStatus, Deptid, Designation, ManagerId, Status)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try {
				sqlConnection = SqlConnection.getConnection();
				preprdStmt = sqlConnection.prepareStatement(selectquery);

				System.out.println("Enter the following details");

				System.out.println("Employee ID		: ");
				preprdStmt.setString(1, input.next());

				System.out.println("First name		: ");
				preprdStmt.setString(2, input.next());

				System.out.println("Last name		: ");
				preprdStmt.setString(3, input.next());

				System.out.println("Date of birth		: ");
				Date dateInput = null;
				DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
				String tempDate = input.next();
				try {
					dateInput = format.parse(tempDate);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				preprdStmt.setDate(4, (java.sql.Date) dateInput);

				System.out.println("Phone Number		: ");
				preprdStmt.setInt(5, input.nextInt());

				System.out.println("Email			: ");
				preprdStmt.setString(6, input.next());

				System.out.println("Address			: ");
				preprdStmt.setString(7, input.next());

				System.out.println("Contact number		: ");
				preprdStmt.setInt(8, input.nextInt());

				System.out.println("Marital status		: ");
				preprdStmt.setString(9, input.next());

				System.out.println("Dept. ID		: ");
				preprdStmt.setInt(10, input.nextInt());

				System.out.println("Designation		: ");
				preprdStmt.setString(11, input.next());

				System.out.println("Manager ID		: ");
				preprdStmt.setInt(12, input.nextInt());

				System.out.println("Status			: ");
				preprdStmt.setString(13, input.next());

				preprdStmt.execute();

			} catch (SQLException err) {
				err.printStackTrace();
			}
			finally{						
				SqlConnection.closeConnection();
			}

		}

}

