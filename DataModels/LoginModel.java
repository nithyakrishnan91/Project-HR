

public class LoginModel {
	
	DataAccess dataobj = new DataAccess();

	public Boolean loginDetailCheck(String userName, String password) {
		try {
			String[] details = dataobj.getLoginDetails(userName, password);
			if (details != null)
				return true;
			else
				return false;
		} catch (Exception se) {
			se.getMessage();
			return false;
		}
	}

}
