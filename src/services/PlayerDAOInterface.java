package services;

public interface PlayerDAOInterface {
	public boolean addNewPlayer(String name,int age,String email,int tournament_id) throws Exception;
	public void displayPlayers(int tornamentId) throws Exception;
	public boolean markAsWinner(int playerTournamentId) throws Exception;
	public void addLosses(int playerId,int losses) throws Exception;
	public void addWins(int playerId,int wins) throws Exception ;
}
