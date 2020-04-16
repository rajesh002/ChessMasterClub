package services;

public interface PlayerDAOInterface {
	public void addNewPlayer(String name,int age,String email,int tournament_id) throws Exception;
	public void displayPlayers() throws Exception;
}
