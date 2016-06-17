import java.util.HashMap;

public class TrainingModel {
	
	DataAccess dataAccess;
	
	public HashMap<String,Object> getApprovalTrainings(){
		HashMap<String,Object> dataset = new HashMap<String,Object>();		
		try {
			 dataAccess = new DataAccess();	
			 dataset = dataAccess.getTrainingsToApprove();			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}	
		return dataset;		
	}
	
	public HashMap<String,Object> getApprovedTrainings(){
		HashMap<String,Object> dataset = new HashMap<String,Object>();		
		try {
			 dataAccess = new DataAccess();
			 dataset = dataAccess.getApprovedTrainings();	
			 
			 for(int i=0;i<dataset.size();i++){
				 if(dataset.get("Status").equals("Completed")){
					 updateEmployeeSkill((int)dataset.get("TraineeID"),(int)dataset.get("SkillID"));
				 }
			 }
		} 
		catch(Exception e) {
			e.printStackTrace();
		}	
		return dataset;		
	}
	
	public void approveTrainingsAssigned(int Id){
		try {
			 dataAccess = new DataAccess();
			 if(!dataAccess.approveTrainingStatus(Id)){
				 System.out.println("Error in approving the training");
			 }			 
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void rejectTrainingsAssigned(int Id){
		try {
			 dataAccess = new DataAccess();
			 if(!dataAccess.rejectTraining(Id)){
				 System.out.println("Error in rejecting the training");
			 }			 
		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	
	private void updateEmployeeSkill(int EmpId, int SkillId){
		dataAccess = new DataAccess();
		if(!dataAccess.updateEmpSkill(EmpId, SkillId)){
			System.out.println("Error in updating the employee skill");
		}			
	}
}
