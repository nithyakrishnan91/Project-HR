import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Payroll {
	
	PayrollModel payroll = new PayrollModel();
	Scanner input = new Scanner(System.in);
	
	@SuppressWarnings("deprecation")
	private void generatePayslipEveryMonth(){
		
		Date day = new Date();
	/*	
		if(day.getDate()== 18){
	
		payroll.generatePdfReport();
		}
		*/
	}
	
	public void payrollMenu(){
		System.out.println("Press 1 to view payslip");
		System.out.println("Press 2 to create Payslip pdf \n\n");		

		int payrollChoice;
		payrollChoice = input.nextInt();

		if (payrollChoice == 1) {
			
			System.out.println("Please enter your Employee ID");
			int employee_id = input.nextInt();

			try {
				if(payroll.employeeIDCheck(employee_id)){
					printPayroll(employee_id);
				}
				else{
					System.out.println("Invalid Employee Id");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			}
		 else if (payrollChoice == 2) {
			System.out.println("Please enter the details of the employee you wish to add");
			payroll.generatePdfReport();	 
		}
		 else{
			 System.out.println("Invalid Entry!!");
		 }
	}
	private void printPayroll(int id){

		HashMap<String,Object> data = payroll.viewPayslip(id);
		Object[] columns = data.keySet().toArray();
		Object[] values = data.values().toArray();

		String leftAlignFormat = "| %-10s ";


		for (int i = 0; i < columns.length; i++) {
			System.out.format("|	"+ columns[i]+"	");		
		}
		System.out.format("%n+-------------------------------------------------------------------------+%n");
		for (int i = 0; i < values.length; i++) {
			System.out.format(leftAlignFormat, values[i].toString());
		}
		System.out.format("%n+-------------------------------------------------------------------------+%n");

	}
}
