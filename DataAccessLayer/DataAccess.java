import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.*;

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
					(" IF EXISTS ( SELECT 1 FROM [ProjectHR].[dbo].[Department] WHERE ID = "+deptID +") "
							+ "BEGIN "
							+ "SELECT [EmpID], "
							+ "[ManagerID] ,"
							+ "[Rating] "
							+ "FROM [ProjectHR].[dbo].[Appraisal] "
							+ "WHERE [DeptID] =  "+deptID
							+ " END ");
												  

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
							+ "FROM [ProjectHR].[dbo].[Employee]  "
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
					("  UPDATE [ProjectHR].[dbo].[Employee] "
							+ "SET Status = 'In-Active' "
							+ "WHERE ID = "+Id);	
			
			statement.execute();
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

	public int getEmployeeID(int employeeID) {
		int emploIDOutput = -1;
		PreparedStatement preprdStmt = null;
		String selectquery = "SELECT [ID] FROM Employee WHERE ID = ?";

		try {
			sqlConnection = SqlConnection.getConnection();
			preprdStmt = sqlConnection.prepareStatement(selectquery);
			preprdStmt.setInt(1, employeeID);

			ResultSet rs = preprdStmt.executeQuery();

			if (rs.next()) {
				emploIDOutput = rs.getInt("ID");

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

	public void getEmployeeDetails(int employee_id) {

		PreparedStatement preprdStmt = null;
		String selectquery = "SELECT * FROM Employee WHERE ID = ?";

		try {
			sqlConnection = SqlConnection.getConnection();
			preprdStmt = sqlConnection.prepareStatement(selectquery);
			preprdStmt.setInt(1, employee_id);

			ResultSet rs = preprdStmt.executeQuery();

			if (rs.next()) {

				System.out.println("Employee ID		: " + rs.getInt("ID"));
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

				if ((i != 1) && (i < colCount)) {
					String stringPlaceHolder = null;
					int intPlaceHolder = 0;
					java.sql.Date datePlaceHolder = null;

					System.out.println("Enter the new " + empTableNames[i - 1]);

					PreparedStatement preprdStmt2 = null;
					String selectquery2 = "update employee set " + empTableNames[i - 1] + " = ? where ID = ?";
					preprdStmt2 = sqlConnection.prepareStatement(selectquery2);

					if (i == 10 || i == 12) {
						intPlaceHolder = input.nextInt();
						preprdStmt2.setInt(1, intPlaceHolder);
					} else if (i == 4) {						
						String tempString = input.next();
						String.format("yyyy-mm-dd", tempString);

						try {
							datePlaceHolder = java.sql.Date.valueOf(tempString);
						} catch (Exception e) {
							
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

	public void insertEmployeeDetails() {
		PreparedStatement preprdStmt = null;
		String selectquery = "insert into Employee ("
				+ "Firstname, "
				+ "Lastname, " 
				+ "DOB, "
				+ "PhoneNumber, "
				+ "Email, "
				+ "Address, "
				+ "EmergencyContactNumber, " 
				+ "FamilyStatus, "
				+ "Deptid, "
				+ "Designation, "
				+ "ManagerId, "
				+ "Status)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			sqlConnection = SqlConnection.getConnection();
			preprdStmt = sqlConnection.prepareStatement(selectquery);

			System.out.println("Enter the following details");

			System.out.println("First name		: ");
			preprdStmt.setString(1, input.next());

			System.out.println("Last name		: ");
			preprdStmt.setString(2, input.next());

			System.out.println("Date of birth		: ");
			java.sql.Date dateInput = null;
			//DateFormat format = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
			String tempDate = input.next();
			String.format("yyyy-mm-dd", tempDate);
			try {
				dateInput = java.sql.Date.valueOf(tempDate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preprdStmt.setDate(3, (java.sql.Date) dateInput);

			System.out.println("Phone Number		: ");
			preprdStmt.setString(4, input.next());

			System.out.println("Email			: ");
			preprdStmt.setString(5, input.next());

			System.out.println("Address			: ");
			preprdStmt.setString(6, input.next());

			System.out.println("Contact number		: ");
			preprdStmt.setString(7, input.next());

			System.out.println("Marital status		: ");
			preprdStmt.setString(8, input.next());

			System.out.println("Dept. ID		: ");
			preprdStmt.setInt(9, input.nextInt());

			System.out.println("Designation		: ");
			preprdStmt.setString(10, input.next());

			System.out.println("Manager ID		: ");
			preprdStmt.setInt(11, input.nextInt());

			System.out.println("Status			: ");
			preprdStmt.setString(12, input.next());

			preprdStmt.execute();

		} catch (SQLException err) {
			err.printStackTrace();
		}
		finally{						
			SqlConnection.closeConnection();
		}

	}

	public void insertJobDetails(String jobDesc,int managerID,int vacancyNum,boolean Status){
		sqlConnection = SqlConnection.getConnection();
		PreparedStatement preprdStmt = null;			

		String insertQuery = " INSERT INTO JOB"
				+ "(Descrip, ManagerID, VacancyNum, Status)"  
				+ " values (?, ?, ?, ?)";

		try{
			if (jobDesc != null && managerID != 0 && vacancyNum != 0) {
				preprdStmt = sqlConnection.prepareStatement(insertQuery);
				preprdStmt.setString(1,jobDesc);
				preprdStmt.setInt(2,managerID);
				preprdStmt.setInt(3,vacancyNum);
				preprdStmt.setBoolean(4,Status);

				preprdStmt.executeUpdate();
			}
		}

		catch (Exception e)

		{
			e.printStackTrace();
			System.err.println(e.getMessage());

		}
		finally{						
			SqlConnection.closeConnection();
		}

	}

	public void deleteJobDetails(int jobID,int managerID,int vacancyNum,boolean Status){

		sqlConnection = SqlConnection.getConnection();
		PreparedStatement preprdStmt = null;

		String deleteQuery = "DELETE FROM JOB WHERE ID = ?";

		try{

			if (jobID != 0) {

				preprdStmt = sqlConnection.prepareStatement(deleteQuery);
				preprdStmt.setInt(1,jobID);
				preprdStmt.executeUpdate();
			}
		}

		catch (Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		finally{						
			SqlConnection.closeConnection();
		}
	}

	public void updateJobDetails(int jobID,String jobDesc,int managerID,int vacancyNum,boolean Status){

		sqlConnection = SqlConnection.getConnection();
		PreparedStatement preprdStmt = null;

		String updateQuery = "UPDATE Job SET Descrip = ?, ManagerID = ?, VacancyNum = ?, Status = ? WHERE ID = ?";

		try{

			if (jobID != 0) {

				preprdStmt = sqlConnection.prepareStatement(updateQuery);

				preprdStmt.setString(1,jobDesc);
				preprdStmt.setInt(2,managerID);
				preprdStmt.setInt(3,vacancyNum);
				preprdStmt.setBoolean(4,Status);

				System.out.println(jobID);
				preprdStmt.setInt(5,jobID);

				int row = preprdStmt.executeUpdate();

				System.out.println(row);

			}
		}
		catch (Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		finally{						
			SqlConnection.closeConnection();
		}
	}

	public Map <Integer, String> getJobDetails(){

		Map <Integer, String> dictionary = new HashMap <Integer, String>();
		sqlConnection = SqlConnection.getConnection();
		PreparedStatement preprdStmt = null;

		String selectJobquery = " SELECT ID, Descrip from Job ";

		try {

			preprdStmt = sqlConnection.prepareStatement(selectJobquery);    
			ResultSet rs = preprdStmt.executeQuery();

			while (rs.next()){
				dictionary.put(rs.getInt("ID"), rs.getString("Descrip"));
			}
			return dictionary;
		}
		catch (SQLException err) {
			err.printStackTrace();
			return null;
		}
		finally{						
			SqlConnection.closeConnection();
		}
	}

	public void insertApplicantDetails(String firstName,String lastName,int phone,String email,String resume,int JobID) {

		PreparedStatement preprdStmt = null; 
		sqlConnection = SqlConnection.getConnection();
		String SPsql = "EXEC insertApplicant ?, ?, ?, ?, ?, ?";
		try{

			if (firstName != null && lastName !=null && phone != 0 && email != null && resume != null && JobID !=0) {

				File inputFile = new File(resume);  
				FileInputStream inStream = new FileInputStream(inputFile);
				int len = (int)inputFile.length();


				preprdStmt = sqlConnection.prepareStatement(SPsql);
				preprdStmt.setString(1,firstName);
				preprdStmt.setString(2,lastName);
				preprdStmt.setInt(3,phone);
				preprdStmt.setString(4,email);
				preprdStmt.setBinaryStream(5, inStream, len);
				preprdStmt.setInt(6,JobID);

				preprdStmt.executeUpdate();    
				inStream.close();            
			}  
		}    
		catch (Exception e)   {
			e.printStackTrace();
			System.err.println(e.getMessage());    
		}
		finally{						
			SqlConnection.closeConnection();
		}
	}

	public ArrayList<ApplicantsModel> getApplDetails(){

		ArrayList<ApplicantsModel> lst = new ArrayList<ApplicantsModel>();

		sqlConnection = SqlConnection.getConnection();
		PreparedStatement preprdStmt = null;

		String sql = "EXEC getApplicantDetails";

		try {	    
			preprdStmt = sqlConnection.prepareStatement(sql);    
			ResultSet rs = preprdStmt.executeQuery();

			while (rs.next()){
				ApplicantsModel app =  new ApplicantsModel();
				app.firstName = rs.getString("FirstName");
				app.lastName = rs.getString("LastName");
				app.phone = rs.getInt("Phone");
				app.email = rs.getString("Email");
				app.status = rs.getInt("Status");
				app.score = rs.getInt("Score");
				app.jobDes = rs.getString("Descrip");
				app.statusDes =rs.getString("Description");
				lst.add(app); 	  
			}
			return lst;	          
		}
		catch (SQLException err) {		
			err.printStackTrace();
			return null;		  
		}
		finally{						
			SqlConnection.closeConnection();
		}
	}

	public void updateApplDetails(int status,int score, int JobID,int AppID){

		String sqlupd = "{call updateJobAppliedDetails(?,?,?,?)}";
		sqlConnection = SqlConnection.getConnection();

		try{
			if (JobID != 0 ) {

				CallableStatement callableStatement = sqlConnection.prepareCall(sqlupd);
				callableStatement.setInt(1, status);
				callableStatement.setInt(2, score);
				callableStatement.setInt(3, JobID);
				callableStatement.setInt(4, AppID);

				callableStatement.executeUpdate();	    
			}
		}

		catch (Exception e){
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		finally{						
			SqlConnection.closeConnection();
		}
	}

	public Map <String, String> getSelectedCandidates()
	{

		Map <String, String> dictionary = new HashMap <String, String>();

		PreparedStatement preprdStmt = null;
		sqlConnection = SqlConnection.getConnection();

		String selectAppquery = " Exec checkStatus ";		
		try {	    
			preprdStmt = sqlConnection.prepareStatement(selectAppquery);    
			ResultSet rs = preprdStmt.executeQuery();

			while (rs.next())
			{	          
				dictionary.put(rs.getString("FirstName"), rs.getString("Email"));	                
			} 
			return dictionary;	          
		}
		catch (SQLException err)  {		
			err.printStackTrace();
			return null;		  
		}
		finally{						
			SqlConnection.closeConnection();
		}
	}


	public HashMap<String,Object> getPayrollDetails(int empId)
	{
		HashMap<String,Object> dataset = new HashMap<String, Object>();

		try {
			sqlConnection = SqlConnection.getConnection();

			statement = sqlConnection.prepareStatement
					(" SELECT "
							+ "[EmployeeName],"
							+ "[Designation],"					   		
							+ "[BankAccountNumber],"
							+ "[DateOfJoining],"
							+ "[BasicSalary],"
							+"[MedicalAllowance],"
							+ "[RentAllowance],"
							+ "[GrossSalary],"
							+ "[NetPay],"
							+ "[Deductions]"
							+ "FROM [ProjectHR].[dbo].Payroll  "
							+ "WHERE [EmployeeID] = "+empId);	

			ResultSet result = statement.executeQuery();

			while(result.next()){
				dataset.put("EmployeeName", result.getString("EmployeeName"));
				dataset.put("Designation", result.getString("Designation"));
				dataset.put("BankAccountNumber", result.getString("BankAccountNumber"));
				dataset.put("DateOfJoining", result.getString("DateOfJoining"));
				dataset.put("BasicSalary", result.getString("BasicSalary"));
				dataset.put("MedicalAllowance", result.getString("MedicalAllowance"));
				dataset.put("RentAllowance", result.getString("RentAllowance"));
				dataset.put("GrossSalary", result.getString("GrossSalary"));
				dataset.put("NetPay", result.getString("NetPay"));
				dataset.put("Deductions", result.getString("Deductions"));			
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



}

