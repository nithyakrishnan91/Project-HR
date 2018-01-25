import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Employee {
	
	static EmployeeModel empObj = new EmployeeModel();
	Scanner input = new Scanner(System.in);
	
	public void employeeMenu(){
		System.out.println("Press 1 to view or edit details of an employee");
		System.out.println("Press 2 to create a new employee \n\n");		

		int employeeModuleChoice;
		employeeModuleChoice = input.nextInt();

		if (employeeModuleChoice == 1) {
			String view_edit_choice;
			System.out.println("Type 'v' to view or 'e' to edit employee details");
			view_edit_choice = input.next();

			System.out.println("Please enter your Employee ID");
			int employee_id = input.nextInt();

			boolean isEmployeeIDValid = false;
			try {
				isEmployeeIDValid = empObj.employeeIDCheck(employee_id);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (isEmployeeIDValid && (Objects.equals(view_edit_choice, new String("v")))) {
				empObj.viewDetails(employee_id);
				
			}
			else if (isEmployeeIDValid && (Objects.equals(view_edit_choice, new String("e")))) {
				empObj.editDetails(employee_id);
			
			}					

			else {
				System.out.println("Please enter a valid employee ID and a valid choice (v) or (e)");
			}
				
			}
		 else if (employeeModuleChoice == 2) {
			System.out.println("Please enter the details of the employee you wish to add");
			empObj.insertNewEmployee();	 
		}
	}

}
