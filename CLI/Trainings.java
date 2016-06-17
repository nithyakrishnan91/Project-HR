
import java.util.HashMap;


public class Trainings {
	
	static TrainingModel model = new TrainingModel();

	public static void main(String[] args) {
		
		printApprovalTrainings();	
		
	}
	
	private static void printApprovalTrainings(){
		
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

}
