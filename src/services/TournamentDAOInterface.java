package services;

import java.time.LocalDate;

public interface TournamentDAOInterface {
	public void addNewTournament(String tournamentName,LocalDate startDate,LocalDate endDate) throws Exception ;
	public void displayTournaments() throws Exception;
	public boolean tournamentNotEnded(int id) throws Exception;
	public boolean displayPlayersOfTournament(LocalDate date) throws Exception ;
}
