import java.sql.SQLException;

public class EmployeeModel {	

	String[] employeeDetails = new String[11];

	static DataAccess dataobj = new DataAccess();
	// Getting Employee table column names

	// Employee ID Check
	public Boolean employeeIDCheck(int emloyeeID) throws SQLException {
		try {
			int details = dataobj.getEmployeeID(emloyeeID);
			if (details != -1)
				return true;
			else
				return false;
		} catch (Exception se) {
			se.getMessage();
			return false;
		}
	}

	// Viewing the details of the employee by pulling it from the DB
	public void viewDetails(int employee_id) {
		dataobj.getEmployeeDetails(employee_id);
	}

	// Editing the details of the employee by pushing it to the DB
	public void editDetails(int employee_id) {
		dataobj.editEmployeeDetails(employee_id);
		// dataobj.printDetails(employee_id);
	}

	public void insertNewEmployee() {
		dataobj.insertEmployeeDetails();
	}

}
