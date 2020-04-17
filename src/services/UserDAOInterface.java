package services;



public interface UserDAOInterface {
	public boolean admin(String username,String password) throws Exception;
	public void signUp(String username,String password) throws Exception;
	public boolean login(String username,String password) throws Exception;
}
