package services;

public interface PlayerDAOInterface {
	public boolean addNewPlayer(String name,int age,String email,int tournament_id) throws Exception;
	public void displayPlayers(int tornamentId) throws Exception;
	public boolean markAsWinner(int playerTournamentId) throws Exception;
	public void addLosses(int playerId,int losses) throws Exception;
	public void addWins(int playerId,int wins) throws Exception ;
	public void requestToParticipate(String playerName, int playerAge, String playerCity,String username,String password) throws Exception;
	public void displayPendingRequests() throws Exception;
	public void acceptRequestAddPlayer(int requestId, int playerTournamentId) throws Exception;
	public boolean requestExists(String username, String password) throws Exception;
}
