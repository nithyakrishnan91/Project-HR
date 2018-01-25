import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class MainPortalHR {
	static LoginModel userlogin = new LoginModel();
	static Employee employee = new Employee();
	static RecruitmentAndJob recruitment = new RecruitmentAndJob();
	static Trainings training = new Trainings();
	static Appraisal appraisal = new Appraisal();
	static Seperation seperation = new Seperation();
	static Payroll payroll = new Payroll();

	@SuppressWarnings({ "static-access" })
	public static void main(String[] args) throws SQLException, AddressException, MessagingException {

		Login login = new Login();

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
			boolean isLoginValid  = login.isValidLogin();
			printEmptyLines();

			if (isLoginValid) {
				while (returnToMainScr == 'y') {
					System.out.println("Welcome to the HR Portal");
					System.out.println();

					System.out.println("Press 1 to enter 'Employee Module' ");
					System.out.println("Press 2 to enter 'Payroll & Compensation Module' ");
					System.out.println("Press 3 to enter 'Competency Development & Appraisal Module' ");
					System.out.println("Press 4 to enter 'Recruitment & Job Posting Module' ");
					System.out.println("Press 5 to enter 'Employee Seperation' ");

					printEmptyLines();

					System.out.println("Please enter your choice: ");

					int moduleChoice;
					moduleChoice = input.nextInt();

					switch (moduleChoice) {
					case 1:
						System.out.println("Welcome to Employee Module");
						printEmptyLines(); 
						employee.employeeMenu();
						System.out.println("Do you wish to continue operating the HR portal y/n?");
						returnToMainScr = input.next().charAt(0);
						break;
				

					case 2:
						System.out.println("Welcome to Payroll and Compensation Module");
						printEmptyLines(); 

						System.out.println("Press 1 to view payroll details of an employee");
						System.out.println("Press 2 to print payroll details of all employees");
						printEmptyLines();
							
						payroll.payrollMenu();
						
							System.out.println("Do you wish to continue operating the HR portal y/n?");
							returnToMainScr = input.next().charAt(0);
							break;

						case 3:
							System.out.println("Welcome to Competancy Development and Appraisal Module");
							printEmptyLines(); 

							System.out.println("Press 1 for Competancy Development");
							System.out.println("Press 2 for Appraisal");

							printEmptyLines();

							int choice;
							choice = input.nextInt();

							if(choice==1){
								training.trainingsMenu();
							}

							else if(choice ==  2){
								appraisal.AppraisalMenu();
							}

							else{
								System.out.println("Invalid Entry!");
							}

							printEmptyLines();

							System.out.println("Do you wish to continue operating the HR portal y/n?");
							returnToMainScr = input.next().charAt(0);
							break;

						case 4:
							System.out.println("Welcome to Recruitment & Job Posting Module");
							printEmptyLines(); 
							recruitment.recruitmentMenu();
							System.out.println("Do you wish to continue operating the HR portal y/n?");
							returnToMainScr = input.next().charAt(0);
							break;

						case 5:
							System.out.println("Welcome to Recruitment & Job Posting Module");
							seperation.seperationMenu();
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
