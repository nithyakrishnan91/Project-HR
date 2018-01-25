import java.util.Scanner;

public class Login {
	
	LoginModel userlogin = new LoginModel();
	Scanner input = new Scanner(System.in);
	
	public boolean isValidLogin(){
	
	// Get login details
				System.out.println("Please Enter your User Name: ");
				String userName = input.next();

				System.out.println("Please Enter your Password: ");
				String password = input.next();				
				
				return userlogin.loginDetailCheck(userName, password);
	}
}
