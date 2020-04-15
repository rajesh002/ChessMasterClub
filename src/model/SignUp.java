package model;

public class SignUp {
     private String userName;
     private String passWord;
     public SignUp(String userName, String passWord, String confirmPassword) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.confirmPassword = confirmPassword;
	}
	private String confirmPassword;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}    
}
