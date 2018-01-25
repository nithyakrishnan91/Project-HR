

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class RecruitmentAndJob {

	int getopt;
	RecruitmentModel recruit = new RecruitmentModel();
	DataAccess obj;

	public void menuForJobPosting(){
		System.out.println(" Please Select an Option -0 / 1/ 2 / 3 / 4 / 5 / 6 / 7");

		System.out.println("---------------------------------------------------");

		System.out.println("0. Show Job Details - Shows a list of all the Available Job Openings");
		System.out.println("1. Post Job - Create a New Job Opening");
		System.out.println("2. Edit Job - Edit an Existing Job Opening");
		System.out.println("3. Delete Job - Delete an Existing Job Opening");
		System.out.println("4. Insert Applicant Details ");
		System.out.println("5. View Applicant Details ");
		System.out.println("6. Edit Applicant Details ");
		System.out.println("7. Send Email to Selected Candidates ");
		System.out.println("8. Exit");

		Scanner scan1 = new Scanner(System.in);
		getopt = scan1.nextInt();

	}

	public void postJobMenu(){

		//Job Post Screen Variables
		String jobDesc;
		int managerID;
		int vacancyNum;
		boolean Status;
		int saveToDb;

		// get User input for posting Job   
		System.out.println(" Please Enter the Following details to post a Job");
		System.out.println("Job Description :");

		Scanner input = new Scanner(System.in);
		jobDesc = input.nextLine();

		System.out.println("Please Provide the Manager ID :");
		managerID = input.nextInt();

		System.out.println("Number of Vacancies for the  Mentioned Position :");
		vacancyNum = input.nextInt();

		System.out.println("Status of the Position - Open or Close (true/false) :");
		Status = input.nextBoolean();

		System.out.println("Press 1 to Save the Details or 0 to Exit");
		saveToDb = input.nextInt();
		if (saveToDb==1){
			recruit.insertJob(jobDesc, managerID, vacancyNum, Status);
			System.out.println(" Data Saved Successfully");
		}
		else{
			System.out.println("Action - Posting Job Cancelled");			 
		}
	}

	public void deleteJobMenu(){ 

		String dltconf;
		String jobDesc;
		int managerID = 0;
		int vacancyNum = 0;
		boolean Status = false;

		Scanner input = new Scanner(System.in);
		System.out.println(" Please Enter the Job ID to Delete a Job Posting");		
		int jobID = input.nextInt();

		System.out.println("Enter Yes to Confirm or No to Exit");
		Scanner input1 = new Scanner(System.in);
		dltconf = input1.nextLine();

		if (dltconf.equals("Yes"))
		{				 
			recruit.deleteJob(jobID, managerID, vacancyNum, Status);			 
			System.out.println(" Data Deleted Successfully");			 
		}
		else{
			System.out.println("Action - Deleting Job Cancelled");
		}
	}

	public void updateJobMenu(){

		String updconf;
		String jobDesc = null;
		int managerID = 0;
		int vacancyNum = 0;
		boolean Status = false;

		System.out.println(" Please Enter the Job ID to Modify or Update a Job Posting");
		Scanner scan4 = new Scanner(System.in);
		int jobID = scan4.nextInt();

		System.out.println("Enter the new Job Description");
		Scanner scan5 = new Scanner(System.in);
		jobDesc = scan5.nextLine();

		System.out.println("Enter the new the Manager ID");
		managerID = scan5.nextInt();

		System.out.println("Enter the No of Vacancies ");
		vacancyNum = scan5.nextInt();

		System.out.println("Enter the Status (true/false)");
		Status = scan5.nextBoolean();

		System.out.println("Enter Yes to Confirm or No to Exit");
		Scanner scan6 = new Scanner(System.in);
		updconf = scan6.nextLine();

		if (updconf.equalsIgnoreCase("Yes")){				 
			recruit.updateJob(jobID,jobDesc, managerID, vacancyNum, Status);			 
			System.out.println(" Data updated Successfully");			 
		}
		else{
			System.out.println("Action - Updating Job Cancelled");
		}
	}

	public void showJobMenu() {	
		Map <Integer, String> rs = recruit.getJob();	
		for (Map.Entry <Integer, String> entry : rs.entrySet()) 
		{
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
	}

	public void insertapplicant(){

		String firstName = null;
		String lastName = null;
		int phone = 0;
		String email = null;
		int JobID = 0;
		int status = 0;
		int score = 0;   

		System.out.println("Enter the Applicant First Name :");
		Scanner scan7 = new Scanner(System.in);
		firstName = scan7.nextLine();

		System.out.println("Enter the Applicant Last Name :");
		lastName = scan7.nextLine();

		System.out.println("Enter the Applicant Email Address :");
		email = scan7.nextLine();

		System.out.println("Enter the file path for attaching Resume :");
		String path = scan7.nextLine();

		System.out.println("Enter the Job ID:");
		JobID = scan7.nextInt();

		System.out.println("Enter the Applicant Phone Number :");
		phone = scan7.nextInt();

		System.out.println("Enter Yes to Confirm or No to Exit");
		Scanner scan6 = new Scanner(System.in);
		String insconf = scan6.nextLine();

		if (insconf.equals("Yes")){

			recruit.insertAppl(firstName, lastName, phone, email, path,JobID);
			System.out.println("Applicant Details Created Successfully");
		}
		else

		{
			System.out.println("Action - Applicant Creation Cancelled");
		}
	}

	public void getApplDetails(){

		ArrayList<ApplicantsModel> lst = recruit.getApplDetails();

		for (ApplicantsModel entry : lst) 
		{
			System.out.println("------------------------");
			System.out.println("First Name - " + entry.firstName);//  lst.get(0));
			System.out.println("Last Name - " + entry.lastName);
			System.out.println("Phone - " + entry.phone);
			System.out.println("Email - " + entry.email);
			System.out.println("Status - " + entry.status);
			System.out.println("Status Description - " + entry.statusDes);
			System.out.println("Score - " + entry.score);
			System.out.println("Job Description - " + entry.jobDes);
			System.out.println("------------------------");			
		}
	}

	public void updApplicants()	{

		int JobID = 0;
		int status = 0;
		int score = 0;

		System.out.println(" Please Enter Applicant Id to edit his details");
		Scanner scan9 = new Scanner(System.in);
		int AppID = scan9.nextInt();

		System.out.println(" Please Enter Job Id of the Applicant");
		JobID = scan9.nextInt();

		System.out.println(" Please Enter the new Status of the Applicant");
		status = scan9.nextInt();

		System.out.println(" Please Enter the new Score of the Applicant");
		score = scan9.nextInt();

		System.out.println("Enter Yes to Confirm or No to Exit");
		Scanner sc = new Scanner(System.in);
		String updcon = sc.nextLine();

		if (updcon.equals("Yes")) {
			recruit.updateApplDetails(status, score, JobID, AppID);
			System.out.println(" Data updated Successfully");		 
		}
		else {
			System.out.println("Action - Applicant Update Cancelled");
		}
	}

	public void recruitmentMenu() throws AddressException, MessagingException{

		menuForJobPosting();

		while(getopt<8)
		{		
			switch(getopt)
			{
			case 0:
				showJobMenu();
				menuForJobPosting();
				break;

			case 1:
				postJobMenu();
				menuForJobPosting();
				break;

			case 2:
				updateJobMenu();
				menuForJobPosting();
				break;

			case 3:
				deleteJobMenu();
				menuForJobPosting();
				break;

			case 4:
				insertapplicant();
				menuForJobPosting();
				break;

			case 5:
				getApplDetails();
				//sendEmail();
				menuForJobPosting();
				break;
			case 6:
				updApplicants();
				menuForJobPosting();
				break;
			case 7:
				recruit.sendEmailToCandidates();
			   menuForJobPosting();
			   break;
			case 8:
				// call the main HR home page screen designed by AMulya
				getopt = -1;
				System.out.println("Welcome to the HR Portal " );
				break;
			}	   		   				
		}
	}
}

