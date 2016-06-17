import java.util.HashMap;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class AppraisalModel{	
	
	DataAccess dataAccess; 
	
	LowAppraisalComponent eventComponent = new LowAppraisalComponent();
	
	public HashMap<String,Object> getAppraisalsOfDept(int deptID){
		HashMap<String,Object> dataset = new HashMap<String,Object>();		
		try {
			 dataAccess = new DataAccess();
			 dataset = dataAccess.getAppraisalsOfDepartment(deptID);
			 
			 for(int i=0;i<dataset.size();i++){
				 if((int)dataset.get("Rating") < 3){
					 int managerID = (int)dataset.get("ManagerID");
					 eventComponent.addEventListener(new LowAppraisalEventListener(){
						 public void lessRatingEventRecieved(LowAppraisalEvent event){
							 sendMailToManager(managerID);
							}
					 });					 
				 }
			 }
		} 
		catch(Exception e) {
			e.printStackTrace();
		}	
		return dataset;		
	}
	
	public void sendMailToManager(int managerID){
		
		 dataAccess = new DataAccess();		
		 String to = dataAccess.getEmailId(managerID);

	     String from = "JavaProjectHR@gmail.com";

	     String host = "localhost";
	     
	     Properties properties = System.getProperties();

	     properties.setProperty("mail.smtp.host", host);	     
	     properties.setProperty("mail.user", "JavaProjectHR");
	     properties.setProperty("mail.password", "javaisinteresting");

	     Session session = Session.getDefaultInstance(properties);

	      try{
	          MimeMessage message = new MimeMessage(session);	    	  
	    	  message.setFrom(new InternetAddress(from));     
	    	  message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));        
	         
	    	  message.setSubject("Assigning trainings to employees for performance improvement");	         
	    	  message.setText("Dear Manager,"
	    	  		+ "This is an auto generated e-mail."
	    	  		+ "Kindly assign the trainings for the employees in your department whose rating is below average."
	    	  		+ "Regards,"
	    	  		+ "HR");
	    	  
	    	  Transport.send(message);
	         
	    	  System.out.println("Sent message successfully....");
	      }
	      catch (MessagingException mex) {
	         mex.printStackTrace();
	      }		
	}
	
}
