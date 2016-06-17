import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class MainPortalHR {
	static LoginModel userlogin = new LoginModel();
	static EmployeeModel empObj = new EmployeeModel();

	@SuppressWarnings({ "static-access" })
	public static void main(String[] args) throws SQLException {

		// Main Welcome Screen
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);

		char returnToHRportalScr = 'y';
		char returnToMainScr = 'y';

		while (returnToHRportalScr == 'y' && returnToMainScr == 'y') {
			System.out.println("-------------------------");
			System.out.println("Welcome to the HR Portal");
			System.out.println("-------------------------");

			printEmptyLines();

			// Get login details
			System.out.println("Please Enter your User Name: ");
			String userName = input.next();

			System.out.println("Please Enter your Password: ");
			String password = input.next();

			boolean isLoginValid = false;
			isLoginValid = userlogin.loginDetailCheck(userName, password);

			printEmptyLines();

			if (isLoginValid) {
				while (returnToMainScr == 'y') {
					System.out.println("Welcome to the HR Portal");
					System.out.println();

					System.out.println("Press 1 to enter 'Employee Module' ");
					System.out.println("Press 2 to enter 'Payroll & Compensation Module' ");
					System.out.println("Press 3 to enter 'Competency Development Module' ");
					System.out.println("Press 4 to enter 'Recruitment & Job Posting Module' ");

					printEmptyLines();
					
					System.out.println("Please enter your choice: ");

					int moduleChoice;
					moduleChoice = input.nextInt();

					switch (moduleChoice) {
					case 1:
						System.out.println("Welcome to Employee Module");
						printEmptyLines(); 
						
						System.out.println("Press 1 to view or edit details of an employee");
						System.out.println("Press 2 to create a new employee");
						printEmptyLines();

						int employeeModuleChoice;
						employeeModuleChoice = input.nextInt();

						if (employeeModuleChoice == 1) {
							String view_edit_choice;
							System.out.println("Type 'v' to view or 'e' to edit employee details");
							view_edit_choice = input.next();

							System.out.println("Please enter your Employee ID");
							int employee_id = input.nextInt();

							// The entered employee ID <emp_id> is validated for
							// its presence in the DB
							boolean isEmployeeIDValid = false;
							try {
								isEmployeeIDValid = empObj.employeeIDCheck(employee_id);
							} catch (SQLException e) {
								e.printStackTrace();
							}

							if (isEmployeeIDValid && (Objects.equals(view_edit_choice, new String("v")))) {
								empObj.viewDetails(employee_id);
								System.out.println("Do you wish to continue operating the HR portal y/n?");
								returnToMainScr = input.next().charAt(0);
								;
							}

							else if (isEmployeeIDValid && (Objects.equals(view_edit_choice, new String("e")))) {
								empObj.editDetails(employee_id);
								System.out.println("Do you wish to continue operating the HR portal y/n?");
								returnToMainScr = input.next().charAt(0);
								;
							}

							else {
								System.out.println("Please enter a valid employee ID and a valid choice (v) or (e)");
								System.out.println("Do you wish to continue operating the HR portal y/n?");
								returnToMainScr = input.next().charAt(0);
							}
						} else if (employeeModuleChoice == 2) {
							System.out.println("Please enter the details of the employee you wish to add");
							empObj.insertNewEmployee();

							System.out.println("Do you wish to continue operating the HR portal y/n?");
							returnToMainScr = input.next().charAt(0);
						}
						break;

					case 2:
						System.out.println("Call the function to enter Payroll & Compensation Module");
						// Insert the call for Payroll and Compensation Module
						// Method here
						System.out.println("Do you wish to continue operating the HR portal y/n?");
						returnToMainScr = input.next().charAt(0);
						break;

					case 3:
						System.out.println("Welcome to Competancy Development Module");
						printEmptyLines(); 
						
						System.out.println("Press 1 to view the new trainings assigned and to approve");
						System.out.println("Press 2 to view the approved trainings");
						printEmptyLines();

						int choice;
						choice = input.nextInt();

						// Insert the call for Competency development Module
						// Method here
						System.out.println("Do you wish to continue operating the HR portal y/n?");
						returnToMainScr = input.next().charAt(0);
						break;

					case 4:
						System.out.println("Call the function to enter Recruitment & Job Posting Module");
						// Insert the call for Recruitment Module Method here
						System.out.println("Do you wish to continue operating the HR portal y/n?");
						returnToMainScr = input.next().charAt(0);
						break;

					default:
						System.out.println("Invalid choice please start program again");
						System.out.println("Do you wish to continue operating the HR portal y/n?");
						returnToMainScr = input.next().charAt(0);
						break;

					}
				}
			} else {
				System.out.println("Invalid Credentials  - Do you wish to start again (y/n)?");
				returnToHRportalScr = input.next().charAt(0);
			}
		}
	}
	
	private static void printEmptyLines(){
		for (int i = 1; i < 2; i++) {
			System.out.println();
		} 
	}

}
