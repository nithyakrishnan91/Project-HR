import java.util.HashMap;
import java.util.Scanner;

public class Seperation {
	
	static SeperationModel model = new SeperationModel();
	Scanner input = new Scanner(System.in);

	public void seperationMenu(){

		try {
			char menuChoice = 'y';

			while(menuChoice=='y'){		

				System.out.println("Press 1 to view resigned employee details");				

				int choice = input.nextInt();

				if(choice==1){
					printResignedEmployees();

					System.out.println("Press 1 to approve");			
					System.out.println("Press 2 to go back");

					int trainingChoice = input.nextInt();

					if(trainingChoice==1){
						System.out.println("Enter Employee Id: ");
						int id = input.nextInt();
						approveResignation(id);
						System.out.println("Approved Resignation for Employee Id: "+id);
						menuChoice = 'n';
					}
					else if(trainingChoice == 2){
						menuChoice = 'y';
					}
				}			
				else{
					System.out.println("Invalid choice");	
				}

			}
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}

	private void printResignedEmployees(){

		HashMap<String,Object> data = model.getResignedEmployees();
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
	
	private void approveResignation(int id){		
		model.acceptResignation(id);		
	}

}
