
import java.util.HashMap;
import java.util.Scanner;


public class Trainings {

	static TrainingModel model = new TrainingModel();
	Scanner input = new Scanner(System.in);

	public void trainingsMenu(){

		try {
			char menuChoice = 'y';

			while(menuChoice=='y'){		

				System.out.println("Press 1 to view the new trainings assigned and to approve");
				System.out.println("Press 2 to view the approved trainings");

				int choice = input.nextInt();

				if(choice==1){
					printApprovalTrainings();

					System.out.println("Press 1 to approve trainings");			
					System.out.println("Press 2 to go back");

					int trainingChoice = input.nextInt();

					if(trainingChoice==1){
						System.out.println("Enter Employee Id: ");
						int id = input.nextInt();
						approveTraining(id);
						System.out.println("Approved Trainings for Employee Id: "+id);
						menuChoice = 'n';
					}
					else if(trainingChoice == 2){
						menuChoice = 'y';
					}
				}			
				else if(choice==2){
					printApprovedTrainings();
					menuChoice = 'n';
				}
				else{
					System.out.println("Invalid choice");	
				}

			}
		} catch (Exception e) {		
			e.printStackTrace();
		}
	}

	private void printApprovalTrainings(){

		HashMap<String,Object> data = model.getApprovalTrainings();
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

	private void printApprovedTrainings(){
		HashMap<String,Object> data = model.getApprovedTrainings();
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

	private void approveTraining(int id){
		model.approveTrainingsAssigned(id);
	}
}
