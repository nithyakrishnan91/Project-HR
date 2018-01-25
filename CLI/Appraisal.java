import java.util.Scanner;

public class Appraisal {

	static AppraisalModel model = new AppraisalModel();
	Scanner input = new Scanner(System.in);
	public void AppraisalMenu(){

		try{

			System.out.println("Enter Department ID: ");
			int deptId = input.nextInt();

			model.getAppraisalsOfDept(deptId);

		}
		catch (Exception e) {		
			e.printStackTrace();
		}
	}
}

