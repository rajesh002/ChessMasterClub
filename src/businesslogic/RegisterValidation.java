package businesslogic;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import model.SignUp;

public class RegisterValidation extends SignUp{
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	private Pattern pattern;
	private Matcher matcher1,matcher2;
	public RegisterValidation(String username,String password,String confirmPassword){
		 super(username,password,confirmPassword);
	  }
	  
	public boolean checkUserDetails() {
		if(validPassword(getPassWord(),getConfirmPassword())&& isValidUserName(getUserName()))
			return true;
		else
			return false;
	}

	//passwords validation
	
	private boolean validPassword(String password, String confirmPassword) {
		 pattern = Pattern.compile(PASSWORD_PATTERN);
		 if(password.equals(confirmPassword)) {
		 
		 matcher1 = pattern.matcher(password);
		 matcher2 = pattern.matcher(confirmPassword);
		  if(matcher2.matches() && matcher1.matches())
		  return true;
		  else{
			  System.out.println("password must be strong");
			  return false;
		  }	
		 }
		 System.out.println("password and confirm password must be same");
		 return false;
	}


	//username validation	 
	 
	private boolean isValidUserName(String s) 
  	{ 
	  if(s.matches("^[a-zA-Z0-9]+$"))
		return true;
	  else
	  {
		  System.out.println("UserName must be Alphanumeric");
		  return false;
	  }	
  	}
}    