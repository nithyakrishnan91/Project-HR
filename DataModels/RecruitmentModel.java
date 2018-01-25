import java.io.InputStream;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.PasswordAuthentication;

import javax.mail.Session;

import javax.mail.Message;

import javax.mail.MessagingException;

import javax.mail.Transport;

import javax.mail.internet.AddressException;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

import javax.mail.MessagingException;

public class RecruitmentModel{

	static DataAccess dataobj = new DataAccess();	

	public static void insertJob(String jobDesc,int managerID,int vacancyNum,boolean Status){
		dataobj.insertJobDetails(jobDesc, managerID, vacancyNum, Status);
	}
	public static void deleteJob(int jobID,int managerID,int vacancyNum,boolean Status){
		dataobj.deleteJobDetails(jobID, managerID, vacancyNum, Status);
	}
	public static void updateJob(int jobID,String jobDesc, int managerID,int vacancyNum,boolean Status){    	
		dataobj.updateJobDetails(jobID,jobDesc, managerID, vacancyNum, Status);    	
	}
	public static Map <Integer, String> getJob(){    	
		return dataobj.getJobDetails();
	}
	public void insertAppl(String firstName,String lastName,int phone,String email,String path,int JobID){
		dataobj.insertApplicantDetails( firstName, lastName, phone, email, path, JobID);
	}
	public ArrayList<ApplicantsModel> getApplDetails()
	{
		return dataobj.getApplDetails();
	}
	public void updateApplDetails(int status,int score, int JobID, int AppID)
	{
		dataobj.updateApplDetails (status, score, JobID , AppID);
	}
	// sending email to selected Candidates
		public void sendEmailToCandidates()throws AddressException, MessagingException

		{	
			 
			 Properties mailServerProperties;
			 Session getMailSession;
			 MimeMessage generateMailMessage;
			 String uname="JavaProjectHR@gmail.com";
			 String pwd="javaisinteresting";
			 String smtphost="smtp.gmail.com";
			 String smtpPort="587";
			 String senderName="HR";
			 
			 
			 Map <String, String> dictionary = dataobj.getSelectedCandidates();
			 if(dictionary.size()>0)
			 {
				
								
			// Step1
			 // System.out.println("\n 1st ===> setup Mail Server Properties..");
			  mailServerProperties = System.getProperties();
			  mailServerProperties.put("mail.host", smtphost);
			  mailServerProperties.put("mail.smtp.port", smtpPort);
			  mailServerProperties.put("mail.smtp.auth", "true");
			  mailServerProperties.put("mail.smtp.starttls.enable", "true");
			  //System.out.println("Mail Server Properties have been setup successfully..");
			 
			  // Step2
			  
			  for (Map.Entry <String, String> entry : dictionary.entrySet()) 
				{
				  
				  //System.out.println(entry.getKey() + " - " + entry.getValue());
				  
			 // System.out.println("\n\n 2nd ===> get Mail Session..");
			  	  //getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			  getMailSession = Session.getInstance(mailServerProperties,
			      new javax.mail.Authenticator() {
			     protected PasswordAuthentication getPasswordAuthentication() {
			      return new PasswordAuthentication(uname, pwd);
			     }
			      });

			  
			  
			  generateMailMessage = new MimeMessage(getMailSession);
			  generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(entry.getValue()));
			  generateMailMessage.setSubject("Offer from ABC Infosystems");
			  generateMailMessage.setFrom(senderName);
			  String emailBody = "Dear " + entry.getKey() + "," + " <br> <br> You have cleared the interview and been selected as Software Engineer.We are delighted to see you onboard in our Organization. " + "<br><br> Regards, <br>The HR Team";
			  generateMailMessage.setContent(emailBody, "text/html");
			  //System.out.println("Mail Session has been created successfully..");
			 
			  // Step3
			 // System.out.println("\n\n 3rd ===> Get Session and Send mail");
			  
			  Transport.send(generateMailMessage);
			  System.out.println("\n\n Email sent Successfully to " + entry.getKey());
			 }
			 }
			 else
			 {
				 System.out.println("\n\n No Candidates Selected");
			 }
		}


}


