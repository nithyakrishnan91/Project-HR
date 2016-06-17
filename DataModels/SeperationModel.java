import java.util.HashMap;

public class SeperationModel {

	DataAccess dataAccess;
	
	public HashMap<String,Object> getResignedEmployees(){
		HashMap<String,Object> dataset = new HashMap<String,Object>();		
		try {
			 dataAccess = new DataAccess();	
			 dataset = dataAccess.getResignedEmployeesDetails();			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}	
		return dataset;		
	}
	
	public void acceptResignation(int id){
		try {
			 dataAccess = new DataAccess();	
			 if(!dataAccess.approveResignation(id)){
				 System.out.println("Error in approving the resignation");
			 }
		} 
		catch(Exception e) {
			e.printStackTrace();
		}			
	}
}
