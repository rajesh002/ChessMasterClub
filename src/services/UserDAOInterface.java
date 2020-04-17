package services;

import java.time.LocalDate;

public interface UserDAOInterface {
	public boolean admin(String username,String password) throws Exception;
	public void signUp(String username,String password) throws Exception;
	public boolean login(String username,String password) throws Exception;
	public void displayPlayersOfTournament(LocalDate date) throws Exception;
}
